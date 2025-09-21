package com.example.tunaandme
import com.example.tunaandme.R

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTruth: MaterialButton = findViewById(R.id.btnTruth)
        val btnDare: MaterialButton = findViewById(R.id.btnDare)

        btnTruth.setOnClickListener { openResultActivity("truth") }
        btnDare.setOnClickListener { openResultActivity("dare") }
    }

    private fun openResultActivity(type: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }
}
