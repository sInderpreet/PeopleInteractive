package com.test.shaadi

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Shaadi : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Shaadi? = null

        fun applicationContext(): Context {
            return instance?.applicationContext ?: Shaadi()
        }
    }
}