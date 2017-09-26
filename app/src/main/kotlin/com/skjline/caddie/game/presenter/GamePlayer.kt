package com.skjline.caddie.game.presenter

import com.skjline.caddie.common.model.Hole
import com.skjline.caddie.common.model.Stroke
import com.skjline.caddie.game.controller.HoleViewController

/**
 * Created on 9/14/17.
 */
class GamePlayer(val view: HoleViewController) : GamePresenter<HoleViewController> {

    override fun setHolePar(par: Int) {
    }

    override fun addStroke(shot: Stroke) {
    }

    override fun finishHole()
            = Hole.build(0) { par = 4 }

}