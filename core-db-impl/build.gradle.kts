plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("com.google.devtools.ksp")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.core_db_impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles ("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation (project(":core-domain"))

    implementation ("androidx.core:core-ktx:1.10.1")

    //Room
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    ksp ("androidx.room:room-compiler:2.5.2")

    //Koin
    implementation (libs.koin.core)
    implementation (libs.koin.android)
}