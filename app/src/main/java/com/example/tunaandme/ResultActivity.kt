package com.example.tunaandme

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private val truthQuestions = listOf(
        "En son yalan söylediğin zamanı anlat?",
        "En büyük korkun nedir?",
        "Birine aşık oldun mu hiç?"
    )

    private val dareTasks = listOf(
        "10 şınav çek!",
        "Yanındaki kişiye komik bir şarkı söyle.",
        "Telefonunu rastgele birine mesaj gönder."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Eğer layout adı activity_result2 ise aşağıdaki satırı değiştir:
        setContentView(R.layout.activity_result)

        val tvResult: TextView? = findViewById(R.id.tvResult)

        val type = intent.getStringExtra("type") ?: "truth"
        val randomItem = if (type == "truth") truthQuestions.random() else dareTasks.random()

        tvResult?.text = randomItem
    }
}
