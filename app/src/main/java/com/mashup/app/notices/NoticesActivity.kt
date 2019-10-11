package com.mashup.app.notices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.R

class NoticesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notices_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NoticesFragment.newInstance())
                    .commitNow()
        }
    }

}
