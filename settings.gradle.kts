pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven(url = "https://jitpack.io")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "middleman"
include(":app")
include(":core")
include(":core:injection")
include(":core:source")
include(":navigation")
include(":feature:openchallenges")
include(":feature:authentication")
include(":feature:createchallenge")
include(":core:common")
include(":composables")
include(":feature:challengedetails")
include(":feature:common")
include(":designsystem")
include(":feature:dashboard")
include(":feature:notification")
include(":shared")
