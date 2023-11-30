@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    kotlin("kapt")
}

android {
    namespace = "com.moneybox.minimb.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    // Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    // Activity-Fragment-Material-compose material bundle
    implementation(libs.bundles.activity.fragment.material.bundle)
    // compose bundle
    implementation(libs.bundles.compose.bundle)
    // Jetpack Navigation Bundle
    implementation(libs.bundles.navigation.bundle)
    // Life Cycle Bundle
    implementation(libs.bundles.lifecycle.bundle)
    // Retrofit
    implementation(libs.bundles.retrofit.bundle)
    // unit test bundle
    testImplementation(libs.bundles.unit.test.bundle)
}