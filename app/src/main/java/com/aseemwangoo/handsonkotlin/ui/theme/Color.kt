@file:Suppress("MagicNumber")

package com.aseemwangoo.handsonkotlin.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

private val primary_blue = Color(0xFF0A3751)
private val sec_orange = Color(0xFFFC8138)
private val bg_blue = Color(0xFFF4F4F8)
private val bg_black = Color(0xFF282828)

val lightColorPalette = lightColors(
    primary = primary_blue,
    secondary = sec_orange,
    background = bg_blue,
    onBackground = bg_black,
    onSurface = primary_blue
)
