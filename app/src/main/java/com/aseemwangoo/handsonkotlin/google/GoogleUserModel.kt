package com.aseemwangoo.handsonkotlin.google

data class GoogleUserModel(
    val name: String?,
    val email: String?
) {

    val showLoading: Boolean
        get() = name.isNullOrEmpty() || email.isNullOrEmpty()
}