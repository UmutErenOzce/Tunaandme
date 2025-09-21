package com.example.truthdareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTruth: Button = findViewById(R.id.btnTruth)
        val btnDare: Button = findViewById(R.id.btnDare)

        btnTruth.setOnClickListener {
            openResultActivity("truth")
        }

        btnDare.setOnClickListener {
            openResultActivity("dare")
        }
    }

    private fun openResultActivity(type: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }
}
