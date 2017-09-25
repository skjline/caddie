package com.skjline.caddie.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.skjline.caddie.common.Const
import com.skjline.caddie.model.Round
import io.reactivex.Single

/**
 * Created on 9/20/17.
 */
@Dao
interface RoundInterface {
    @Query("SELECT * FROM ${Const.ROUND_TABLE}")
    fun getRounds(): Single<List<Round>>

    @Query("SELECT * FROM ${Const.ROUND_TABLE} WHERE session = :session")
    fun getRounds(session: String): Single<List<Round>>

    @Insert
    fun insertRound(round: Round)

    @Insert
    fun insertRound(rounds: Array<out Round?>)
}