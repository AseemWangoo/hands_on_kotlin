package com.aseemwangoo.handsonkotlin.components.signingoogle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aseemwangoo.handsonkotlin.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignInGoogleButton(
    onClick: () -> Unit,
    isLoading: Boolean = false,
) {
    Surface(
        modifier = Modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                start = 12.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Login",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isLoading) {
                    "Logging in..."
                } else {
                    "Sign in With Google"
                }
            )

            AnimatedVisibility(isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 2.dp,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewSignInGoogleButton() {
    SignInGoogleButton(onClick = {

    })
}