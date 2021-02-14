plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId("com.ianarbuckle.composecitybikes")
        minSdkVersion(23)
        targetSdkVersion(30)

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        kotlinCompilerExtensionVersion = "1.0.0-alpha12"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.21")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.ui:ui-tooling:1.0.0-alpha07")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")

    implementation("androidx.compose.ui:ui:1.0.0-alpha12")
    implementation("androidx.compose.ui:ui-graphics:1.0.0-alpha12")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-alpha12")
    implementation("androidx.compose.foundation:foundation:1.0.0-alpha12")
    implementation("androidx.compose.foundation:foundation-layout:1.0.0-alpha12")
    implementation("androidx.compose.material:material:1.0.0-alpha12")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0-alpha12")
    implementation("androidx.navigation:navigation-compose:1.0.0-alpha07")
    implementation("androidx.paging:paging-compose:1.0.0-alpha07")


    implementation("com.google.dagger:hilt-android:2.31-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.31.1-alpha")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")
    implementation("com.squareup.moshi:moshi:1.9.3")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")

    implementation("androidx.activity:activity-ktx:1.2.0")

    implementation("dev.chrisbanes.accompanist:accompanist-coil:0.5.1")

}