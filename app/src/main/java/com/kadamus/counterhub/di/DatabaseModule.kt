package com.kadamus.counterhub.di

import android.content.Context
import androidx.room.Room
import com.kadamus.counterhub.data.local_source.CounterDao
import com.kadamus.counterhub.data.local_source.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideLocalDb(@ApplicationContext appContext: Context): LocalDatabase {
        return Room
            .databaseBuilder(appContext, LocalDatabase::class.java, "counter_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCounterDao(db: LocalDatabase): CounterDao = db.counterDao()
}