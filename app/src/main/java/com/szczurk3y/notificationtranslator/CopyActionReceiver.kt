package com.szczurk3y.notificationtranslator

import android.content.*
import android.util.Log
import android.widget.Toast

class CopyActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val clipboard: ClipboardManager = Settings.CONTEXT.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("translated text", DirectReplyReceiver.replyResult)
        clipboard.primaryClip = clip
        Toast.makeText(Settings.CONTEXT, "COPIED", Toast.LENGTH_SHORT).show()
    }
}