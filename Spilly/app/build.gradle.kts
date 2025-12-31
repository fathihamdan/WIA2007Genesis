plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.spilly"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.spilly"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.navigation:navigation-fragment:2.9.6")
    implementation("androidx.navigation:navigation-ui:2.9.6")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Firebase BoM (Bill of Materials)
    implementation platform('com.google.firebase:firebase-bom:32.7.0')
    implementation('com.google.firebase:firebase-auth')
    implementation ('com.google.firebase:firebase-firestore')
    implementation ('com.google.firebase:firebase-storage')

    // Room Database
    implementation ('androidx.room:room-runtime:2.6.1')
    annotationProcessor ('androidx.room:room-compiler:2.6.1')

    // Lifecycle components
    implementation ('androidx.lifecycle:lifecycle-livedata:2.7.0')
    implementation ('androidx.lifecycle:lifecycle-viewmodel:2.7.0')
}