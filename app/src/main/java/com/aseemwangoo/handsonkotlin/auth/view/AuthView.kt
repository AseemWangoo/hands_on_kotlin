package com.aseemwangoo.handsonkotlin.auth.view

import android.app.Application
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.aseemwangoo.handsonkotlin.R
import com.aseemwangoo.handsonkotlin.google.GoogleApiContract
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.google.SignInGoogleViewModel
import com.aseemwangoo.handsonkotlin.google.SignInGoogleViewModelFactory
import com.aseemwangoo.handsonkotlin.shared.destinations.Destinations
import com.aseemwangoo.handsonkotlin.ui.components.loader.FullScreenLoaderComponent
import com.aseemwangoo.handsonkotlin.ui.components.signingoogle.SignInGoogleButton
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
private fun AuthView(
    onClick: () -> Unit,
    isError: Boolean = false,
    mSignInViewModel: SignInGoogleViewModel
) {
    val state = mSignInViewModel.loading.observeAsState()
    val isLoading = state.value

    Scaffold {
        if (isLoading == true && !isError) {
            FullScreenLoaderComponent()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1F))
                Image(
                    painterResource(id = R.drawable.app_logo),
                    contentDescription = stringResource(R.string.app_logo_desc),
                )
                Spacer(modifier = Modifier.weight(1F))
                SignInGoogleButton(onClick = {
                    mSignInViewModel.showLoading()
                    onClick()
                })
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = stringResource(R.string.app_login_bottom),
                    textAlign = TextAlign.Center,
                )

                when {
                    isError -> {
                        isError.let {
                            Text(
                                stringResource(R.string.auth_error_msg),
                                style = MaterialTheme.typography.h6
                            )
                            mSignInViewModel.hideLoading()
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PreviewAuthView() {
    Surface {
        Temp()
    }
}

@Composable
private fun Temp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Image(
            painterResource(id = R.drawable.app_logo),
            contentDescription = stringResource(R.string.app_logo_desc),
        )
        Spacer(modifier = Modifier.weight(1F))
        SignInGoogleButton(onClick = {})
        Spacer(modifier = Modifier.weight(1F))
        Text(
            text = stringResource(R.string.app_login_bottom),
            textAlign = TextAlign.Center,
        )
    }
}
