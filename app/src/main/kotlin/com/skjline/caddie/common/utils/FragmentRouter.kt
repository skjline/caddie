package com.skjline.caddie.common.utils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skjline.caddie.R
import com.skjline.caddie.game.fragment.Game
import com.skjline.caddie.history.History
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created on 9/6/17.
 */
@Module
class FragmentRouter : Route {

    @Provides
    @Singleton
    fun provideFragmentRouter() = FragmentRouter()

    override fun route(activity: AppCompatActivity, frag: Int) {
        route(activity, frag, null)
    }

    override fun route(activity: AppCompatActivity, frag: Int, bundle: Bundle?) {
        val fragment = createFragment(frag, bundle)
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit()
    }

    private fun createFragment(fragment: Int, bundle: Bundle?) =
            when (fragment) {
                R.layout.game_fragment -> Game.newInstance(bundle)
                R.layout.hole_score_list -> History.newInstance(bundle)
                else -> throw IllegalArgumentException("invalid fragment ID")
            }

}

interface Route {
    fun route(activity: AppCompatActivity, fragment: Int)
    fun route(activity: AppCompatActivity, fragment: Int, bundle: Bundle?)
}
