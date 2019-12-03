package com.szczurk3y.notificationtranslator
import android.annotation.SuppressLint
import android.app.PendingIntent
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


class MainActivity : AppCompatActivity() {
    companion object {
        const val CHANNEL_ID: String = "com.szczurk3y.notificationtranslator"
        const val KEY_TEXT_REPLY: String = "key_text_reply"
        const val REQUEST_CODE: Int = 10
        @SuppressLint("StaticFieldLeak")
        lateinit var textView: TextView
    }


    lateinit var currentLanguage_spinner: Spinner
    lateinit var foreignLanguage_spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)
        val languagesList = arrayOf("POLSKI", "ANGIELSKI", "CHINSKI")
        val button = findViewById<Button>(R.id.buttonStartNotification)

        currentLanguage_spinner = findViewById<Spinner>(R.id.currentLanguage_spinner)
        foreignLanguage_spinner = findViewById<Spinner>(R.id.foreignLanguage_spinner)

        ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languagesList
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            currentLanguage_spinner.adapter = it
            foreignLanguage_spinner.adapter = it
        }

        button!!.setOnClickListener {
            NewNotification()
        }
    }

     inner class NewNotification() {
         init {
             val activityIntent: Intent = Intent(this@MainActivity, this::class.java)
             val contentIntent: PendingIntent = PendingIntent.getActivity(this@MainActivity, REQUEST_CODE, activityIntent, PendingIntent.FLAG_ONE_SHOT)

             val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).setLabel("Reply").build()
             val replyIntent = Intent(this@MainActivity, DirectReplyReceiver::class.java)
             val replyPendingIntent = PendingIntent.getBroadcast(this@MainActivity, REQUEST_CODE, replyIntent, PendingIntent.FLAG_ONE_SHOT)

             val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                 R.drawable.reply,
                 "Reply",
                 replyPendingIntent
             ).addRemoteInput(remoteInput).build()


             val notification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                 .setSmallIcon(R.drawable.reply)
                 .addAction(replyAction)
                 .setColor(Color.BLUE)
                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                 .setContentIntent(contentIntent)
                 .setOnlyAlertOnce(true)
                 .build();

             val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
             notificationManager.notify(1, notification)
         }
     }
}
