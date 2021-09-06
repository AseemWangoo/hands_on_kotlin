package com.aseemwangoo.handsonkotlin.google

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch

class SignInGoogleViewModel(application: Application) : AndroidViewModel(application) {
    private var _userState = MutableLiveData<GoogleUserModel>()
    val googleUser: LiveData<GoogleUserModel> = _userState

    init {
        checkSignedInUser(application.applicationContext)
    }

    fun fetchSignInUser(email: String?, name: String?) {
        viewModelScope.launch {
            _userState.value = GoogleUserModel(
                email = email,
                name = name,
            )
        }
    }

    private fun checkSignedInUser(applicationContext: Context) {
        val gsa = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if(gsa != null) {
            _userState.value = GoogleUserModel(
                email = gsa.email,
                name = gsa.displayName,
            )
        }
    }
}

class SignInGoogleViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SignInGoogleViewModel::class.java)) {
            return SignInGoogleViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}