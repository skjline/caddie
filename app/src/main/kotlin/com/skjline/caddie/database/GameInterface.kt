package com.skjline.caddie.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.skjline.caddie.common.Const
import com.skjline.caddie.model.Game
import io.reactivex.Single

/**
 * Created on 9/20/17.
 */
@Dao
interface GameInterface  {
    @Query("SELECT * FROM ${Const.GAME_TABLE}")
    fun getGames(): Single<List<Game>>

    @Query("SELECT * FROM ${Const.GAME_TABLE} WHERE session = :session")
    fun getGames(session: String): Single<List<Game>>

    @Insert
    fun insertGame(game: Game)

    @Insert
    fun insertGame(games: Array<out Game?>)
}