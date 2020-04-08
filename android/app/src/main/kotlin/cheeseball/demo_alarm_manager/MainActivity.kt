package cheeseball.demo_alarm_manager

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var intentAlarm: Intent
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        intentAlarm = Intent(this, alarm_service::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intentAlarm, 0)

        MethodChannel(flutterView, "cheeseball.demo_alarm_manager")
                .setMethodCallHandler { call, result ->
                    if (call.method == "startAlarm") {
                        val time = (call.arguments) as List<Any>
                        setAlarm(time[0] as Long)
                        result.success("Alarm is set successfully")
                    } else if (call.method == "deleteAlarm") {
                        deleteAlarm()
                        result.success("Alarm is deleted")
                    }
                }


    }

    private fun setAlarm(timeinMillies: Long) {
<<<<<<< HEAD
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeinMillies, AlarmManager.INTERVAL_DAY, pendingIntent)
=======
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeinMillies , AlarmManager.INTERVAL_DAY, pendingIntent)
>>>>>>> ea18daf1c8fe1f20995662eb483d786060f868e8
    }

    private fun deleteAlarm() {
        alarmManager.cancel(pendingIntent)
    }


}