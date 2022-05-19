package com.aseemwangoo.handsonkotlin.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.shared.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeTasksUseCase: GetHomeTasksUseCase,
    private val userModel: GoogleUserModel,
) : ViewModel() {

    private val _viewState: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState.Loading)

    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            val getTasksResp = getHomeTasksUseCase()

            _viewState.value = when (getTasksResp) {
                is Response.Success -> {
                    HomeViewState.Loaded(
                        tasks = getTasksResp.data,
                        userModel = userModel,
                    )
                }
                else -> {
                    HomeViewState.Error("Something went wrong")
                }
            }
        }
    }
}
