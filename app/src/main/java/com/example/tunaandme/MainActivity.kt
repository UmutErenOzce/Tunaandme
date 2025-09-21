package com.example.saka

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var openingMedia: MediaPlayer
    private lateinit var loginMedia: MediaPlayer
    private val randomSounds = listOf(
        R.raw.s0, R.raw.s1, R.raw.s2, R.raw.s3, R.raw.s4,
        R.raw.s5, R.raw.s6, R.raw.s7, R.raw.s8, R.raw.s9
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Otomatik ses 3 saatte bir çalışacak
        scheduleAutoSound(this)

        // Açılış ve giriş sesleri
        openingMedia = MediaPlayer.create(this, R.raw.acilis)
        loginMedia = MediaPlayer.create(this, R.raw.giris)
        openingMedia.start()

        setContent {
            var showHome by remember { mutableStateOf(false) }
            if (showHome) {
                HomeScreen()
            } else {
                OpeningScreen { showHome = true }
            }
        }
    }

    @Composable
    fun OpeningScreen(onFinish: () -> Unit) {
        val helloText = "Hello"
        var displayText by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            delay(500)
            for (i in helloText.indices) {
                displayText += helloText[i]
                delay(500)
            }
            delay(1000)
            openingMedia.release()
            onFinish()
        }

        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayText,
                fontSize = 80.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.el_yazisi))
            )
        }
    }

    @Composable
    fun HomeScreen() {
        var displayText by remember { mutableStateOf("") }
        val fullText = "Giriş başarılı, hoş geldiniz efendim!"
        var showEasterEgg by remember { mutableStateOf(false) }
        var userMessage by remember { mutableStateOf("") }
        var botMessages by remember { mutableStateOf(mutableListOf<String>()) }

        // 5 saniyede bir rastgele ses çalma
        LaunchedEffect(Unit) {
            while (true) {
                delay(5000)
                val randomRes = randomSounds.random()
                val media = MediaPlayer.create(this@MainActivity, randomRes)
                media.start()
                media.setOnCompletionListener { it.release() }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // Duvar kağıdı
            val wallpaper: Painter = painterResource(R.drawable.wallpaper)
            Image(painter = wallpaper, contentDescription = null, modifier = Modifier.fillMaxSize())

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = displayText,
                    fontSize = 28.sp,
                    color = Color.Cyan
                )

                LaunchedEffect(Unit) {
                    loginMedia.start()
                    for (i in fullText.indices) {
                        displayText += fullText[i]
                        delay(150)
                    }
                    loginMedia.release()
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Chat alanı
                Column(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                        .background(Color(0xAA000000), RoundedCornerShape(15.dp))
                        .padding(8.dp)
                        .clickable { showEasterEgg = !showEasterEgg }
                ) {
                    botMessages.forEach { msg ->
                        Text(
                            text = msg,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        placeholder = { Text("Mesaj yaz...") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (userMessage.isNotBlank()) {
                            botMessages.add("Sen: $userMessage")
                            botMessages.add("Bot: ${generateBotReply(userMessage)}")
                            userMessage = ""
                        }
                    }) {
                        Text("Gönder")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        botMessages.clear()
                        userMessage = ""
                    }) {
                        Text("Temizle")
                    }
                }

                if (showEasterEgg) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "🎉 Gizli Easter Egg bulundu! 🎉",
                        fontSize = 22.sp,
                        color = Color.Yellow
                    )
                }
            }
        }
    }

    private fun generateBotReply(message: String): String {
        val lower = message.lowercase()
        return when {
            listOf("merhaba", "selam").any { lower.contains(it) } -> "Merhaba! 😊"
            listOf("nasılsın").any { lower.contains(it) } -> "İyiyim, sen nasılsın?"
            listOf("olumlu").any { lower.contains(it) } -> "Anladım, olumlu düşünüyorsun."
            listOf("olumsuz").any { lower.contains(it) } -> "Hmm, olumsuz hissediyorsun gibi."
            listOf("soru").any { lower.contains(it) } -> "Bunu soruyorsun demek…"
            else -> "Hiçbir kelimeyi anlamadım 😅"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (openingMedia.isPlaying) openingMedia.release()
        if (loginMedia.isPlaying) loginMedia.release()
    }
}
