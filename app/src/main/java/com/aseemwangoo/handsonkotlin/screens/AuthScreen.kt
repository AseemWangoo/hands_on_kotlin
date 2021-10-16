package com.aseemwangoo.handsonkotlin.screens

import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.aseemwangoo.handsonkotlin.components.destinations.Destinations
import com.aseemwangoo.handsonkotlin.components.signingoogle.SignInGoogleButton
import com.aseemwangoo.handsonkotlin.google.GoogleApiContract
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.google.SignInGoogleViewModel
import com.aseemwangoo.handsonkotlin.google.SignInGoogleViewModelFactory
import com.google.android.gms.common.api.ApiException
import com.squareup.moshi.Moshi
import timber.log.Timber

@Composable
fun AuthScreen(navController: NavController) {
    val signInRequestCode = 1
    val context = LocalContext.current

    val mSignInViewModel: SignInGoogleViewModel = viewModel(
        factory = SignInGoogleViewModelFactory(context.applicationContext as Application)
    )

    val state = mSignInViewModel.googleUser.observeAsState()
    val user = state.value

    val isError = rememberSaveable { mutableStateOf(false) }

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    mSignInViewModel.fetchSignInUser(gsa.email, gsa.displayName)
                } else {
                    isError.value = true
                }
            } catch (e: ApiException) {
                Timber.d("Error in AuthScreen%s", e.toString())
            }
        }

    AuthView(
        onClick = { authResultLauncher.launch(signInRequestCode) },
        isError = isError.value,
        mSignInViewModel
    )

    user?.let {
        mSignInViewModel.hideLoading()

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(GoogleUserModel::class.java).lenient()
        val userJson = jsonAdapter.toJson(user)

        navController.navigate(Destinations.Home.replace("{user}", userJson))
    }
}

@Composable
fun AuthView(
    onClick: () -> Unit,
    isError: Boolean = false,
    mSignInViewModel: SignInGoogleViewModel
) {
    val state = mSignInViewModel.loading.observeAsState()
    val isLoading = state.value

    Scaffold {
        if (isLoading == true && !isError) {
            FullScreenLoader()
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SignInGoogleButton(
                    onClick = {
                        mSignInViewModel.showLoading()
                        onClick()
                    },
                )

                when {
                    isError -> {
                        isError.let {
                            mSignInViewModel.hideLoading()
                            Spacer(modifier = Modifier.height(20.dp))
                            Text("Something went wrong...")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FullScreenLoader() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        )
    }
}
