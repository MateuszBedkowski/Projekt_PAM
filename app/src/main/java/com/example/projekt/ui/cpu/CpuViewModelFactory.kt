package com.example.projekt.ui.cpu

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CpuViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CpuViewModel::class.java)) {
            return CpuViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
