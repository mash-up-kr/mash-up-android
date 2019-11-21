package com.mashup.app.attendees

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.R

class AttendeesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attendees_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AttendeesFragment.newInstance(
                    intent.getParcelableArrayListExtra(AttendeesFragment.EXTRA_ATTENDANCE_LIST))
                )
                .commitNow()
        }
    }

}
