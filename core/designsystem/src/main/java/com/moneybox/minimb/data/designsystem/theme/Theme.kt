package com.moneybox.minimb.data.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColors(
    primary = teal200,
    secondary = teal700,
    background = colorBlack,
    error = colorRed,
    surface = colorBlack1,
    onPrimary = colorWhite,
    onSecondary = colorWhite,
    onBackground = colorWhite,
    onError = colorWhite,
    onSurface = colorWhite1
)

private val LightColorScheme = lightColors(
    primary = teal200,
    secondary = teal700,
    background = colorWhite,
    error = colorRed,
    surface = colorWhite1,
    onPrimary = colorWhite,
    onSecondary = colorWhite,
    onBackground = colorBlack1,
    onError = colorWhite,
    onSurface = colorBlack1
)
@Composable
fun MoneyBoxAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val moneyBoxColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colors = moneyBoxColorScheme,
        shapes = moneyBoxShapes,
        typography = moneyBoxTypography,
        content = content
    )
}