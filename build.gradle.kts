// In your PROJECT-LEVEL build.gradle.kts

plugins {
    id("com.android.application") version "8.12.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    alias(libs.plugins.compose.compiler) apply false
}