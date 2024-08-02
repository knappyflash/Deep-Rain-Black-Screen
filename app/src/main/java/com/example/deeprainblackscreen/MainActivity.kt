package com.example.deeprainblackscreen

import android.os.Bundle
import android.view.WindowManager
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import android.media.MediaPlayer
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var mediaPlayer1: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer
    private lateinit var mediaPlayer4: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Display the live clock time
                LiveClock()
            }
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Initialize and configure MediaPlayer
        mediaPlayer1 = MediaPlayer.create(this, R.raw.rain_heavy_poor)
        mediaPlayer1.isLooping = true // Enable looping
        mediaPlayer1.setVolume(1.0F, 1.0F)
        mediaPlayer1.start() // Start playing the audio

        mediaPlayer2 = MediaPlayer.create(this, R.raw.rain_drips)
        mediaPlayer2.isLooping = true // Enable looping
        mediaPlayer2.setVolume(1.0F, 1.0F)
        mediaPlayer2.start() // Start playing the audio

        handler.postDelayed({
            mediaPlayer3 = MediaPlayer.create(this, R.raw.rain_heavy_poor2)
            mediaPlayer3.isLooping = true // Enable looping
            mediaPlayer3.setVolume(1.0F, 1.0F)
            mediaPlayer3.start() // Start playing the audio
        }, 10000)

        handler.postDelayed({
            mediaPlayer4 = MediaPlayer.create(this, R.raw.rain_drips)
            mediaPlayer4.isLooping = true // Enable looping
            mediaPlayer4.setVolume(1.0F, 1.0F)
            mediaPlayer4.start() // Start playing the audio
        }, 10000)

    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        if (::mediaPlayer1.isInitialized) {
            mediaPlayer1.release()
        }
        if (::mediaPlayer2.isInitialized) {
            mediaPlayer2.release()
        }
        if (::mediaPlayer3.isInitialized) {
            mediaPlayer3.release()
        }
        if (::mediaPlayer4.isInitialized) {
            mediaPlayer4.release()
        }
    }
}

@Composable
fun LiveClock() {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            kotlinx.coroutines.delay(1000L)
        }
    }

    Text(
        text = "$currentTime\nIn peace I will lie down and sleep,\nfor you alone, O LORD, make me dwell in safety.\nPsalm 4:8",
        color = Color.DarkGray,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.background(Color.Black)
    )
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("h:mm:ss a", Locale.getDefault())
    return sdf.format(Date())
}