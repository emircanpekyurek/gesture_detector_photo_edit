package com.pekyurek.emircan.presentation.core.di

import android.content.Context
import androidx.annotation.StringRes
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }



}
