package com.test.shaadi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.shaadi.R
import com.test.shaadi.data.local.db.entity.UserEntity
import com.test.shaadi.data.local.prefs.SharedPref
import com.test.shaadi.data.network.Status
import com.test.shaadi.data.network.pojo.user.Results
import com.test.shaadi.databinding.ActivityMainBinding
import com.test.shaadi.ui.base.BaseActivity
import com.test.shaadi.utils.AppConstants
import com.test.shaadi.utils.showToast
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    UserRecyclerViewAdapter.UserItemClickListener {
    private lateinit var binding: ActivityMainBinding

    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()
    }

    private fun setup() {
        binding = getBinding()
        binding.rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)

        if (SharedPref.isFirstTime) {
            getDataFromServer()
        } else {
            getDataFromDB()
        }
    }

    private fun getDataFromServer() {
        viewModel.getUserDataFromServer().observe(this, { data ->
            data?.status?.let { response ->
                when (response) {
                    Status.ERROR -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progress.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progress.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                    }

                    Status.SUCCESS -> {
                        data.data?.body()?.results?.let {
                            saveDataToDB(it)
                        } ?: kotlin.run {
                            binding.rvUsers.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                        }
                    }
                }
            }

        })
    }

    private fun getDataFromDB() {
        viewModel.getAllUsersFromDB().observe(this, { data ->
            data?.status?.let { response ->
                when (response) {
                    Status.ERROR -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progress.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                    }

                    Status.LOADING -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progress.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                    }

                    Status.SUCCESS -> {
                        binding.rvUsers.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        binding.tvError.visibility = View.GONE
                        data.data?.let {
                            binding.rvUsers.adapter =
                                UserRecyclerViewAdapter(this@MainActivity, it, this)
                        } ?: kotlin.run {
                            binding.rvUsers.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                        }
                    }
                }
            }

        })
    }

    private fun saveDataToDB(result: ArrayList<Results>) {
        Log.e("MainActivity", "savetoDb")
        if (result.size > 0) {
            Log.e("MainActivity", "result.size")
            val userEntityList = ArrayList<UserEntity>()
            result.forEachIndexed { index, user ->
                Log.e("MainActivity", "user$user")
                val userEntity = UserEntity(
                    firstName = user.name?.first,
                    lastName = user.name?.last,
                    age = user.dob?.age,
                    state = user.location?.state,
                    country = user.location?.country,
                    status = AppConstants.UserStatus.STATUS_NOT_DEFINED,
                    id = index,
                    userAvatar = user.picture?.large
                )
                Log.e("MainActivity", "" + user?.id ?: "Id null")
                userEntityList.add(userEntity)
            }
            viewModel.insertAllUsersToDB(userEntityList).observe(this@MainActivity, {
                Log.e("MainActivity", "insideAllUsers")
                it?.status?.let { status ->
                    Log.e("MainActivity", "status:$status")
                    when (status) {
                        Status.ERROR -> {
                            Log.e("MainActivity", "inside status error")
                            binding.rvUsers.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            binding.rvUsers.adapter =
                                UserRecyclerViewAdapter(this@MainActivity, userEntityList, this)
                        }

                        Status.LOADING -> {
                            Log.e("MainActivity", "inside status LOADING")
                            binding.rvUsers.visibility = View.GONE
                            binding.progress.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                        }

                        Status.SUCCESS -> {
                            Log.e("MainActivity", "inside status SUCCESS")

                            binding.rvUsers.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            it?.data?.let {
                                Log.e("MainActivity", "Size: " + it.size)
                                SharedPref.isFirstTime = false
                                binding.rvUsers.adapter =
                                    UserRecyclerViewAdapter(this@MainActivity, userEntityList, this)
                            } ?: kotlin.run {
                                binding.rvUsers.visibility = View.GONE
                                binding.progress.visibility = View.GONE
                                binding.tvError.visibility = View.VISIBLE
                            }
                        }
                    }

                }
            })
        }
    }

    override fun onActionButtonClicked(item: UserEntity, actionType: String) {
        val userEntity = item
        when (actionType) {
            AppConstants.TypeConstants.ACTION_TYPE_ACCEPT -> {
                userEntity.status = AppConstants.UserStatus.STATUS_ACCEPTED
            }

            AppConstants.TypeConstants.ACTION_TYPE_DECLINE -> {
                userEntity.status = AppConstants.UserStatus.STATUS_DECLINED
            }
        }
        viewModel.updateUserInDB(userEntity).observe(this, {
            it?.status?.let { status ->
                when (status) {
                    Status.ERROR -> {
                        Log.e("MainActivity", "user update failed")
                    }

                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {
                        Log.e("MainActivity", "user update success")
                    }
                }

            }

        })
    }
}