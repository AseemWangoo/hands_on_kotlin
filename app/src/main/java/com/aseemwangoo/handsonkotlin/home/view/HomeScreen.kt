package com.aseemwangoo.handsonkotlin.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Suppress("UnusedPrivateMember")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    userModel: GoogleUserModel,
) {
    val viewState = viewModel.viewState.collectAsState()

    HomeViewContent(
        viewState = viewState.value,
        onDoneClicked = {},
    )
}
