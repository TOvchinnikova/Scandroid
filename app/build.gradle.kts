plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    namespace = "com.t_ovchinnikova.android.scandroid_2"

    defaultConfig {
        applicationId = "com.t_ovchinnikova.android.scandroid_2"
        minSdk = 23
        targetSdk = 34
        versionCode = 3
        versionName = "1.1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {

    implementation(project(":core-db-impl"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))
    implementation(project(":core-utils"))
    implementation(project(":feature:code-details-api"))
    implementation(project(":feature:code-details-impl"))
    implementation(project(":feature:scanner-api"))
    implementation(project(":feature:scanner-impl"))
    implementation(project(":feature:code-list-api"))
    implementation(project(":feature:code-list-impl"))
    implementation(project(":feature:settings-api"))
    implementation(project(":feature:settings-impl"))

    implementation(libs.android.material)

    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    testImplementation(libs.junit4)
}