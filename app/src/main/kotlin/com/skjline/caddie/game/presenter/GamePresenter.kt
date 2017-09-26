package com.skjline.caddie.game.presenter

import com.skjline.caddie.common.ViewController
import com.skjline.caddie.common.model.Hole
import com.skjline.caddie.common.model.Stroke

/**
 * Created on 9/14/17.
 */
interface GamePresenter<T : ViewController> {
    fun setHolePar(par: Int)
    fun addStroke(shot: Stroke)
    fun finishHole(): Hole

//    fun onMapPositionChanged(): Observable<Location>
}