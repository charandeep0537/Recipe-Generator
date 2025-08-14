buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.1")
        classpath("com.google.gms:google-services:4.4.3")
        classpath ("com.google.gms:google-services:4.4.0")
        classpath ("com.google.gms:google-services:4.3.15")


    }
}



plugins {
    id("com.android.application") version "8.1.4" apply false
}