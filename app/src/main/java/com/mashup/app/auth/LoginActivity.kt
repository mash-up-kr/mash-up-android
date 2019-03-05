package com.mashup.app.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mashup.R
import com.mashup.app.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            var email : String = etEmail.text.toString()
            var password : String = etPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.v("signInWithEmail:success")
                        Toast.makeText(baseContext, "Authentication successed.",
                            Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w(task.exception, "signInWithEmail:failure")
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }

                    // ...
                }
        }
    }

    fun moveToHome(){
        val homeIntent : Intent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }
    companion object {
    }
}
