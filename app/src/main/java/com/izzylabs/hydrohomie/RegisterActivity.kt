package com.izzylabs.hydrohomie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val TAG = "MyMessage"

        Signup_button.setOnClickListener {

            val email: String = email_Edit.text.toString()
            val password: String = password_Edit.text.toString()
            val db = FirebaseFirestore.getInstance()
            val user: MutableMap<String, Any> = HashMap()
                    if(identicalPassword()) {
                        val user: MutableMap<String, Any> = HashMap()
                        user["first"] = name_Edit.text.toString()
                        user["last_name"] = lastName_Edit.text.toString()
                        user["height"] = height_Edit.text.toString()
                        user["weight"] = weight_Edit.text.toString()
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                db.collection("users").document(FirebaseAuth.getInstance().currentUser.uid)
                                    .set(user)
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                                Toast.makeText(
                                    this,
                                    "The account has been created, redirecting...",
                                    Toast.LENGTH_SHORT
                                )
                                startActivity(Intent(this, Dashboard::class.java))
                                finish()

                            } else {
                                Toast.makeText(
                                    this,
                                    "Registration failed " + task.exception.toString(),
                                    Toast.LENGTH_SHORT
                                )

                            }
                        }

                }


            }

        }


    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        user?.let {
            startActivity(Intent(this, Dashboard::class.java))
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show()
        }


    }
    fun isNotEmpty() : Boolean = email_Edit.text.toString().trim().isNotEmpty() && password_Edit.text.toString().trim().isNotEmpty() && repeat_password_Edit.text.toString().trim().isNotEmpty()
            && name_Edit.text.toString().trim().isNotEmpty() && lastName_Edit.text.toString().trim().isNotEmpty() && weight_Edit.text.toString().trim().isNotEmpty() &&
            height_Edit.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (isNotEmpty() &&
            password_Edit.text.toString().trim() == repeat_password_Edit.text.toString().trim()
        ) {
            identical = true
        } else if (!isNotEmpty()) {
           Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
        }
        return identical
    }

}