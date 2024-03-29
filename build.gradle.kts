// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools:r8:8.1.56")

        classpath (libs.android.gradlePlugin)
        classpath (libs.kotlin.gradle.plugin)
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.9.0-1.0.11"
}

//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//        maven { url 'https://jitpack.io' }
//    }
//}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}