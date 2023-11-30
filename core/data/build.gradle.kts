import com.android.build.gradle.ProguardFiles.getDefaultProguardFile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")

}
android {
    namespace = "com.moneybox.minimb.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        buildConfigField("String", "BASE_URL", property("baseUrl").toString())
        buildConfigField("String", "API_VERSION", property("apiVersion").toString())
        buildConfigField("String", "APP_ID", property("appId").toString())
        buildConfigField("String", "APP_VERSION", property("appVersion").toString())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.4.4"
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

    implementation(project(":core:common"))
    implementation(project(":domain:repositorycontract"))

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

    // Unit Test bundle
    testImplementation(libs.bundles.unit.test.bundle)

}
