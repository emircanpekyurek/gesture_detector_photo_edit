package com.pekyurek.emircan.presentation.core.di

import android.content.Context
import com.pekyurek.emircan.presentation.core.data.db.AppDatabase
import com.pekyurek.emircan.presentation.core.data.repository.ApiService
import com.pekyurek.emircan.presentation.core.data.repository.RemoteDataSource
import com.pekyurek.emircan.presentation.core.data.repository.Repository
import com.pekyurek.emircan.presentation.core.data.repository.RepositoryImpl
import com.pekyurek.emircan.presentation.core.data.repository.locale.LocaleDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideLocaleDataSource(@ApplicationContext context: Context, appDatabase: AppDatabase) =
        LocaleDataSource(context, appDatabase)

    @Provides
    fun provideRemoteDataSource(
        @ApplicationContext context: Context,
        apiService: ApiService
    ) = RemoteDataSource(context, apiService)

    @Provides
    fun provideRepositoryImpl(
        localeDataSource: LocaleDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository = RepositoryImpl(localeDataSource, remoteDataSource)

}