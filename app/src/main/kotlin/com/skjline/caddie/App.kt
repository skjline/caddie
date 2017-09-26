package com.skjline.caddie

import android.app.Application
import com.skjline.caddie.common.model.AppModule
import com.skjline.caddie.core.AppComponent
import com.skjline.caddie.core.DaggerAppComponent

/**
 * Created on 8/31/17.
 */

class App : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }
}