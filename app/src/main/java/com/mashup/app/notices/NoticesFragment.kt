package com.mashup.app.notices

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.app.attendees.AttendeesActivity
import com.mashup.app.attendees.AttendeesFragment
import com.mashup.app.login.LoginActivity
import com.mashup.app.noticedetail.NoticeDetailActivity
import com.mashup.app.noticedetail.NoticeDetailFragment
import com.mashup.databinding.NoticesFragmentBinding
import com.mashup.util.EventObserver
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
        setupEventObserver()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = NoticeAdapter(viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
        }
    }

    private fun setupEventObserver() {
        viewModel.itemChangedEvent.observe(this, EventObserver { position ->
            listAdapter.notifyItemChanged(position)
        })

        viewModel.showDetailEvent.observe(this, EventObserver { notice ->
            val intent = Intent(this@NoticesFragment.context, NoticeDetailActivity::class.java).apply {
                putExtra(NoticeDetailFragment.EXTRA_NOTICE, notice)
            }
            startActivityForResult(intent, NoticeDetailFragment.REQUEST_NOTICE_ACTION)
        })

        viewModel.onException.observe(this, EventObserver { exception ->
            if (exception) {
                activity?.finishAffinity()
            }
            startActivity(Intent(this@NoticesFragment.context, LoginActivity::class.java))
        })

        viewModel.showAttendeesEvent.observe(this, EventObserver { attendees ->
            val intent = Intent(this@NoticesFragment.context, AttendeesActivity::class.java).apply {
                putParcelableArrayListExtra(
                    AttendeesFragment.EXTRA_ATTENDANCE_LIST,
                    ArrayList(attendees)
                )
            }
            startActivity(intent)
        })
    }
}
