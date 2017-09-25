package com.skjline.caddie

import android.app.Application
import com.skjline.caddie.injectors.AppComponent
import com.skjline.caddie.injectors.DaggerAppComponent
import com.skjline.caddie.model.AppModule

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