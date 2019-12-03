package com.szczurk3y.notificationtranslator

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat

class DirectReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val results = RemoteInput.getResultsFromIntent(intent)

        Log.d("ELO", "JESTEM W DirectReplyReceiver")

        if (results != null) {
            val quickReplyResult = results.getCharSequence(MainActivity.KEY_TEXT_REPLY)
            Toast.makeText(context, quickReplyResult, Toast.LENGTH_LONG).show()
            val notificationManager = NotificationManagerCompat.from(context!!)
            notificationManager.cancelAll()
            MainActivity.textView.text = quickReplyResult!!.toString()
        }
    }
}