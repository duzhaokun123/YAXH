plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "io.github.duzhaokun123.yaxh.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.duzhaokun123.yaxh.demo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        languageVersion = "2.0"
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":YAXH"))
    //implementation("io.github.duzhaokun123:YAXH:main-SNAPSHOT")
    compileOnly(libs.xposed.api)
}
