package com.example.lost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID:String=""
    lateinit var editTextUserName: EditText
    lateinit var sign_up_Text_Email_Address: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextAddress: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editTextUserName=findViewById(R.id.editTextUserName)
        sign_up_Text_Email_Address=findViewById(R.id.sign_up_Text_Email_Address)
        editTextPassword=findViewById(R.id.editTextPassword)
        editTextAddress=findViewById(R.id.editTextAddress)

        findViewById<Button>(R.id.btn_signup).setOnClickListener {
           signInUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

            findViewById<Button>(R.id.alreadyaccount).setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
mAuth= FirebaseAuth.getInstance()

    }

    private fun signInUser() {

        val username:String=editTextUserName.text.toString()
        val email:String=sign_up_Text_Email_Address.text.toString()
        val password:String=editTextPassword.text.toString()
        val address:String=editTextAddress.text.toString()

        if(username==("")){
            editTextUserName.error="Please Enter User Name"
            return
        }
        if(email.equals("")){
            sign_up_Text_Email_Address.error="Please Enter Email Address"
            return
        }
        if(password.equals("")){
            editTextPassword.error="Please Enter Password"
            return
        }
        if(address.equals("")){
            editTextAddress.error="Please Enter Your Location"
            return
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful) {
                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users")
                            .child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = username

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this@SignUp, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                    }
                    else
                    {
                        Toast.makeText(this@SignUp,"Error Message:"+task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}