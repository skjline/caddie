package com.skjline.caddie.common.utils

import java.text.DecimalFormat

/**
 * Created on 9/15/17.
 */

fun Double.digitFormat(digit: Int) : String {
    var format = StringBuilder("#.")
    var index = 0

    while (index++ < digit) {
       format = format.append("0")
    }

    return DecimalFormat(format.toString()).format(this)
}