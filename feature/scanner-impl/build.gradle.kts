plugins {
    id("AndroidFeaturePlugin")
    id("AndroidComposePlugin")
    id("AndroidKoinPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.scanner_impl"
}

dependencies {

    implementation(project(":feature:scanner-api"))
    implementation(project(":feature:settings-api"))
    implementation(project(":core-domain"))
    implementation(project(":core-utils"))
    implementation(project(":core-ui"))
    implementation(project(":core-executor"))
    implementation(project(":core-mvi"))

    implementation(libs.google.mlkit.scanning)

    implementation(libs.androidx.camera)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}