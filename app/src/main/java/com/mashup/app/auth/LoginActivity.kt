package com.mashup.app.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.BaseMvRxActivity
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import com.google.firebase.auth.FirebaseAuth
import com.mashup.R
import com.mashup.app.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : BaseMvRxActivity() {

    private val signInViewModel by viewModel(SignInViewModel::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInViewModel.asyncSubscribe(this, SignInState::signInRequest) {
            // TODO hide progress and move to home
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email : String = etEmail.text.toString()
            val password : String = etPassword.text.toString()

            signInViewModel.signIn(email, password)
            // TODO show progress
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser?.let(signInViewModel::signIn)
    }
}
