package com.skjline.caddie

import com.skjline.caddie.activity.ScoreActivity
import com.skjline.caddie.game.fragment.Game
import com.skjline.caddie.history.History
import com.skjline.caddie.model.AppModule
import com.skjline.caddie.utils.FragmentRouter
import dagger.Component
import javax.inject.Singleton

/**
 * Created on 8/31/17.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, FragmentRouter::class))
        interface AppComponent {
    fun inject(app: App)
    fun inject(activity: ScoreActivity)

    fun inject(fragment: Game)
    fun inject(fragment: History)
}