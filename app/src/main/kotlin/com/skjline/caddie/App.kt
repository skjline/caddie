package com.skjline.caddie

import android.app.Application
import android.arch.persistence.room.Room
import com.skjline.caddie.common.Const
import com.skjline.caddie.database.StrokeDatabase
import com.skjline.caddie.model.AppModule

/**
 * Created on 8/31/17.
 */

class App : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var component: AppComponent

        @JvmStatic lateinit var database: StrokeDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(applicationContext,
                StrokeDatabase::class.java, Const.DATABASE_NAME).build()

        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }
}