package com.mashup.app.notices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.databinding.NoticesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoticesFragment : Fragment() {

    companion object {
        fun newInstance() = NoticesFragment()
    }

    private val viewModel: NoticesViewModel by viewModel()
    private lateinit var viewDataBinding: NoticesFragmentBinding
    private lateinit var listAdapter: NoticeAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewDataBinding = NoticesFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = NoticeAdapter(viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
        }
    }

}
