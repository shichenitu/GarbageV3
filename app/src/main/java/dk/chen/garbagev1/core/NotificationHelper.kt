package dk.chen.garbagev1.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dk.chen.garbagev1.MainActivity
import dk.chen.garbagev1.R
import kotlin.apply
import kotlin.jvm.java
import javax.inject.Inject
import javax.inject.Singleton
import android.os.Build

@Singleton
class NotificationHelper @Inject constructor(){
    private val CHANNEL_ID = "recycling_notifications"

    fun showNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int
    ) {
        val notificationManager =
            context.getSystemService(/* p0 = */ Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the channel (required for Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                /* id = */ CHANNEL_ID,
                /* name = */ "Recycling Reminders",
                /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for deadlines and recycling status"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(/* packageContext = */ context, /* cls = */ MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            /* context = */ context,
            /* requestCode = */ 0,
            /* intent = */ intent,
            /* flags = */ PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT // This flag is strictly required for Android 12+
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Dismisses the notification when clicked
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}