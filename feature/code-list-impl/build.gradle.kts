plugins {
    id("AndroidFeaturePlugin")
    id("AndroidComposePlugin")
    id("AndroidKoinPlugin")
}

android {
    namespace = "com.t_ovchinnikova.android.scandroid_2.code_list_impl"
}

dependencies {
    implementation(project(":feature:code-list-api"))
    implementation(project(":feature:settings-api"))
    implementation(project(":core-domain"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))
    implementation(project(":core-executor"))
    implementation(project(":core-utils"))
    implementation(project(":core-db-impl"))
    implementation(project(":core-mvi"))

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}