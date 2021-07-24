package com.test.shaadi.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.shaadi.R
import com.test.shaadi.data.local.db.entity.UserEntity
import com.test.shaadi.data.network.pojo.user.Results
import com.test.shaadi.databinding.ItemUserBinding
import com.test.shaadi.utils.AppConstants

class UserRecyclerViewAdapter(
    private val context: Context,
    private val dataList: List<UserEntity>,
    private val listener: UserItemClickListener
) :
    RecyclerView.Adapter<UserRecyclerViewAdapter.UserItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val genreItemBinding: ItemUserBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_user,
                parent,
                false
            )
        return UserItemViewHolder(genreItemBinding)

    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val user = dataList[position]

        Glide.with(context).load(user.userAvatar ?: R.mipmap.ic_launcher)
            .into(holder.itemUserBinding.ivUserAvatar)

        holder.itemUserBinding.tvName.text = context.getString(
            R.string.user_name,
            user.firstName ?: "",
            user.lastName ?: ""
        )

        holder.itemUserBinding.tvAge.text = context.getString(
            R.string.user_age,
            user.age ?: ""
        )

        holder.itemUserBinding.tvCountry.text = context.getString(
            R.string.user_location,
            user.state ?: "",
            user.country ?: ""
        )

        user.status.let {

            when (it) {
                AppConstants.UserStatus.STATUS_ACCEPTED -> {
                    holder.itemUserBinding.btnDecline.visibility = View.INVISIBLE
                    holder.itemUserBinding.btnAccept.visibility = View.INVISIBLE
                    holder.itemUserBinding.tvMessage.visibility = View.VISIBLE
                    holder.itemUserBinding.tvMessage.text = context.getString(
                        R.string.text_response_message,
                        "accepted"
                    )
                }

                AppConstants.UserStatus.STATUS_DECLINED -> {
                    holder.itemUserBinding.btnDecline.visibility = View.INVISIBLE
                    holder.itemUserBinding.btnAccept.visibility = View.INVISIBLE
                    holder.itemUserBinding.tvMessage.visibility = View.VISIBLE
                    holder.itemUserBinding.tvMessage.text = context.getString(
                        R.string.text_response_message,
                        "declined"
                    )
                }
                else -> {
                    holder.itemUserBinding.btnDecline.visibility = View.VISIBLE
                    holder.itemUserBinding.btnAccept.visibility = View.VISIBLE
                    holder.itemUserBinding.tvMessage.visibility = View.GONE

                }

            }
        }

        holder.itemUserBinding.btnAccept.setOnClickListener {
            holder.itemUserBinding.btnDecline.visibility = View.INVISIBLE
            holder.itemUserBinding.btnAccept.visibility = View.INVISIBLE
            holder.itemUserBinding.tvMessage.visibility = View.VISIBLE
            holder.itemUserBinding.tvMessage.text = context.getString(
                R.string.text_response_message,
                "accepted"
            )
            listener?.onActionButtonClicked(
                item = user,
                actionType = AppConstants.TypeConstants.ACTION_TYPE_ACCEPT
            )
        }

        holder.itemUserBinding.btnDecline.setOnClickListener {
            holder.itemUserBinding.btnDecline.visibility = View.INVISIBLE
            holder.itemUserBinding.btnAccept.visibility = View.INVISIBLE
            holder.itemUserBinding.tvMessage.visibility = View.VISIBLE
            holder.itemUserBinding.tvMessage.text = context.getString(
                R.string.text_response_message,
                "rejected"
            )
            listener?.onActionButtonClicked(
                item = user,
                actionType = AppConstants.TypeConstants.ACTION_TYPE_DECLINE
            )
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class UserItemViewHolder(itemView: ItemUserBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var itemUserBinding: ItemUserBinding = itemView

    }

    interface UserItemClickListener {
        fun onActionButtonClicked(item: UserEntity, actionType: String)
    }

}