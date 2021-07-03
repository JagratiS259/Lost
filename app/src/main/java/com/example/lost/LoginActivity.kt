package com.example.lost

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.prefs.AbstractPreferences

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences=getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        isRemembered=sharedPreferences.getBoolean("CHECKBOX", false)

        if(isRemembered) {
            val intent = Intent(this, DashboardFragment::class.java)
            startActivity(intent)
            finish()
        }
        
        }
    }
