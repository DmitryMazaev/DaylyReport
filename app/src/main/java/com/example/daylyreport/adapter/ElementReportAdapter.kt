package com.example.daylyreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.daylyreport.ListReportFragmentDirections
//import com.example.daylyreport.ListReportFragmentDirections
import com.example.daylyreport.entitys.Report
import com.example.daylyreport.databinding.ElementListReportItemBinding

class ElementReportAdapter(private val reportList: ArrayList<Report>): RecyclerView.Adapter<ElementReportAdapter.ViewHolder>() {

    class ViewHolder(val binding: ElementListReportItemBinding): RecyclerView.ViewHolder(binding.root)
    var onItemClick: ((Report) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ElementReportAdapter.ViewHolder(
            ElementListReportItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = reportList[position]
        holder.apply {
            binding.keyDaylyReport.text = item.reportId
            binding.date.text = item.dateOfWork
            binding.constructionObject.text = item.constructionObject
            binding.foreman.text = "прораб"
            binding.elementListReportRecycler.setOnClickListener {
                val action = ListReportFragmentDirections.actionListReportFragmentToReportFragment(
                    item.reportId.toString(),
                    item.dateOfWork.toString(),
                    item.constructionObject.toString(),
                    item.foreman.toString()
                )
                holder.itemView.findNavController().navigate(action)
            }
        }
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }
}