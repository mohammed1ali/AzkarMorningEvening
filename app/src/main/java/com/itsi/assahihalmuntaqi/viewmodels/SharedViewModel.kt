package com.itsi.assahihalmuntaqi.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<String>()

    fun pageTitle(item: String) {
        selected.value = item
    }
}