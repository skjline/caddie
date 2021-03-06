package com.skjline.caddie.database.presenter

import android.text.TextUtils
import android.util.Log
import com.skjline.caddie.common.model.Round
import com.skjline.caddie.common.model.Stroke
import com.skjline.caddie.database.StrokeDatabase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

/**
 * Created on 9/21/17.
 */
class DatabasePresenter(val database: StrokeDatabase) {

    fun restoreExistingGame(session: String): Single<Round> {
        if (TextUtils.isEmpty(session)) {
            // if non-empty; null check is done by the language
            throw IllegalArgumentException("passed contains empty string")
        }

        return database.roundDao()
                .getRounds(session)
                .subscribeOn(Schedulers.io())
                // should optimize to return a single item
                .map { list -> list.get(list.size - 1) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createGameSession(): Observable<Round> {
        return Observable.fromCallable {
            Callable {
                val session = Round("Course")

                database.roundDao().insertRound(session)
                session
            }.call()
        }.subscribeOn(Schedulers.io())
    }

    fun insertStroke(stroke: Stroke) {
        Observable.fromCallable {
            Callable {
                database.strokeDao().insertStroke(stroke)
                stroke.strokeId
            }.call()
        }.subscribeOn(Schedulers.io())
                .subscribe { s ->
                    Log.wtf(DatabasePresenter::class.java.simpleName,
                            "Successfully inserted stroke: $s")
                }

    }
}