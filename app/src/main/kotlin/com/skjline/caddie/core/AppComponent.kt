package com.skjline.caddie.core

import com.skjline.caddie.App
import com.skjline.caddie.common.model.AppModule
import com.skjline.caddie.common.utils.FragmentRouter
import com.skjline.caddie.core.activity.ScoreActivity
import com.skjline.caddie.game.controller.MapViewController
import com.skjline.caddie.game.fragment.Game
import com.skjline.caddie.history.History
import dagger.Component
import javax.inject.Singleton

/**
 * Created on 8/31/17.
 */

@Singleton
@Component(modules = [(AppModule::class), (FragmentRouter::class)])
        interface AppComponent {
    fun inject(app: App)

    fun inject(activity: ScoreActivity)

    fun inject(fragment: Game)
    fun inject(fragment: History)

    fun inject(controller: MapViewController)
}
