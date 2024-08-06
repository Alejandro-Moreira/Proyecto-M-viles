// build.gradle.kts (nivel superior)

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("com.google.gms:google-services:4.3.15")
        classpath(kotlin("gradle-plugin", "1.8.20")) // Añadido para soporte de Kotlin
    }
}

allprojects {
    repositories {
        //google()
        //mavenCentral()
    }
}