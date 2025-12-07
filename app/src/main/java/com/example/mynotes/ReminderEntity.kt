package com.example.mynotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: Long = System.currentTimeMillis(),
    val day: Int,
    val month: Int,
    val hour: Int = 9,     // Default 9 AM
    val minute: Int = 0,   // Default :00
    val note: String,
    val isYearly: Boolean
)
