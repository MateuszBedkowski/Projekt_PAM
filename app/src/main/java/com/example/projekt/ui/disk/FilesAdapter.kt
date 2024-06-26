package com.example.projekt.ui.disk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R

class FilesAdapter : ListAdapter<FileInfo, FilesAdapter.FileViewHolder>(FileDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = getItem(position)
        holder.bind(file)
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.text_fileName)
        private val fullPathTextView: TextView = itemView.findViewById(R.id.text_fullPath)
        private val sizeTextView: TextView = itemView.findViewById(R.id.text_size)

        fun bind(file: FileInfo) {
            val context = itemView.context

            val fileName = context.getString(R.string.file_name)
            val path = context.getString(R.string.path)
            val fileSize = context.getString(R.string.file_size)

            fileNameTextView.text = "$fileName ${file.fileName}"
            fullPathTextView.text = "$path ${file.fullPath}"
            sizeTextView.text = "$fileSize ${file.size} MB"
        }
    }



    private class FileDiffCallback : DiffUtil.ItemCallback<FileInfo>() {
        override fun areItemsTheSame(oldItem: FileInfo, newItem: FileInfo): Boolean {
            return oldItem.fileName == newItem.fileName
        }

        override fun areContentsTheSame(oldItem: FileInfo, newItem: FileInfo): Boolean {
            return oldItem == newItem
        }
    }
}
