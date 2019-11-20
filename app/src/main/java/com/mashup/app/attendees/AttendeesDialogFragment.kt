package com.mashup.app.attendees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mashup.databinding.AttendeesDialogFragmentBinding
import com.mashup.model.NoticeAttendance
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AttendeesDialogFragment(attendanceList: List<NoticeAttendance>) : DialogFragment() {

    companion object {
        fun newInstance(attendanceList: List<NoticeAttendance>) = AttendeesDialogFragment(attendanceList)
        const val TAG_ATTENDEES_DIALOG = "tag_attendees_dialog"
    }

    private val viewModel: AttendeesDialogViewModel by viewModel { parametersOf(attendanceList) }
    private lateinit var viewDataBinding: AttendeesDialogFragmentBinding
    private lateinit var listAdapter: AttendessDialogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = AttendeesDialogFragmentBinding.inflate(inflater, container, false).apply {
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
            listAdapter = AttendessDialogAdapter(viewModel)
            viewDataBinding.attendanceList.adapter = listAdapter
        }
    }
}
