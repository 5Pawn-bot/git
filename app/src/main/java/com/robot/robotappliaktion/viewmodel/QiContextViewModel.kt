package com.robot.robotappliaktion.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QiContextViewModel: ViewModel() {

    val observeQiContext by lazy { MutableLiveData<Boolean>() }

    fun setQiContextObserver(boolean: Boolean){
        observeQiContext.postValue(boolean)
    }

    fun qiContextValue(): Boolean? {
        return observeQiContext.value
    }
}