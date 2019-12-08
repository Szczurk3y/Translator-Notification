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
import com.mannan.translateapi.Language
import com.mannan.translateapi.TranslateAPI

class DirectReplyReceiver : BroadcastReceiver() {
    companion object {
        var replyResult: String = "WORD"
        var translatedWord: String = "TRANSLATION"
        var swaped: Boolean = false
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val results = RemoteInput.getResultsFromIntent(intent)
        if (results != null) {
            val quickReplyResult = results.getCharSequence(Settings.KEY_TEXT_REPLY)
            val notificationManager = NotificationManagerCompat.from(context!!)
            notificationManager.cancelAll()

            val translateAPI: TranslateAPI
            when(swaped) {
                false -> {
                    translateAPI = TranslateAPI(
                        Settings.languagesShortcuts.get(Settings.currentLanguage_spinner.selectedItemId.toInt()),
                        Settings.languagesShortcuts.get(Settings.foreignLanguage_spinner.selectedItemId.toInt()),
                        quickReplyResult!!.toString())
                }
                true -> {
                    translateAPI = TranslateAPI(
                        Settings.languagesShortcuts.get(Settings.foreignLanguage_spinner.selectedItemId.toInt()),
                        Settings.languagesShortcuts.get(Settings.currentLanguage_spinner.selectedItemId.toInt()),
                        quickReplyResult!!.toString())
                }
            }

            translateAPI.setTranslateListener(object:TranslateAPI.TranslateListener {
                override fun onSuccess(translatedText: String?) {
                    when(swaped) {
                        false -> {
                            replyResult = quickReplyResult.toString()
                            translatedWord = translatedText!!
                            MainActivity.newNotification(
                                quickReplyResult.toString(),
                                translatedText,
                                Settings.currentLanguage_spinner.selectedItem.toString(),
                                Settings.foreignLanguage_spinner.selectedItem.toString()
                            )
                        }
                        true -> {
                            replyResult = translatedText!!
                            translatedWord = quickReplyResult.toString()
                            MainActivity.newNotification(
                                quickReplyResult.toString(),
                                translatedText,
                                Settings.foreignLanguage_spinner.selectedItem.toString(),
                                Settings.currentLanguage_spinner.selectedItem.toString()
                            )
                        }
                    }

                }

                override fun onFailure(failureText: String?) {
                    MainActivity.newNotification(quickReplyResult.toString(), failureText.toString())
                }
            })
        }
    }
}
