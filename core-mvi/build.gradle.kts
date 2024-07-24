plugins {
    id("AndroidLibraryPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.core_mvi"
}

dependencies {
    implementation(libs.lifecycleViewModel)
}