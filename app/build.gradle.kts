plugins {
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.project.middleman"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.middleman"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            buildConfigField ("String", "OAUTH_2_WEB_CLIENT", "\"PROD\"" )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug{
            isDebuggable = true
            buildConfigField ("String", "OAUTH_2_WEB_CLIENT", "\"DEV\"" )
            applicationIdSuffix = ".debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.compiler)
    implementation (libs.firebase.firestore)
    implementation (libs.play.services.auth)
    platform(libs.firebase.bom)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate.ktx)
    implementation(libs.compose.lifecycle)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.material3)
    implementation(libs.material3.window.size)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.coil.image)
    implementation(libs.material.accompanist.pager)
    implementation(libs.airbnb.lottie)
    implementation(libs.skydoves.image.placeholder)
    implementation(libs.material.icons.extended)
    implementation(libs.skydoves.image.loader)
    implementation(libs.compose.constraintlayout)
    implementation(libs.stevdza.messageBarCompose)
    ksp(libs.hilt.ksp)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.google.identity.library)
    ksp(libs.room.ksp)
    implementation(libs.compose.navigation)
    implementation(libs.google.credential.manager)
    implementation(libs.kotlin.coroutine)
    implementation(libs.splash.screen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Desugar JDK
    coreLibraryDesugaring(libs.core.library8)
}