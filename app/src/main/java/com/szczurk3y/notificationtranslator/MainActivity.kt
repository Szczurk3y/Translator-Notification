package com.szczurk3y.notificationtranslator
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.RemoteInput
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@SuppressLint("StaticFieldLeak")
object Settings {
    lateinit var CONTEXT: Context
    lateinit var currentLanguage_spinner: Spinner
    lateinit var foreignLanguage_spinner: Spinner
    const val CHANNEL_ID: String = "com.szczurk3y.notificationtranslator"
    const val KEY_TEXT_REPLY: String = "key_text_reply"
    const val REQUEST_CODE: Int = 10
}

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Settings.CONTEXT = this
        val languagesList = arrayOf("POLSKI", "ANGIELSKI", "CHINSKI")
        val button = findViewById<Button>(R.id.buttonStartNotification)

        Settings.currentLanguage_spinner = findViewById<Spinner>(R.id.currentLanguage_spinner)
        Settings.foreignLanguage_spinner = findViewById<Spinner>(R.id.foreignLanguage_spinner)

        ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languagesList
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Settings.currentLanguage_spinner.adapter = it
            Settings.foreignLanguage_spinner.adapter = it
        }

        button!!.setOnClickListener {
            val notificaitonManager = NotificationManagerCompat.from(this)
            notificaitonManager.cancelAll()
            newNotification("Word")
        }
    }

     companion object {
         @SuppressLint("DefaultLocale")
         fun newNotification(currentWord: String, translatedWord: String = "Translation") {
             val activityIntent: Intent = Intent(Settings.CONTEXT, this::class.java)
             val contentIntent: PendingIntent = PendingIntent.getActivity(Settings.CONTEXT, Settings.REQUEST_CODE, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

             val remoteInput: RemoteInput = RemoteInput.Builder(Settings.KEY_TEXT_REPLY).setLabel("type new word").build()
             val replyIntent = Intent(Settings.CONTEXT, DirectReplyReceiver::class.java)
             val replyPendingIntent = PendingIntent.getBroadcast(Settings.CONTEXT, Settings.REQUEST_CODE, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

             val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                 R.drawable.reply,
                 "new",
                 replyPendingIntent
             ).addRemoteInput(remoteInput).build()


             val notification = NotificationCompat.Builder(Settings.CONTEXT, Settings.CHANNEL_ID)
                 .setSmallIcon(R.drawable.reply)
                 .addAction(replyAction)
                 .setColor(Color.BLUE)
                 .setContentTitle("${Settings.currentLanguage_spinner.selectedItem.toString().toLowerCase()} -> ${Settings.foreignLanguage_spinner.selectedItem.toString().toLowerCase()}")
                 .setContentText("${currentWord.toUpperCase()} -> ${translatedWord.toUpperCase()}")
                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                 .setContentIntent(contentIntent)
                 .setOnlyAlertOnce(true)
                 .build();

             val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(Settings.CONTEXT)
             notificationManager.notify(1, notification)
         }
     }
}
