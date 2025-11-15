plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.thriftlink"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.thriftlink"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" // ✅ works perfectly with Kotlin 2.0
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.foundation:foundation")
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui-text:1.6.0")
    implementation(libs.androidx.benchmark.traceprocessor)
    implementation(libs.androidx.foundation)
    implementation(libs.material3)
    implementation(libs.androidx.runtime)
    debugImplementation("androidx.compose.ui:ui-tooling")


    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.compose.material:material-icons-extended")

    // Stable refresh layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.6-rc")

    // --- Coil (for image loading in Compose) ---
    implementation("io.coil-kt:coil-compose:2.6.0")
    // Lifecycle + ViewModel + Coroutines
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Local Unit Tests (for the '@Test' annotation in local tests)
    testImplementation("junit:junit:4.13.2")

    // Android Instrumented Tests (for the failing file ExampleInstrumentedTest.kt)
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // This provides 'test' for instrumented tests
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}



