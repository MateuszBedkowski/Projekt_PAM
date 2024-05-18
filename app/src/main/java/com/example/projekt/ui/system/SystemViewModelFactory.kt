package com.example.projekt.ui.system

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SystemViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SystemViewModel::class.java)) {
            return SystemViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
