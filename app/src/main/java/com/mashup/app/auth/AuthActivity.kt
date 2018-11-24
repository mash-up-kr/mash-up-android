package com.mashup.app.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.R
import com.mashup.SplashActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btnAuth.setOnClickListener {
            moveToHome()
        }
    }

    fun moveToHome(){
        //TODO : Need to be fixed; SplashActivity -> HomeActivity
        val homeIntent : Intent = Intent(this, SplashActivity::class.java)
        startActivity(homeIntent)
    }
}
