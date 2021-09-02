package com.aseemwangoo.handsonkotlin.google

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInGoogleViewModel() : ViewModel() {
    private var _googleUser = MutableLiveData<GoogleUserModel>()
    val googleUser: LiveData<GoogleUserModel>
        get() = _googleUser

    init {
        initData()
    }

    fun initData() {
        _googleUser.value = GoogleUserModel("", "")
    }
}