//import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt") // Support -> dataBinding
//    id("com.google.firebase.crashlytics")
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.interview.assignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.interview.assignment"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Enabling multidex support.
        multiDexEnabled = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //                                          yy-dd-mm
            resValue("string", "app_version_name", "Ver-22-17-10")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //                                          yy-dd-mm
            resValue("string", "app_version_name", "Ver-22-17-10")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    /*applicationVariants.all {
        outputs.all {
            if (name.contains("release")) (this as BaseVariantOutputImpl).outputFileName = "Assignment_${versionName}.apk"
        }
    }*/

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true // for BuildConfig file.
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

}

dependencies {
    // Libs .jar | .aar file supports
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar", "*.zip"))))

    // Rabbit MQ
    // implementation 'com.rabbitmq:amqp-client:5.7.3'
    // implementation files('libs/rabbitmq-client-app.jar')

    // Project -> Library
    implementation(project(mapOf("path" to ":app_base")))

    // AndroidX dependencies
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.work:work-runtime:2.9.1")
    implementation("androidx.annotation:annotation:1.8.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Lifecycle => ViewModel | LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    // Multi dex

    implementation("androidx.multidex:multidex:2.0.1")

    // RxJava | RxAndroid
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // Kotlin-X Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // For GPS Location Permission
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Anko Kotlin
    // implementation("org.jetbrains.anko:anko-commons:0.10.8")

    // Shared Preferences
    implementation("com.chibatching.kotpref:kotpref:2.13.2")
    implementation("com.chibatching.kotpref:gson-support:2.13.2")

    // AndroidX Material
    implementation("com.google.android.material:material:1.12.0")

    // Dynamic UI/UX
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    // Center Title Toolbar
    implementation("com.github.RaviKoradiya:Toolbar-Center-Title:1.0.3")

    // Grid line Divider
    implementation("com.bignerdranch.android:simple-item-decoration:1.0.0")

    // SpinKit | Ripple Effect
    implementation("com.github.ybq:Android-SpinKit:1.4.0")
    implementation("com.balysv:material-ripple:1.0.2")

    // Popup Image View
    implementation("com.github.chrisbanes:PhotoView:2.3.0")
    implementation("androidx.palette:palette-ktx:1.0.0")

    // Glide for Image Loading
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.1")
    implementation("androidx.activity:activity:1.9.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    // Gson for Json
    implementation("com.google.code.gson:gson:2.10.1")

    // Rx Android Networking
    implementation("com.github.amitshekhariitbhu.Fast-Android-Networking:rx2-android-networking:1.0.4")

    // Bottom Navigation Bar
    implementation ("com.ashokvarma.android:bottom-navigation-bar:2.1.0")

    implementation ("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.4")
    implementation ("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")

//    implementation ("com.hivemq:hivemq-mqtt-client:1.2.3")

    implementation ("com.google.android.gms:play-services-maps:18.1.0")

    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    // Material Components for Android. Replace the version with the latest version of Material Components library.
//    implementation ("com.google.android.material:material:1.5.0")
//
//    // Circle Indicator (To fix the xml preview "Missing classes" error)
//    implementation ("me.relex:circleindicator:2.1.6")
//
//    implementation ("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")

    // Android Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


}