plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.zelianko.numerologic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zelianko.numerologic"
        minSdk = 24
        targetSdk = 34
        versionCode = 42
        versionName = "2.0.0.42"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation ("com.yandex.android:mobileads:7.3.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material:material:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")

    implementation("io.appmetrica.analytics:analytics:6.1.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.2")
    implementation("androidx.lifecycle:lifecycle-process:2.8.4")
    implementation("androidx.appcompat:appcompat:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.2.0")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.0")

    implementation("com.android.billingclient:billing:7.0.0")
    implementation("com.android.billingclient:billing-ktx:7.0.0")
    implementation ("com.google.android.gms:play-services-ads:23.2.0")
    implementation ("com.github.skydoves:cloudy:0.1.2")
    implementation ("com.github.commandiron:WheelPickerCompose:1.1.11")


    // For Java-friendly APIs to register and unregister callbacks
    implementation ("androidx.window:window-java:1.3.0")

    // For RxJava2 integration
    implementation ("androidx.window:window-rxjava2:1.3.0")

    // For RxJava3 integration
    implementation ("androidx.window:window-rxjava3:1.3.0")

    // For testing
    implementation ("androidx.window:window-testing:1.3.0")


   // implementation ("com.yandex.ads.mediation:mobileads-mytarget:5.20.1.0")
    implementation ("com.yandex.android:mobileads-mediation:7.0.1.0")
}