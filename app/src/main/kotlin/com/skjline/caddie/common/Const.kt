package com.skjline.caddie.common

/**
 * Created on 9/20/17.
 */
class Const {
    companion object {
        @JvmStatic val DATABASE_NAME = "caddie.db"

        const val HOLE_TABLE = "hole"
        const val ROUND_TABLE = "round"
        const val STOKE_TABLE = "stroke"

        const val POSITION_TYPE = "position_type"
        const val POSITION_REF = 1
        const val POSITION_MAP = 2

        const val METERS_TO_YARDS = 1.0936133
    }
}