package com.test.shaadi.utils

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}