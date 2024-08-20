plugins {
    id("AndroidApplicationPlugin")
    id("AndroidComposePlugin")
}

android {
    buildToolsVersion = "34.0.0"

    namespace = "com.t_ovchinnikova.android.scandroid_2"

    defaultConfig {
        applicationId = "com.t_ovchinnikova.android.scandroid_2"
        versionCode = 6
        versionName = "1.1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(project(":core-db-impl"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))
    implementation(project(":core-utils"))
    implementation(project(":core-executor"))
    implementation(project(":core-mvi"))
    implementation(project(":core-resources"))
    implementation(project(":feature:code-details-api"))
    implementation(project(":feature:code-details-impl"))
    implementation(project(":feature:scanner-api"))
    implementation(project(":feature:scanner-impl"))
    implementation(project(":feature:code-list-api"))
    implementation(project(":feature:code-list-impl"))
    implementation(project(":feature:settings-api"))
    implementation(project(":feature:settings-impl"))

    implementation(libs.android.material)

    implementation(libs.compose.activity)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    testImplementation(libs.junit4)
}