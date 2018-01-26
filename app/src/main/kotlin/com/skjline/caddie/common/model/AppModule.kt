package com.skjline.caddie.common.model

import android.arch.persistence.room.Room
import android.content.Context
import android.location.LocationManager
import com.skjline.caddie.App
import com.skjline.caddie.common.Const
import com.skjline.caddie.common.utils.ScopeQaulifier
import com.skjline.caddie.database.StrokeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created on 8/31/17.
 */

@Module
class AppModule(val app: App) {
    @Provides @Singleton
    @ScopeQaulifier
    fun provideApp() = app

    @Provides @Singleton
    fun provideLocationManager() =
            app.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides @Singleton
    fun providerStrokeDatabase() =
            Room.databaseBuilder(app, StrokeDatabase::class.java, Const.DATABASE_NAME).build()
}
