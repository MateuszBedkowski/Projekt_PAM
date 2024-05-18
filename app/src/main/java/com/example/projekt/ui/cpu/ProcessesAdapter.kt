package com.example.projekt.ui.cpu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R

class ProcessesAdapter : ListAdapter<ProcessInfo, ProcessesAdapter.ProcessViewHolder>(ProcessDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_process, parent, false)
        return ProcessViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProcessViewHolder, position: Int) {
        val process = getItem(position)
        holder.bind(process)
    }

    inner class ProcessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pidTextView: TextView = itemView.findViewById(R.id.text_pid)
        private val appNameTextView: TextView = itemView.findViewById(R.id.text_appName)
        private val cpuUsageTextView: TextView = itemView.findViewById(R.id.text_cpu_usage)

        fun bind(process: ProcessInfo) {
            val context = itemView.context

            val runningAppName = context.getString(R.string.running_app_name)
            val pid = context.getString(R.string.pid)
            val cpuUsage = context.getString(R.string.cpu_usage)

            appNameTextView.text = "$runningAppName ${process.appName}"
            pidTextView.text = "$pid: ${process.pid}"
            cpuUsageTextView.text = "$cpuUsage ${process.cpuUsage}%"
        }
    }

    private class ProcessDiffCallback : DiffUtil.ItemCallback<ProcessInfo>() {
        override fun areItemsTheSame(oldItem: ProcessInfo, newItem: ProcessInfo): Boolean {
            return oldItem.pid == newItem.pid
        }

        override fun areContentsTheSame(oldItem: ProcessInfo, newItem: ProcessInfo): Boolean {
            return oldItem == newItem
        }
    }
}
