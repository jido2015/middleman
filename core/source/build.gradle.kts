plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.project.middleman.core.source"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        buildTypes {
            release {
                isMinifyEnabled = false
                buildConfigField ("String", "OAuth_2_0_WEB_CLIENT_ID", "\"168974225366-fmbbugslpe8dupcukrq3p30rcfoiv1in.apps.googleusercontent.com\"" )
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug{
                buildConfigField ("String", "OAuth_2_0_WEB_CLIENT_ID", "\"282524923052-bb6g6q5uo7k0gpm6qf2st8l6qrsrplnf.apps.googleusercontent.com\"" )
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

dependencies {
    ksp(libs.hilt.ksp)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.google.credential.manager)
    implementation(libs.firebase.auth)
    implementation (libs.firebase.firestore)
    platform(libs.firebase.bom)
    implementation (libs.play.services.auth)
    implementation(libs.google.identity.library)
    implementation(libs.gson)
    implementation(libs.roomdb)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.room.testing)
}