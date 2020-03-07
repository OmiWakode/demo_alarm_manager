package cheeseball.demo_alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity: FlutterActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)

    MethodChannel(flutterView, "cheeseball.demo_alarm_manager")
            .setMethodCallHandler{call , result->
                if (call.method=="startAlarm"){
                    setAlarm(call.arguments as Long)
                    result.success("Successful")
                }
            }



  }
    private fun setAlarm(timeinMillies : Long){
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, alarm_service::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeinMillies,AlarmManager.INTERVAL_DAY,pendingIntent)
        Toast.makeText(this,"Alarm is set", Toast.LENGTH_SHORT).show()
    }

}
