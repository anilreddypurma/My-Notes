package com.example.mynotes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {
    @Query("SELECT * FROM reminders")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Insert
    suspend fun insert(reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE month = :month AND day = :day")
    suspend fun getRemindersForDate(month: Int, day: Int): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE isYearly = 1 AND month = :month AND day = :day")
    suspend fun getYearlyRemindersForDate(month: Int, day: Int): List<ReminderEntity>
}
