package com.kadamus.counterhub.data.local_source

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kadamus.counterhub.domain.Counter

@Dao
interface CounterDao {
    @Query("SELECT * FROM counters")
    fun getCounters(): LiveData<List<Counter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCounter(counter: Counter)

    @Delete(entity = Counter::class)
    suspend fun deleteCounter(counter: Counter)
}