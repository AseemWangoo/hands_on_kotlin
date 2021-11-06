package com.aseemwangoo.handsonkotlin.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.aseemwangoo.handsonkotlin.R

private val MontserratBold = FontFamily(Font(R.font.montserrat_bold))
private val MontserratExtraBold = FontFamily(Font(R.font.montserrat_extrabold))
private val MontserratLight = FontFamily(Font(R.font.montserrat_light))
private val MontserratMedium = FontFamily(Font(R.font.montserrat_medium))
private val MontserratRegular = FontFamily(Font(R.font.montserrat_regular))

// Set of Material typography styles to start with
val typography = Typography(
    h1 = TextStyle(
        fontFamily = MontserratExtraBold,
        fontSize = 40.sp
    ),
    h2 = TextStyle(
        fontFamily = MontserratBold,
        fontSize = 32.sp
    ),
    h3 = TextStyle(
        fontFamily = MontserratRegular,
        fontSize = 26.sp
    ),
    h4 = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 24.sp
    ),
    h5 = TextStyle(
        fontFamily = MontserratRegular,
        fontSize = 22.sp
    ),
    h6 = TextStyle(
        fontFamily = MontserratRegular,
        fontSize = 18.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 15.sp
    ),
    body1 = TextStyle(
        fontFamily = MontserratLight,
        fontSize = 13.sp
    ),
    button = TextStyle(
        fontFamily = MontserratBold,
        fontSize = 13.sp
    ),
)
