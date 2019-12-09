package com.szczurk3y.notificationtranslator
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.RemoteInput
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mannan.translateapi.Language

@SuppressLint("StaticFieldLeak")
object Settings {
    lateinit var CONTEXT: Context
    lateinit var currentLanguage_spinner: Spinner
    lateinit var foreignLanguage_spinner: Spinner
    lateinit var languagesShortcuts: MutableList<String>
    const val CHANNEL_ID: String = "com.szczurk3y.notificationtranslator"
    const val KEY_TEXT_REPLY: String = "key_text_reply"
    const val REQUEST_CODE: Int = 10
    const val NAME: String = "cos"
}

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Settings.CONTEXT = this
        Settings.languagesShortcuts = mutableListOf<String>().addLanguagesShortcuts()
        val languagesFullNames = mutableListOf<String>().addLanguages()
        val button = findViewById<Button>(R.id.buttonStartNotification)

        Settings.currentLanguage_spinner = findViewById<Spinner>(R.id.currentLanguage_spinner)
        Settings.foreignLanguage_spinner = findViewById<Spinner>(R.id.foreignLanguage_spinner)

        ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languagesFullNames
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Settings.currentLanguage_spinner.adapter = it
            Settings.foreignLanguage_spinner.adapter = it
        }

        button!!.setOnClickListener {
            newNotification()
        }
    }

    fun List<String>.addLanguages(): MutableList<String> {
        val list = mutableListOf<String>()
        list.add("POLISH")
        list.add("ENGLISH")

        list.add("AFRIKAANS")
        list.add("ALBANIAN")
        list.add("BASQUE")
        list.add("BENGALI")
        list.add("BULGARIAN")
        list.add("CATALAN")
        list.add("CHINESE")
        list.add("CROATIAN")
        list.add("CZECH")
        list.add("DANISH")
        list.add("DUTCH")
        list.add("AUTO_DETECT")
        list.add("ESTONIAN")
        list.add("FILIPINO")
        list.add("FINNISH")
        list.add("FRENCH")
        list.add("GALICIAN")
        list.add("GEORGIAN")
        list.add("GERMAN")
        list.add("GREEK")
        list.add("GUJARATI")
        list.add("HAITIAN_CREOLE")
        list.add("HEBREW")
        list.add("HINDI")
        list.add("HUNGARIAN")
        list.add("ICELANDIC")
        list.add("INDONESIAN")
        list.add("IRISH")
        list.add("ITALIAN")
        list.add("JAPANESE")
        list.add("KANNADA")
        list.add("KOREAN")
        list.add("LATIN")
        list.add("LATVIAN")
        list.add("LITHUANIAN")
        list.add("MACEDONIAN")
        list.add("MALAY")
        list.add("MALTESE")
        list.add("NORWEGIAN")
        list.add("PORTUGUESE")
        list.add("ROMANIAN")
        list.add("SLOVAK")
        list.add("SPANISH")
        list.add("SWAHILI")
        list.add("SWEDISH")
        list.add("TAMIL")
        list.add("TELUGU")
        list.add("THAI")
        list.add("TURKISH")
        list.add("UKRAINIAN")
        list.add("URDU")
        list.add("VIETNAMESE")
        list.add("WELSH")
        list.add("YIDDISH")
        list.add("CHINESE_SIMPLIFIED")
        list.add("CHINESE_TRADITIONAL")
        return list
    }

    fun List<String>.addLanguagesShortcuts(): MutableList<String> {
        val list = mutableListOf<String>()
        list.add(Language.POLISH)
        list.add(Language.ENGLISH)
        list.add(Language.AFRIKAANS)
        list.add(Language.ALBANIAN)
        list.add(Language.BASQUE)
        list.add(Language.BENGALI)
        list.add(Language.BULGARIAN)
        list.add(Language.CATALAN)
        list.add(Language.CHINESE)
        list.add(Language.CROATIAN)
        list.add(Language.CZECH)
        list.add(Language.DANISH)
        list.add(Language.DUTCH)
        list.add(Language.AUTO_DETECT)
        list.add(Language.ESTONIAN)
        list.add(Language.FILIPINO)
        list.add(Language.FINNISH)
        list.add(Language.FRENCH)
        list.add(Language.GALICIAN)
        list.add(Language.GEORGIAN)
        list.add(Language.GERMAN)
        list.add(Language.GREEK)
        list.add(Language.GUJARATI)
        list.add(Language.HAITIAN_CREOLE)
        list.add(Language.HEBREW)
        list.add(Language.HINDI)
        list.add(Language.HUNGARIAN)
        list.add(Language.ICELANDIC)
        list.add(Language.INDONESIAN)
        list.add(Language.IRISH)
        list.add(Language.ITALIAN)
        list.add(Language.JAPANESE)
        list.add(Language.KANNADA)
        list.add(Language.KOREAN)
        list.add(Language.LATIN)
        list.add(Language.LATVIAN)
        list.add(Language.LITHUANIAN)
        list.add(Language.MACEDONIAN)
        list.add(Language.MALAY)
        list.add(Language.MALTESE)
        list.add(Language.NORWEGIAN)
        list.add(Language.PORTUGUESE)
        list.add(Language.ROMANIAN)
        list.add(Language.SLOVAK)
        list.add(Language.SPANISH)
        list.add(Language.SWAHILI)
        list.add(Language.SWEDISH)
        list.add(Language.TAMIL)
        list.add(Language.TELUGU)
        list.add(Language.THAI)
        list.add(Language.TURKISH)
        list.add(Language.UKRAINIAN)
        list.add(Language.URDU)
        list.add(Language.VIETNAMESE)
        list.add(Language.WELSH)
        list.add(Language.YIDDISH)
        list.add(Language.CHINESE_SIMPLIFIED)
        list.add(Language.CHINESE_TRADITIONAL)
        return list
    }

     companion object {
         @SuppressLint("DefaultLocale")
         fun newNotification(currentWord: String = "Word", translatedWord: String = "Translation",
                             current_language: String = Settings.currentLanguage_spinner.selectedItem.toString(),
                             foreign_language: String = Settings.foreignLanguage_spinner.selectedItem.toString()) {

             val activityIntent: Intent = Intent(Settings.CONTEXT, this::class.java)
             val contentIntent: PendingIntent = PendingIntent.getActivity(Settings.CONTEXT, Settings.REQUEST_CODE, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

             val remoteInput: RemoteInput = RemoteInput.Builder(Settings.KEY_TEXT_REPLY).setLabel("type new word").build()

             val replyIntent = Intent(Settings.CONTEXT, DirectReplyReceiver::class.java)
             val replyPendingIntent = PendingIntent.getBroadcast(
                 Settings.CONTEXT,
                 Settings.REQUEST_CODE,
                 replyIntent,
                 PendingIntent.FLAG_UPDATE_CURRENT)
             val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                 R.drawable.reply,
                 "new",
                 replyPendingIntent
             ).addRemoteInput(remoteInput).build()


             val copyIntent = Intent(Settings.CONTEXT, CopyActionReceiver::class.java)
             val copyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
                 Settings.CONTEXT,
                 Settings.REQUEST_CODE,
                 copyIntent,
                 PendingIntent.FLAG_UPDATE_CURRENT
             )
             val copyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                 R.drawable.ic_content_copy_black_24dp,
                 "copy",
                 copyPendingIntent
             ).build()


             val swapIntent = Intent(Settings.CONTEXT, SwapActionReceiver::class.java)
             val swapPendingIntent: PendingIntent = PendingIntent.getBroadcast(
                 Settings.CONTEXT,
                 Settings.REQUEST_CODE,
                 swapIntent,
                 PendingIntent.FLAG_UPDATE_CURRENT
             )
             val swapAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                 R.drawable.ic_compare_arrows_black_24dp,
                 "swap",
                 swapPendingIntent
             ).build()

             val notification = NotificationCompat.Builder(Settings.CONTEXT, Settings.CHANNEL_ID)
                 .setSmallIcon(R.drawable.ic_compare_arrows_black_24dp)
                 .addAction(copyAction)
                 .addAction(swapAction)
                 .addAction(replyAction)
                 .setColor(Color.BLUE)
                 .setContentTitle("${currentWord.toUpperCase()} -> ${translatedWord.toUpperCase()}")
                 .setContentText("${current_language.toLowerCase()} -> ${foreign_language.toLowerCase()}")
                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                 .setContentIntent(contentIntent)
                 .setOnlyAlertOnce(true)
                 .build();



             val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(Settings.CONTEXT)
             notificationManager.cancelAll()

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 val mChannel: NotificationChannel = NotificationChannel(Settings.CHANNEL_ID, Settings.NAME, NotificationManager.IMPORTANCE_HIGH)
                 notificationManager.createNotificationChannel(mChannel)
             }
             notificationManager.notify(Settings.REQUEST_CODE, notification)
         }
     }
}
