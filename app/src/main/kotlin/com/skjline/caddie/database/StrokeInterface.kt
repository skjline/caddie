package com.skjline.caddie.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.skjline.caddie.common.Const
import com.skjline.caddie.model.Stroke
import io.reactivex.Single


/**
 * Created on 9/19/17.
 */
@Dao
interface StrokeInterface {
    @Query("SELECT * FROM ${Const.STOKE_TABLE}")
    fun getStrokes(): Single<List<Stroke>>

    @Query("SELECT * FROM ${Const.STOKE_TABLE} WHERE hole = :hole")
    fun getStrokes(hole: Int): Single<List<Stroke>>

    @Insert
    fun insertStroke(stroke: Stroke)

    @Insert
    fun insertStroke(strokes: Array<out Stroke?>)
}