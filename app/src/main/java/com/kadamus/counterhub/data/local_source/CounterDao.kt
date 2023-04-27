package com.kadamus.counterhub.data.local_source

import androidx.room.*
import com.kadamus.counterhub.domain.model.Counter
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Query("SELECT * FROM counters")
    fun getCounters(): Flow<List<Counter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCounter(counter: Counter)

    @Delete(entity = Counter::class)
    suspend fun deleteCounter(counter: Counter)
}