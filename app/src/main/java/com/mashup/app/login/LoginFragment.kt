package com.mashup.app.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mashup.app.main.MainActivity
import com.mashup.databinding.LoginFragmentBinding
import com.mashup.util.EventObserver
import com.mashup.util.setupSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var viewDataBinding: LoginFragmentBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewDataBinding = LoginFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)
        setupSnackbar()
        setupEventObserver()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupEventObserver() {
        viewModel.isLoginSuccess.observe(this, EventObserver {
            if (it) {
                activity?.finishAffinity()
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
