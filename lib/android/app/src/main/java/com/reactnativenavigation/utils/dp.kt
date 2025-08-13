package com.reactnativenavigation.utils

import android.content.res.Resources

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int?.dp: Int?
    get() = this?.let { (it * Resources.getSystem().displayMetrics.density).toInt() }
