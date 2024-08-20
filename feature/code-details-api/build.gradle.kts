plugins {
    id("JvmLibraryPlugin")
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":core-executor"))
}