# demo_alarm_manager

A Sample flutter Application that fires an alarm.

### Implementation

This app uses Method Channels to communicate to the native code (kotlin) for andriod.

Flutter side
```dart
// This the method channel in flutter side to fire an alarm
void startAlarmService() async {
    if (TargetPlatform.android != null) {
      var methodChannel = MethodChannel("cheeseball.demo_alarm_manager");
      String data =
          await methodChannel.invokeMethod("startAlarm", timeInMillies);
      debugPrint(data);
    }
  }
  //This is method channel in flutter side to delete an alarm
  void deleteAlarm() async {
    if (TargetPlatform.android != null) {
      var methodChannel = MethodChannel("cheeseball.demo_alarm_manager");
      String data = await methodChannel.invokeMethod("deleteAlarm");
      debugPrint(data);
    }
  }
  ```
  Andriod side
  ```kotlin
  class MainActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
        
        //The method channel invoked at andriod side.
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
    // alarm_service is a broad cast reciever.
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
```
### Screen Shots
<img src = "Screenshot_20200317-033228.png" height = "500em"/> <img src = "Screenshot_20200317-033235.png" height = "500em"/>
<img src = "Screenshot_20200317-033311.png" height = "500em"/>



