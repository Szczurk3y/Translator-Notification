package com.szczurk3y.notificationtranslator

import android.content.*
import android.util.Log
import android.widget.Toast

class CopyActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val clipboard: ClipboardManager = Settings.CONTEXT.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData
        when(DirectReplyReceiver.swaped) {
            true -> {
                clip = ClipData.newPlainText("translated text", DirectReplyReceiver.replyResult)
            }
            false -> {
                clip = ClipData.newPlainText("translated text", DirectReplyReceiver.translatedWord)
            }
        }
        clipboard.primaryClip = clip
        Toast.makeText(Settings.CONTEXT, "${DirectReplyReceiver.translatedWord} COPIED", Toast.LENGTH_SHORT).show()
    }
}