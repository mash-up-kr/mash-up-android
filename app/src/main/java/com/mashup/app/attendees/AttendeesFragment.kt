package com.mashup.app.attendees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.databinding.AttendeesFragmentBinding
import com.mashup.model.NoticeAttendance
import com.mashup.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AttendeesFragment(attendanceList: List<NoticeAttendance>) : Fragment() {

    companion object {
        fun newInstance(attendanceList: List<NoticeAttendance>) = AttendeesFragment(attendanceList)
        const val EXTRA_ATTENDANCE_LIST = "ExtraAttendanceList"
    }

    private val viewModel: AttendeesViewModel by viewModel { parametersOf(attendanceList) }
    private lateinit var viewDataBinding: AttendeesFragmentBinding
    private lateinit var listAdapter: AttendeesAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AttendeesFragmentBinding.inflate(inflater, container, false).apply {
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
            val gridLayoutManager = GridLayoutManager(this.context, 4)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (listAdapter.getItemViewType(position) == ItemType.SECTION.typeInt) 4 else 1
                }
            }
            viewDataBinding.attendanceList.layoutManager = gridLayoutManager
            listAdapter = AttendeesAdapter()
            viewDataBinding.attendanceList.adapter = listAdapter
        }
    }

    private fun setupEventObserver() {
        viewModel.closeEvent.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                activity?.finish()
            }
        })
    }
}
