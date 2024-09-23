plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.application.base"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        version = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

}

dependencies {
    // Android X => AppCompat | Core | Support | Constraint
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Material
    implementation("com.google.android.material:material:1.12.0")

    // Gson for Json
    implementation("com.google.code.gson:gson:2.10.1")

    // Preference
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Anko Kotlin
    // implementation("org.jetbrains.anko:anko-commons:0.10.8")

    // Fcm for HttpException
    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.2")

    // PDF Creator Android
    implementation("com.github.tejpratap46:PDFCreatorAndroid:1.1")

    // Lifecycle => ViewModel | LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // RxJava | RxAndroid
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
}
