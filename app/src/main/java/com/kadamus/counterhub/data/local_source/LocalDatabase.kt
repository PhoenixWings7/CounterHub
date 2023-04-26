package com.kadamus.counterhub.data.local_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadamus.counterhub.domain.Counter

@Database(entities = [Counter::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao
}