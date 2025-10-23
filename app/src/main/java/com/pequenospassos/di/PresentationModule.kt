package com.pequenospassos.di

import android.content.Context
import com.pequenospassos.presentation.utils.TtsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt para dependências de apresentação.
 *
 * Fornece:
 * - TtsManager (Text-to-Speech)
 *
 * @since MVP-08 v1.10.1 (23/10/2025)
 */
@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideTtsManager(
        @ApplicationContext context: Context
    ): TtsManager {
        return TtsManager(context)
    }
}

