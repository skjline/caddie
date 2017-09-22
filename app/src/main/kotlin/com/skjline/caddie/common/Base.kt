package com.skjline.caddie.common

import android.support.v4.app.Fragment

/**
 * Created on 9/6/17.
 */

open class Base : Fragment() {

    private var listener: OnFragmentEvent? = null

    fun setEventListener(listener: OnFragmentEvent) {
        this.listener = listener
    }

    protected fun triggerEvent(event: Any) {
        listener?.onEvent(event)
    }

    interface OnFragmentEvent {
        fun onEvent(event: Any)
    }
}