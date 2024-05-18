package com.example.projekt.ui.ram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R


class ProcessesAdapter : ListAdapter<RamProcessInfo, ProcessesAdapter.ProcessViewHolder>(ProcessDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ram_item_process, parent, false)
        return ProcessViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProcessViewHolder, position: Int) {
        val process = getItem(position)
        holder.bind(process)
    }

    inner class ProcessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pidTextView: TextView = itemView.findViewById(R.id.text_pid)
        private val appNameTextView: TextView = itemView.findViewById(R.id.text_appName)
        private val ramUsageTextView: TextView = itemView.findViewById(R.id.text_ram_usage)

        fun bind(process: RamProcessInfo) {
            val context = itemView.context

            val appName = context.getString(R.string.running_app_name)
            val pid = context.getString(R.string.pid)
            val ramUsage = context.getString(R.string.ram_usage)

            appNameTextView.text = "$appName ${process.appName}"
            pidTextView.text = "$pid ${process.pid}"
            ramUsageTextView.text = "$ramUsage ${process.ramUsage}%"
        }
    }

    private class ProcessDiffCallback : DiffUtil.ItemCallback<RamProcessInfo>() {
        override fun areItemsTheSame(oldItem: RamProcessInfo, newItem: RamProcessInfo): Boolean {
            return oldItem.pid == newItem.pid
        }

        override fun areContentsTheSame(oldItem: RamProcessInfo, newItem: RamProcessInfo): Boolean {
            return oldItem == newItem
        }
    }
}
