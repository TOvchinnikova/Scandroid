import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.t_ovchinnikova.android.scandroid_2.buildlogic"

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toString())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toString())
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("jvmLibraryPlugin") {
            id = "JvmLibraryPlugin"
            implementationClass = "JvmLibraryPlugin"
        }
        register("androidApplicationPlugin") {
            id = "AndroidApplicationPlugin"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidComposePlugin") {
            id = "AndroidComposePlugin"
            implementationClass = "AndroidComposePlugin"
        }
        register("androidLibraryPlugin") {
            id = "AndroidLibraryPlugin"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidFeaturePlugin") {
            id = "AndroidFeaturePlugin"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("androidKoinPlugin") {
            id = "AndroidKoinPlugin"
            implementationClass = "AndroidKoinPlugin"
        }
    }
}