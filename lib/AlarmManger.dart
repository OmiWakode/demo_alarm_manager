import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Alarm extends StatefulWidget {
  @override
  _AlarmState createState() => _AlarmState();
}

class _AlarmState extends State<Alarm> {
  bool _timeSet = false;
  TimeOfDay _time = TimeOfDay.now();
  var _day = DateTime
      .now()
      .day;
  var _month = DateTime
      .now()
      .month;
  var _year = DateTime
      .now()
      .year;
  int _hour;
  int _minute;
  var timeInMillies;


  void startAlarmService(BuildContext context) async {
    var methodChannel = MethodChannel("cheeseball.demo_alarm_manager");
    String data =
    await methodChannel.invokeMethod(
        "startAlarm", [timeInMillies, _hour, _minute]);
    debugPrint(data);
    showToast(context, data);
  }

  void deleteAlarm(BuildContext context) async {
    var methodChannel = MethodChannel("cheeseball.demo_alarm_manager");
    String data = await methodChannel.invokeMethod("deleteAlarm");
    debugPrint(data);
    showToast(context, data);
  }

  void showToast(BuildContext context, String data) {
    Scaffold.of(context).showSnackBar(SnackBar(content: Text(data),));
  }

  void _selectTime(BuildContext context) async {
    TimeOfDay picked =
    await showTimePicker(context: context, initialTime: _time);

    if (picked != null && picked != _time) {
      print("Time Current: ${_time.toString().substring(10, 15)}");
      setState(() {
        _time = picked;
        _timeSet = true;
        _hour = int.parse(_time.toString().substring(10, 12));
        _minute = int.parse(_time.toString().substring(13, 15));
        timeInMillies = DateTime(_year, _month, _day, _hour, _minute)
            .millisecondsSinceEpoch;
        print(timeInMillies);
        startAlarmService(context);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Alarm Manager")),
      body: Builder(
        builder: (context) =>
            Container(
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    _timeSet
                        ? Text(_time.toString().substring(10, 15))
                        : Container(),
                    RaisedButton(
                        child: Text('Select Time'),
                        onPressed: () {
                          _selectTime(context);
                        }),
                    _timeSet
                        ? RaisedButton(
                      child: Text(
                          "Delete Alarm for ${_time.toString().substring(
                              10, 15)}"),
                      onPressed: () {
                        setState(() {
                          _timeSet = false;
                        });
                        deleteAlarm(context);
                      },
                    )
                        : Container()
                  ],
                ),
              ),
            ),
      ),
    );
  }
}
