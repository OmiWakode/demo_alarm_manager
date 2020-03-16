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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        MethodChannel(flutterView, "cheeseball.demo_alarm_manager")
                .setMethodCallHandler { call, result ->
                    if (call.method == "startAlarm") {
                        setAlarm(call.arguments as Long)
                        result.success("Alarm is set successfully")
                    } else if (call.method == "deleteAlarm") {
                        deleteAlarm()
                        result.success("Alarm is deleted")
                    }
                }


    }

    private val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intentAlarm = Intent(this, alarm_service::class.java)
    private val pendingIntent = PendingIntent.getBroadcast(this, 0, intentAlarm, 0)

    private fun setAlarm(timeinMillies: Long) {
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeinMillies, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
    }

    private fun deleteAlarm() {
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm is deleted!", Toast.LENGTH_SHORT).show()
    }


}
