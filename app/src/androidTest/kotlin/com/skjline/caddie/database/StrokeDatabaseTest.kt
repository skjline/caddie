package com.skjline.caddie.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.skjline.caddie.model.Stroke
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Created on 9/19/17.
 */
@RunWith(AndroidJUnit4::class)
class StrokeDatabaseTest {
    lateinit var strokeInterface: StrokeInterface
    lateinit var database: StrokeDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room
                .inMemoryDatabaseBuilder(context, StrokeDatabase::class.java)
                .build()
        strokeInterface = database.strokeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val stroke = Stroke(0, 0, false, 0.0, 0.0)
        strokeInterface.insertStroke(stroke)

        strokeInterface.getStrokes().test()
                .assertNoErrors()
                .assertValue { l ->
                    l.isNotEmpty() && l.get(0).strokeId.equals(stroke.strokeId)
                }
    }
}