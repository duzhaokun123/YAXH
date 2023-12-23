plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    `maven-publish`
}

android {
    namespace = "io.github.duzhaokun123.yaxh"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
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
    compileOnly(libs.xposed.api)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "io.github.duzhaokun123"
                artifactId = "YAXH"
                version = "0.1.0" + if (true) "-SNAPSHOT" else ""

                from(components["release"])

                pom {
                    name = "YAXH"
                    description = "Yet Another Xposed Helper"
                    url = "https://github.com/duzhaokun123/YAXH"
                    licenses {
                        license {
                            name = "The Apache License, Version 2.0"
                            url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                        }
                    }
                }
            }
        }

        repositories {
            mavenLocal()
        }
    }
}
