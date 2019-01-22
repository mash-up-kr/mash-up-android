package com.mashup.app.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.R
import com.mashup.SplashActivity
import com.mashup.app.home.HomeActivity
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
        val homeIntent : Intent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }
}
