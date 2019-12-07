package com.szczurk3y.notificationtranslator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SwapActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(DirectReplyReceiver.swaped) {
            true -> {
                DirectReplyReceiver.swaped = false
                MainActivity.newNotification(
                    DirectReplyReceiver.replyResult,
                    DirectReplyReceiver.translatedWord,
                    Settings.currentLanguage_spinner.selectedItem.toString(),
                    Settings.foreignLanguage_spinner.selectedItem.toString()
                )
            }
            false -> {
                DirectReplyReceiver.swaped = true
                MainActivity.newNotification(
                    DirectReplyReceiver.translatedWord,
                    DirectReplyReceiver.replyResult,
                    Settings.foreignLanguage_spinner.selectedItem.toString(),
                    Settings.currentLanguage_spinner.selectedItem.toString()
                )
            }
        }

    }
}