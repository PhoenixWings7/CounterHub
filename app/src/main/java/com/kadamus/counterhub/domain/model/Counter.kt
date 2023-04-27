package com.kadamus.counterhub.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "counters")
data class Counter(@PrimaryKey(autoGenerate = true) val id: Int, var title: String, var count: Int)