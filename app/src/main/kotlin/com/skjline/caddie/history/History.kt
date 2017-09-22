package com.skjline.caddie.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skjline.caddie.App
import com.skjline.caddie.common.Base
import com.skjline.caddie.model.Stroke

/**
 * Created on 8/29/17.
 */

class History : Base() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.component.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val obj = Stroke()
        triggerEvent(obj)
    }

    companion object {
        fun newInstance(bundle: Bundle): History {
            val history = History()
            history.arguments = bundle

            return history
        }
    }

}