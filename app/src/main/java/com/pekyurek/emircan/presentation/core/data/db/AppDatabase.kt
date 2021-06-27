package com.pekyurek.emircan.presentation.core.data.db

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.pekyurek.emircan.presentation.core.data.db.dao.OverlayDao
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay

@Database(entities = [Overlay::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun overlayDao(): OverlayDao

    @Keep
    companion object {
        val DATABASE_NAME = AppDatabase::class.java.simpleName
    }

}