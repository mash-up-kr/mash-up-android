package com.mashup.app.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mashup.R
import com.mashup.app.home.HomeActivity
import com.mashup.ext.toast
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

            if (email.isEmpty() || !email.contains("@")){
                "알맞은 이메일 서식이 아닙니다.".toast(this)
            }else if (password.isEmpty() || (password.split("").size<8) || (password.split("").size>12)){
                "알맞은 비밀번호 서식이 아닙니다. 8-12자로 설정해주세요.".toast(this)
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Timber.v("signInWithEmail:success")
                            val user = auth.currentUser
                        } else {
                            // TODO : DB 쿼리를 통해 email이 DB 내에 있는지 확인
                            /**
                             * 1) email 이 DB 내에 존재하면, 입력정보가 틀렸음을 알려준다.
                             * 2) email 이 DB 내에 존재하지 않으면, 회원가입으로 유도한다.
                             * 일단은 구냥 회원가입 절차로 넘어간다!
                             * **/
                            Timber.w(task.exception, "signInWithEmail:failure")
                        }

                        // ...
                    }
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
