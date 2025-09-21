package com.example.tunaandme
import com.example.tunaandme.R

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private val truthQuestions = listOf(
        "En son yalan söylediğin zamanı anlat?",
        "En büyük korkun nedir?",
        "Birine aşık oldun mu hiç?",
        "En utanç verici anını paylaş."
    )

    private val dareTasks = listOf(
        "10 şınav çek!",
        "Yanındaki kişiye komik bir şarkı söyle.",
        "Telefonunu rastgele birine mesaj gönder.",
        "Birine tatlı bir şaka yap."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResult: TextView? = findViewById(R.id.tvResult)
        val type = intent.getStringExtra("type") ?: "truth"
        val randomItem = if (type == "truth") truthQuestions.random() else dareTasks.random()
        tvResult?.text = randomItem
    }
}
