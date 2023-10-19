package com.zelianko.numerologic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectedDateTextViewModel : ViewModel() {

    private val _selectedDateText = MutableLiveData<String>()
    val selectedDateText: LiveData<String> = _selectedDateText

    fun setSelectedDateText (newValue: String) {
        _selectedDateText.value = newValue
    }
 }