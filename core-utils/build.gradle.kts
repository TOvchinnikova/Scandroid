plugins {
    id("AndroidLibraryPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.core_utils"
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":core-resources"))

    implementation(libs.androidx.core.ktx)
}