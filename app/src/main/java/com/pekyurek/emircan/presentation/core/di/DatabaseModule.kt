package com.pekyurek.emircan.presentation.core.di

import android.content.Context
import androidx.room.Room
import com.pekyurek.emircan.presentation.core.data.db.AppDatabase
import com.pekyurek.emircan.presentation.core.data.db.dao.OverlayDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }
}