package com.example.projekt.ui.disk

import android.os.Environment
import android.os.StatFs
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.File

data class FileInfo(val fileName: String, val fullPath: String, val size: Int)

class DiskViewModel : ViewModel() {

    private val _diskSpaceInfo = MutableLiveData<String>()
    private val _fileInfoList = MutableLiveData<List<FileInfo>>()

    val diskSpaceInfo: LiveData<String> = _diskSpaceInfo
    val fileInfoList: LiveData<List<FileInfo>> = _fileInfoList

    init {
        updateDiskSpaceInfo()
        updateFileInfoList(10)
    }

    private fun updateFileInfoList(count: Int): List<FileInfo> {
        val fileList = mutableListOf<FileInfo>()

        val rootDirectory = File("/storage/emulated/0")
        if (rootDirectory.exists() && rootDirectory.isDirectory) {
            val files = rootDirectory.listFiles()
            files?.let {
                val sortedFiles = it.sortedByDescending { file -> file.length() }
                sortedFiles.take(10).forEach { file ->
                    val size = file.length().toInt()
                    val fileName = file.name
                    val fullPath = file.path
                    fileList.add(FileInfo(fileName, fullPath, size))
                }
            }
        }

//        _fileInfoList.postValue(fileList)
        Log.d("DiskViewModel", "fileList: $fileList")
        return fileList
    }

    private fun getFilesInfo(directory: File): List<FileInfo> {
        val command = "du -ah ${directory.absolutePath} | sort -rh | head -n 10"
        val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        val fileList = mutableListOf<FileInfo>()

        reader.useLines { lines ->
            lines.take(10).forEach { line ->
                val (sizeStr, path) = line.split("\\s+".toRegex(), 2)
                val size = sizeStr.substringBeforeLast('k').toIntOrNull() ?: return@forEach
                val fileName = path.substringAfterLast('/')
                fileList.add(FileInfo(fileName, path, size))
            }
        }

        return fileList
    }

    private fun updateDiskSpaceInfo() {
        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpaceGB = totalBlocks * blockSize / (1024 * 1024 * 1024)
        val usedSpaceGB = (totalBlocks - availableBlocks) * blockSize / (1024 * 1024 * 1024)

        _diskSpaceInfo.postValue("Disk space: $usedSpaceGB GB / $totalSpaceGB GB")
    }
}
