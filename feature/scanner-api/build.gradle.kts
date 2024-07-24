plugins {
    id("AndroidLibraryPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.scanner_api"
}

dependencies {
    implementation(project(":core-domain"))

    implementation(libs.google.mlkit.detection)
    implementation(libs.androidx.camera)
}