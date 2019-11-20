package com.mashup.app.noticedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashup.databinding.NoticeDetailFragmentBinding
import com.mashup.model.Notice
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoticeDetailFragment(notice: Notice) : Fragment() {

    companion object {
        fun newInstance(notice: Notice) = NoticeDetailFragment(notice)
        const val EXTRA_NOTICE = "ExtraNotice"
        const val REQUEST_NOTICE_ACTION = 1100
    }

    private val viewModel: NoticeDetailViewModel by viewModel { parametersOf(notice) }
    private lateinit var viewDataBinding: NoticeDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = NoticeDetailFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)
    }

}
