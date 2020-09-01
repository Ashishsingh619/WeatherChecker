package com.example.weatherforecast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var img_app:ImageView
    lateinit var txt_appName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img_app=findViewById(R.id.img_app)
        txt_appName=findViewById(R.id.txt_appName)
        img_app.animate().alpha(1f).setDuration(3000)
        txt_appName.animate().translationXBy(1000f).setDuration(2000)
        Handler().postDelayed({
            val intent= Intent(this@MainActivity,NavigationActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}
