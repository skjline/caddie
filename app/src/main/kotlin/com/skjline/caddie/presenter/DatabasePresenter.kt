package com.skjline.caddie.presenter

import android.text.TextUtils
import android.util.Log
import com.skjline.caddie.database.StrokeDatabase
import com.skjline.caddie.model.Game
import com.skjline.caddie.model.Stroke
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

/**
 * Created on 9/21/17.
 */
class DatabasePresenter(val database: StrokeDatabase) {

    fun restoreExistingGame(session: String): Single<Game> {
        if (TextUtils.isEmpty(session)) {
            // if non-empty; null check is done by the language
            throw IllegalArgumentException("passed contains empty string")
        }

        return database.gameDao()
                .getGames(session)
                .subscribeOn(Schedulers.io())
                // should optimize to return a single item
                .map { list -> list.get(list.size - 1) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createGameSession(): Observable<Game> {
        return Observable.fromCallable {
            Callable { ->
                val session = Game("Course")

                database.gameDao().insertGame(session)
                return@Callable session
            }.call()
        }.subscribeOn(Schedulers.io())
    }

    fun insertStroke(stroke: Stroke) {
        Observable.fromCallable {
            Callable { ->
                database.strokeDao().insertStroke(stroke)
                return@Callable stroke.strokeId
            }.call()
        }.subscribeOn(Schedulers.io())
                .subscribe { s ->
                    Log.wtf(DatabasePresenter::class.java.simpleName,
                            "Successfully inserted stroke: $s")
                }

    }
}