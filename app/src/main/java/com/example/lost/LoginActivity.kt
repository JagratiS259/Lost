package com.example.lost

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {


    lateinit var signinPassword:EditText
    lateinit var signinEmailAddress:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signinEmailAddress=findViewById(R.id.signinEmailAddress)
        signinPassword=findViewById(R.id.signinPassword)

        retrieveData()

        findViewById<Button>(R.id.btn_signin).setOnClickListener {
            saveData()
            val intent=Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
        findViewById<Button>(R.id.noaccount_signin).setOnClickListener {
            val intent=Intent(this, SignUp::class.java)
            startActivity(intent)
        }
}
    private fun retrieveData(){
        val mypref=getSharedPreferences("mypref", Context.MODE_PRIVATE)

        val email=mypref.getString("email", "")
        val password=mypref.getString("password", "")

        signinEmailAddress.setText(email)
        signinPassword.setText(password)
    }

    private fun saveData() {
        if(signinEmailAddress.text.isEmpty()){
            signinEmailAddress.error="Please Enter Email Address"
            return
    }
        if(signinPassword.text.isEmpty()){
            signinPassword.error="Please Enter Password"
            return
        }
        val mypref=getSharedPreferences("mypref", MODE_PRIVATE)

        val editor=mypref.edit()
        editor.putString("email",signinEmailAddress.text.toString() )
        editor.putString("password",signinPassword.text.toString() )

        editor.apply()

        Toast.makeText(this,"Data saved", Toast.LENGTH_LONG).show()

}
    }
