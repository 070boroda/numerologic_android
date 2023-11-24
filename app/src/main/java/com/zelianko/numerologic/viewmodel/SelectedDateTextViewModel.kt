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

    private val _mapDataTransform = MutableLiveData<HashMap<String, String>>()
    val mapDataTransform: LiveData<HashMap<String, String>> = _mapDataTransform

    fun setMapDataTransform (newValue: HashMap<String, String>) {
        _mapDataTransform.value = newValue
    }

    private val _mapDataDegrad = MutableLiveData<HashMap<String, String>>()
    val mapDataDegrad: LiveData<HashMap<String, String>> = _mapDataDegrad

    fun setMapDataDegrad (newValue: HashMap<String, String>) {
        _mapDataDegrad.value = newValue
    }

    private val _listDis = MutableLiveData<MutableList<Int>>()
    val listDis: LiveData<MutableList<Int>> = _listDis

    fun setListDis (newValue: MutableList<Int>) {
        _listDis.value = newValue
    }
 }