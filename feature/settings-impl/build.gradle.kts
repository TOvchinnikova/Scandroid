plugins {
    id("AndroidFeaturePlugin")
    id("AndroidComposePlugin")
    id("AndroidKoinPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.settings_impl"
}

dependencies {

    implementation(project(":feature:settings-api"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))
    implementation(project(":core-executor"))
    implementation(project(":core-mvi"))
    implementation(project(":core-resources"))

    implementation(libs.androidx.dataStore.preferences)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}