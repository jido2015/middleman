plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.project.middleman.feature.openchallenges"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

}


dependencies {

    implementation(libs.androidx.compiler)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)
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
    ksp(libs.hilt.ksp)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.google.identity.library)
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
    implementation(project(":core:source"))
    implementation(project(":feature:common"))

}