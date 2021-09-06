package com.aseemwangoo.handsonkotlin.google

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SignInGoogleViewModel(
) : ViewModel() {
    private var _userState = MutableLiveData<GoogleUserModel>()
    val googleUser: LiveData<GoogleUserModel> = _userState

    fun fetchSignInUser(email: String?, name: String?) {
        viewModelScope.launch {
            _userState.value = GoogleUserModel(
                email = email,
                name = name,
            )
        }
    }
}

class SignInGoogleViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SignInGoogleViewModel::class.java)) {
            return SignInGoogleViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}