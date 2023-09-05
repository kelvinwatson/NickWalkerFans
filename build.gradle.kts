// Top-level build file where you can add configuration options common to all sub-projects/modules.
var serializationPluginVersionGlobal: String? = null
buildscript {
    extra.apply {
        set("coilVersion", "2.4.0")
        set("composeVersion", "1.5.3")
        set("kotlinVersion", "1.9.10")
        set("ktorVersion", "2.3.3")
        set("lifecycleVersion", "2.6.1")
        set("serializationPluginVersion", "1.6.10")
        set("serializationJsonVersion", "1.6.0")
    }
    val coilVersion: String by extra
    val composeVersion: String by extra
    val kotlinVersion: String by extra
    val ktorVersion: String by extra
    val lifecycleVersion: String by extra
    val serializationPluginVersion: String by extra
    val serializationJsonVersion: String by extra
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    //FIXME the
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}