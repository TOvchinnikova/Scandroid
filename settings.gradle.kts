pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Scandroid_2"

include(":app")
include(":core-db-impl")
include(":core-domain")
include(":feature:settings-impl")
include(":feature:scanner-api")
include(":feature:scanner-impl")
include(":feature:code-list-api")
include(":feature:code-list-impl")
include(":feature:code-details-api")
include(":feature:code-details-impl")
include(":core-ui")
include(":core-resources")
include(":feature:settings-api")
include(":core-utils")
include(":core-executor")
include(":core-mvi")
