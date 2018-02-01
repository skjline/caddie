package com.skjline.caddie.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.skjline.caddie.common.model.Round
import com.skjline.caddie.common.model.Stroke
import dagger.Module


/**
 * Created on 9/19/17.
 */
@Module
@Database(entities = arrayOf(Stroke::class, Round::class), version = 1)
abstract class StrokeDatabase : RoomDatabase() {
    abstract fun strokeDao(): StrokeInterface
    abstract fun roundDao(): RoundInterface
}