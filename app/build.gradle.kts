@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.moneybox.minimb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moneybox.minimb"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "8.35.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":feature:login"))
    implementation(project(":feature:home"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":domain:repositorycontract"))

    // Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    // Constraint layout
    implementation(libs.androidx.constraintlayout)

    implementation(libs.bundles.activity.fragment.material.bundle)
    implementation(libs.bundles.compose.bundle)
    implementation(libs.bundles.navigation.bundle)

    // unit test bundle
    testImplementation(libs.bundles.unit.test.bundle)
    testImplementation(libs.junit)
}