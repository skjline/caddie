package com.skjline.caddie.common.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.skjline.caddie.common.Const
import java.util.*

/**
 * Created on 8/28/17.
 */
//data class Stroke (val latitude: Long = 0, val longitude: Long = 0, val count: Int = 0, val isDrop: Boolean = false)

@Entity(tableName= Const.STOKE_TABLE)
data class Stroke(var hole: Int,
                  var stroke: Int,
                  var penalty: Boolean,
                  var latitude: Double,
                  var longitude: Double) {
    @PrimaryKey
    var strokeId = UUID.randomUUID().toString()
    var timestamp = System.currentTimeMillis()
}