package com.skjline.caddie.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.skjline.caddie.common.Const
import java.util.*

/**
 * Created on 9/9/17.
 */

@Entity(tableName = Const.ROUND_TABLE)
data class Round (var course: String) {
    @PrimaryKey
    var session: String = UUID.randomUUID().toString()
    var start: Long? = null
    var finish: Long? = null

//    var holes: SparseArray<Hole>? = null
}
