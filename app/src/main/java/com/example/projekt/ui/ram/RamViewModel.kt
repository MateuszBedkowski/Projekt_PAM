package com.example.projekt.ui.ram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RamViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is RAM Fragment"
    }
    val text: LiveData<String> = _text
}