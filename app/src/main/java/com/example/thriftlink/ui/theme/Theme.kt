package com.example.thriftlink.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
/*private val LightColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    secondary = Color(0xFF66BB6A),
    background = Color(0xFFF7FFF7),
    surface = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color.Black,
    secondary = Color(0xFF66BB6A),
    background = Color(0xFF0F1F12),
    surface = Color(0xFF162317)
)

@Composable
fun ThriftLinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
*/



val ThriftLinkGreen = Color(0xFF387030)
val ThriftLinkLightGreen = Color(0xFF6B9C63)
val ThriftLinkDarkSurface = Color(0xFF121212)

private val LightColorScheme = lightColorScheme(
    primary = ThriftLinkGreen,
    onPrimary = Color.White,
    secondary = ThriftLinkLightGreen,
    onSecondary = Color.Black,
    surface = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    error = Color(0xFFB00020),
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = ThriftLinkLightGreen,
    onPrimary = Color.Black,
    secondary = ThriftLinkGreen,
    onSecondary = Color.White,
    surface = ThriftLinkDarkSurface,
    background = ThriftLinkDarkSurface,
    onBackground = Color.White,
    error = Color(0xFFCF6679),
    onSurface = Color.White
)




@Composable
fun ThriftLinkTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}