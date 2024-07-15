plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.scanner_impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {

    implementation(project(":feature:scanner-api"))
    implementation(project(":feature:settings-api"))
    implementation(project(":core-domain"))
    implementation(project(":core-utils"))
    implementation(project(":core-ui"))
    implementation(project(":core-executor"))
    implementation(project(":core-mvi"))

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.google.mlkit.scanning)

    implementation(libs.androidx.camera)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.uiToolingPreview)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
}