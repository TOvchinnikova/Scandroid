plugins {
    id("AndroidFeaturePlugin")
    id("AndroidComposePlugin")
    id("AndroidKoinPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.code_details_impl"
}

dependencies {

    implementation(project(":feature:code-details-api"))
    implementation(project(":core-ui"))
    implementation(project(":core-domain"))
    implementation(project(":core-utils"))
    implementation(project(":core-executor"))
    implementation(project(":core-resources"))
    implementation(project(":core-db-impl"))
    implementation(project(":core-mvi"))
    implementation(project(":feature:settings-api"))

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}