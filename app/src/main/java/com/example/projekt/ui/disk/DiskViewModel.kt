package com.example.projekt.ui.disk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiskViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is disk Fragment"
    }
    val text: LiveData<String> = _text
}