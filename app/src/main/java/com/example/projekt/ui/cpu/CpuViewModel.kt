package com.example.projekt.ui.cpu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CpuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is CPU Fragment"
    }
    val text: LiveData<String> = _text
}