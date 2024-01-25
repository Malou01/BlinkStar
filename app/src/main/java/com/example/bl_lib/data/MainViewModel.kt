package com.example.bl_lib.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val liveDataFragment1 = MutableLiveData<DataStorage>()
    val liveDataFragment2 = MutableLiveData<DataStorage>()
}