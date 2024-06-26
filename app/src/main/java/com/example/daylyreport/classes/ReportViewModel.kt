package com.example.daylyreport.classes

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daylyreport.ReportFragmentArgs
import com.example.daylyreport.databinding.FragmentReportBinding
import com.example.daylyreport.entitys.Report
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class ReportViewModel: ViewModel() {
    init {

    }
    private val firebase = FirebaseDatabase.getInstance().getReference("reportList")
    private val calendar = Calendar.getInstance()
    fun addNewReport(report: Report) {
        viewModelScope.launch(Dispatchers.IO) {
            val reportId = firebase.push().key!!
            firebase.child(reportId).setValue(report)
        }
    }

    fun addNewReportAlt(binding: FragmentReportBinding?) {
        viewModelScope.launch(Dispatchers.IO) {
            val reportId = firebase.push().key!!.toString()
            val report = Report(
                reportId,
                binding?.constructionObjectEditTextForEnter?.text.toString(),
                binding?.dateFromDateAndTime?.text.toString(),
                "08:00",
                null,
                null,
                null)
            binding?.reportId?.hint = report.reportId.toString()
            firebase.child(reportId).setValue(report)
        }
    }

    fun updateReport(binding: FragmentReportBinding?, args: ReportFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO) {
            binding?.apply {
                reportIdEditText.setText(args.reportId)
                constructionObjectEditTextForEnter.setText(args.constructionObject)
                dateFromDateAndTime.setText(args.date)
            }
        }
    }
    fun enterDate(binding: FragmentReportBinding?, parentFragmentManager: FragmentManager) {
        val dateDialog = MaterialDatePicker.Builder.datePicker()
            .build()

        dateDialog.addOnPositiveButtonClickListener {timeInMillis ->
            calendar.timeInMillis = timeInMillis
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            Snackbar.make(binding?.buttonDate!!, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT).show()
            binding.dateFromDateAndTime.text = dateFormat.format(calendar.time)
        }
        dateDialog.show(parentFragmentManager, "DatePicker")
    }
}


