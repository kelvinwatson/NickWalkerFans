import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties()
apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.watsonlogic.nickwalkerfans"
        minSdk = 21
        targetSdk = 34
        versionCode = 4
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "YOUTUBE_API_KEY", apikeyProperties["YOUTUBE_API_KEY"] as String)
    }

    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = false
            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        val composeVersion: String by rootProject.extra
        kotlinCompilerExtensionVersion = composeVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.watsonlogic.nickwalkerfans"
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-test-junit4")
    implementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    val lifecycleVersion: String by rootProject.extra
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")// collectAsStateWithLifecycle
    implementation("androidx.activity:activity-compose:1.7.2")

    val coilVersion: String by rootProject.extra
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // ktor
    val ktorVersion: String by rootProject.extra
    // source: https://www.section.io/engineering-education/making-http-requests-with-ktor-in-android/
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    // HTTP engine: The HTTP client used to perform network requests.
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    // The serialization engine used to convert objects to and from JSON.
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    // Content negotiation for JSON
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    //serialize/deserialize JSON data
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    // Logging
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}