package com.skjline.caddie.model

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.skjline.caddie.App
import com.skjline.caddie.utils.FragmentRouter
import com.skjline.caddie.utils.ScopeQaulifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created on 8/31/17.
 */

@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    @ScopeQaulifier
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideLocationManager(): LocationManager {
        return app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}