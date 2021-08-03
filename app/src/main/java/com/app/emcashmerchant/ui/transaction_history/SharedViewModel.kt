package com.app.emcashmerchant.ui.transaction_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val type = MutableLiveData<String>()
    val status = MutableLiveData<String>()
    val date = MutableLiveData<ArrayList<String>>()
    val endDate = MutableLiveData<String>()

    fun sendType(s_type: String) {
        type.value = s_type
    }

    fun sendStatus(s_status: String) {
        status.value = s_status
    }

    fun sendDate(s_date: ArrayList<String>) {
        date.value = s_date
    }

}