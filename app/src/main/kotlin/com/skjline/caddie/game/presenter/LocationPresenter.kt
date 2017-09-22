package com.skjline.caddie.game.presenter

import android.location.Location
import io.reactivex.Observable

/**
 * Created on 9/15/17.
 */
interface LocationPresenter {

    fun onPositionChanged(): Observable<Location>
}