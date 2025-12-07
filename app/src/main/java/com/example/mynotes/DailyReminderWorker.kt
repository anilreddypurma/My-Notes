package com.example.mynotes

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.datetime.LocalDate

class DailyReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        val todayReminders = db.remindersDao().getRemindersForDate(today.monthNumber, today.dayOfMonth)
        val tomorrowYearly = db.remindersDao().getYearlyRemindersForDate(tomorrow.monthNumber, tomorrow.dayOfMonth)

        NotificationHelper.createNotificationChannel(applicationContext)

        var id = 1001
        for (r in todayReminders) {
            NotificationHelper.showNotification(
                applicationContext,
                "🎉 Reminder Today",
                r.note,
                id++
            )
        }

        for (r in tomorrowYearly) {
            NotificationHelper.showNotification(
                applicationContext,
                "🔔 Reminder Tomorrow",
                "Tomorrow: ${r.note}",
                id++
            )
        }

        return Result.success()
    }
}
