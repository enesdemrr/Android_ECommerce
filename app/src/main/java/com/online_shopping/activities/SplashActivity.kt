package com.online_shopping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.online_shopping.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var bind : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getSupportActionBar()?.hide()
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)
        //create a thread for sleep
        val splash = object : Thread(){
            override fun run() {
                try{
                    sleep(3000)
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }catch(e: Exception){
                    Toast.makeText(this@SplashActivity, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
        splash.start()
    }
}