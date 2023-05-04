package com.kadamus.counterhub.data.local_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kadamus.counterhub.domain.model.Counter
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Query("SELECT * FROM counters")
    fun getCounters(): Flow<List<Counter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCounter(counter: Counter)

    @Query("DELETE FROM counters WHERE id=:counterId")
    suspend fun deleteCounter(counterId: Int)

    @Query("UPDATE counters SET count = count + :incrementBy WHERE id=:counterId")
    suspend fun incrementCounter(counterId: Int, incrementBy: Int = 1)
}