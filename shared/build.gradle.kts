import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.kotlin.native.cocoapods) // ✅ this line is key

}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.project.shared"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }



    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate


    iosArm64()

    cocoapods {
        summary = "Shared Kotlin Multiplatform module"
        homepage = "https://yourprojecturl"
        version = "1.0.0"          // ✅ Add this
        ios.deploymentTarget = "14.1"

        framework {
            baseName = "sharedKit"
            isStatic = false
        }
    }


    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {

        commonMain.dependencies {
            implementation(libs.kotlin.stdlib)
            implementation(libs.koin.core)
            implementation(libs.gitLive.firebase.storage)
            implementation(libs.gitLive.firebase.auth)
            implementation(libs.gitLive.firebase.app)
            implementation(libs.gitLive.firebase.firestore)
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)

        }

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            // Add Android-specific dependencies here. Note that this source set depends on
            // commonMain by default and will correctly pull the Android artifacts of any KMP
            // dependencies declared in commonMain.
            implementation(libs.koin.android)
        }

//        val iosX64Main by getting
//        val iosArm64Main by getting
//        val iosSimulatorArm64Main by getting
//
//        val iosMain by creating {
//            dependsOn(commonMain)
//            iosX64Main.dependsOn(this)
//            iosArm64Main.dependsOn(this)
//            iosSimulatorArm64Main.dependsOn(this)
//            dependencies {
//                // iOS-specific deps (if any)
//
//                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
//                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
//                // part of KMP’s default source set hierarchy. Note that this source set depends
//                // on common by default and will correctly pull the iOS artifacts of any
//                // KMP dependencies declared in commonMain.
//            }
//        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.test.ext.junit)
            }
        }

    }

}