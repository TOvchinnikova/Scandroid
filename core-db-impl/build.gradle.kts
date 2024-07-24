plugins {
    id("com.google.devtools.ksp")
    id("AndroidLibraryPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.core_db_impl"
}

dependencies {

    implementation(project(":core-domain"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}