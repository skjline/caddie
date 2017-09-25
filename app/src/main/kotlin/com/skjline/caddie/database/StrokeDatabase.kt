package com.skjline.caddie.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.skjline.caddie.model.Round
import com.skjline.caddie.model.Stroke


/**
 * Created on 9/19/17.
 */

@Database(entities = arrayOf(Stroke::class, Round::class), version = 1)
abstract class StrokeDatabase : RoomDatabase() {
    abstract fun strokeDao(): StrokeInterface
    abstract fun roundDao(): RoundInterface
}