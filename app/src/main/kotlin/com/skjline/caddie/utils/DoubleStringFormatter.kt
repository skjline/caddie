package com.skjline.caddie.utils

import java.text.DecimalFormat

/**
 * Created on 9/15/17.
 */

fun Double.digitFormat(digit: Int) {
    var format = StringBuilder("#.")
    var index = 0

    while (index++ < digit) {
       format = format.append("0")
    }

    DecimalFormat(format.toString()).format(this)
}