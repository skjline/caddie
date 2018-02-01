package com.skjline.caddie.history

import android.os.Bundle
import com.skjline.caddie.common.Base

/**
 * Created on 8/29/17.
 */

class History : Base() {

    companion object {
        fun newInstance(bundle: Bundle?): History {
            val history = History()

            bundle?.let {
                history.arguments = it
            }

            return history
        }
    }

}