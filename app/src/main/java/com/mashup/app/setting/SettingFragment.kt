package com.mashup.app.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashup.databinding.SettingFragmentBinding
import com.mashup.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mashup.BuildConfig
import com.mashup.R
import com.mashup.app.login.LoginActivity


class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModel()
    private lateinit var viewDataBinding: SettingFragmentBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewDataBinding = SettingFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)
        setupEventObserver()
    }

    private fun setupEventObserver() {
        viewModel.logoutEvent.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                activity?.finishAffinity()
                val intent = Intent(this.context, LoginActivity::class.java)
                startActivity(intent)
            }
        })

        viewModel.sendEmailEvent.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    val addressees: Array<String> = arrayOf(BuildConfig.FEED_BACK_EMAIL)
                    putExtra(Intent.EXTRA_EMAIL, addressees)
                    putExtra(
                            Intent.EXTRA_SUBJECT,
                            "<" + getString(R.string.app_name) + " " + getString(R.string.setting_send_feedback_email_title) + ">"
                    )
                    putExtra(
                            Intent.EXTRA_TEXT,
                            "앱 버전 (AppVersion):${BuildConfig.VERSION_NAME}\n기기명 (Device):${Build.MODEL}\n안드로이드 OS (Android OS):${Build.VERSION.SDK_INT}\n\n내용 (Content):\n"
                    )
                    type = "message/rfc822"
                }
                startActivity(intent)
            }
        })
    }
}
