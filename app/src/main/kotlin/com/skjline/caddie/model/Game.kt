package com.skjline.caddie.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.skjline.caddie.common.Const
import java.util.*

/**
 * Created on 9/20/17.
 */
@Entity(tableName = Const.GAME_TABLE)
data class Game (var course: String) {
    @PrimaryKey
    var session: String = UUID.randomUUID().toString()
    var start: Long? = null
    var finish: Long? = null
}