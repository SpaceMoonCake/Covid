package com.spacemooncake.covid.di

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.spacemooncake.covid.model.database.Database
import com.spacemooncake.covid.model.repository.Repository
import com.spacemooncake.covid.model.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Array.get
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "database"
    ).build()

    @Singleton
    @Provides
    fun provideRepository(db: Database): Repository{
        return RepositoryImpl(db = db)
    }

}