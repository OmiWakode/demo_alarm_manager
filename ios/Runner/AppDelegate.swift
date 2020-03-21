import UIKit
import Flutter
import UserNotifications

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?
  ) -> Bool {
    let center = UNUserNotificationCenter.current()
        center.delegate = self
        center.requestAuthorization(options: [.alert, .badge, .sound]) { (granted, error) in
            if granted {
                print("Permission granted")
            } else {
                print("Permission denied\n")
            }
    let controller = window?.rootViewController as! FlutterViewController
    let channel = FlutterMethodChannel(name: "cheeseball.demo_alarm_manager", binaryMessenger: controller)
        channel.setMethodCallHandler({
            (call: FlutterMethodCall, result: FlutterResult) -> Void in
            guard call.method == "startAlarm"  else {
                result(FlutterMethodNotImplemented)
                return
            }
            let arg = (channel.arguments as! [Array]). first else { return }
            if (call.method== "startAlarm"){
              self.startAlarmService(result: result)

            }
        })


    GeneratedPluginRegistrant.register(with: self)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
   private func startAlarmService(result: FlutterResult) {
      let center = UNUserNotificationCenter.current()
      let content = UNMutableNotificationContent()
      content.title = "Alarm"
      content.body = "Lots of text"
      content.sound = UNNotificationSound.default()
      content.categoryIdentifier = "Local Notification"
      content.userInfo = ["example": "information"]

      var date = DateComponents()
      date.hour = arg[1]
      date.minute = arg[2]
      let trigger = UNCalendarNotificationTrigger(dateMatching: date, repeats: true)

      let trigger = UNCalendarNotificationTrigger(dateMatching: FlutterResult, repeats: true)
      let request = UNNotificationRequest(content: content, trigger: trigger)
       center.add(request)
}
