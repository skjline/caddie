package com.skjline.caddie.common.utils

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created on 9/27/17.
 */

fun <T : View> Activity.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) as T }
}

fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) as T }
}

fun <T : View> View.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) as T }
}

fun <T : View> android.support.v4.app.Fragment.findChild(@IdRes res : Int) : T {
    @Suppress("UNCHECKED_CAST")
    return activity.findViewById(res)
}

fun <T : View> android.support.v4.app.Fragment.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return activity.bind(res)
}
