package com.example.deeprainblackscreen

import android.os.Bundle
import android.view.WindowManager
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import android.widget.Toast
import android.widget.TextView
import android.media.MediaPlayer
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Button
import  android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.preference.PreferenceManager

class MainActivity : ComponentActivity() {

    private lateinit var myTextViewmyHeavyRainLoop_A_B: TextView
    private lateinit var myTextViewmyRain_Drips_A_B: TextView

    private lateinit var myTextViewClock: TextView

    private lateinit var myTextViewMsg: TextView

    private lateinit var mediaPlayer1: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer

    private lateinit var mediaPlayer3: MediaPlayer
    private lateinit var mediaPlayer4: MediaPlayer

    private var SecondsCounter1 = SecondsCounter()
    private var SecondsCounter2 = SecondsCounter()

    private var SecondsCounter3 = SecondsCounter()
    private var SecondsCounter4 = SecondsCounter()

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myTextViewmyHeavyRainLoop_A_B = findViewById(R.id.HeavyRainLoop_A_B)
        myTextViewmyRain_Drips_A_B = findViewById(R.id.Rain_Drips_A_B)
        myTextViewClock = findViewById(R.id.textViewClock)
        myTextViewMsg = findViewById(R.id.textViewMsg)
        val myButton: Button = findViewById(R.id.myButton)
        myButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Hi Sheila!", Toast.LENGTH_SHORT).show()
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        mediaPlayer1 = createMediaPlayer(R.raw.rain_heavy_poor2,false)
        mediaPlayer2 = createMediaPlayer(R.raw.rain_heavy_poor2,false)

        mediaPlayer3 = createMediaPlayer(R.raw.rain_drips,false)
        mediaPlayer4 = createMediaPlayer(R.raw.rain_drips,false)

        SecondsCounter1.start()
        SecondsCounter3.start()

        mediaPlayer1.start()
        mediaPlayer3.start()

        startLoopTimer()

        preference_conditions()

    }

    private fun preference_conditions(){

        // Get the SharedPreferences instance
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        // Retrieve the values from settings
        val isShowClockEnabled = sharedPreferences.getBoolean("show_clock", false)
        val isShowMsgEnabled = sharedPreferences.getBoolean("show_msg", false)
        val isShowAudioTimersEnabled =
            sharedPreferences.getBoolean("show_audio_timers", false)

        if (isShowClockEnabled) {
            myTextViewClock.visibility = View.VISIBLE
        } else {
            myTextViewClock.visibility = View.INVISIBLE
        }

        if (isShowMsgEnabled) {
            myTextViewMsg.visibility = View.VISIBLE
        } else {
            myTextViewMsg.visibility = View.INVISIBLE
        }

        if (isShowAudioTimersEnabled) {
            myTextViewmyHeavyRainLoop_A_B.visibility = View.VISIBLE
            myTextViewmyRain_Drips_A_B.visibility = View.VISIBLE
        } else {
            myTextViewmyHeavyRainLoop_A_B.visibility = View.INVISIBLE
            myTextViewmyRain_Drips_A_B.visibility = View.INVISIBLE
        }
    }
    private fun createMediaPlayer(resId: Int, CanLoop: Boolean): MediaPlayer {
        return MediaPlayer.create(this, resId).apply {
            isLooping = CanLoop
            setVolume(1.0F, 1.0F)
        }
    }
    override fun onPause() {
        super.onPause()
        System.exit(0)
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("h:mm:ss a", Locale.getDefault())
        return sdf.format(Date())
    }
    private fun startLoopTimer() {
        runnable = object : Runnable {
            override fun run() {
                // Main Loop

                myTextViewClock.text = getCurrentTime()

                val CounterLimit1 = 27
                val CounterLimit2 = 55

                val SecondsCounterValue1 = SecondsCounter1.getCounterValue()
                val SecondsCounterValue2 = SecondsCounter2.getCounterValue()

                val SecondsCounterValue3 = SecondsCounter3.getCounterValue()
                val SecondsCounterValue4 = SecondsCounter4.getCounterValue()

                myTextViewmyHeavyRainLoop_A_B.text = "Heavy Rain Loop Timer A & B: $SecondsCounterValue1 | $SecondsCounterValue2"
                myTextViewmyRain_Drips_A_B.text = "Rain Drips Timer A & B: $SecondsCounterValue3 | $SecondsCounterValue4"

                if (SecondsCounterValue1 >= CounterLimit1){
                    Stop_Reset_Start1(2)
                }else if (SecondsCounterValue2 >= CounterLimit1){
                    Stop_Reset_Start1(1)
                }

                if (SecondsCounterValue3 >= CounterLimit2){
                    Stop_Reset_Start2(3)
                }else if (SecondsCounterValue4 >= CounterLimit2){
                    Stop_Reset_Start2(4)
                }


                // Schedule the next execution
                handler.postDelayed(this, 100)
            }
        }
        // Start the loop timer
        handler.post(runnable)
    }

    private fun Stop_Reset_Start1(TimerToStart: Int){
        SecondsCounter1.stop()
        SecondsCounter2.stop()
        SecondsCounter1.reset()
        SecondsCounter2.reset()
        if (TimerToStart == 2) {
            SecondsCounter2.start()
            mediaPlayer2.start()
        } else{
            SecondsCounter1.start()
            mediaPlayer1.start()
        }
    }
    private fun Stop_Reset_Start2(TimerToStart: Int){
        SecondsCounter3.stop()
        SecondsCounter4.stop()
        SecondsCounter3.reset()
        SecondsCounter4.reset()
        if (TimerToStart == 3) {
            SecondsCounter4.start()
            mediaPlayer4.start()
        } else{
            SecondsCounter3.start()
            mediaPlayer3.start()
        }
    }
    private fun Stop_All(){
        SecondsCounter1.stop()
        SecondsCounter2.stop()
    }
}

class SecondsCounter {
    private var counter = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            counter = (counter + 1) % 1000000 // Increment counter and reset to 0 after 25
            handler.postDelayed(this, 1000) // Run every second
        }
    }

    fun start() {
        handler.post(runnable)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }

    fun reset() {
        counter = 0
    }

    fun getCounterValue(): Int {
        return counter
    }
}