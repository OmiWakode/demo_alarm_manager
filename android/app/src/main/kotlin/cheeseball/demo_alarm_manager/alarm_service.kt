package cheeseball.demo_alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings

class alarm_service : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val media = MediaPlayer.create(p0, Settings.System.DEFAULT_RINGTONE_URI)
        media.start()
    }
}