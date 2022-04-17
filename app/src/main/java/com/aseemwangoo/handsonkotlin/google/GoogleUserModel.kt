package com.aseemwangoo.handsonkotlin.google

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoogleUserModel(
    val name: String? = null,
    val email: String? = null
) : Parcelable
