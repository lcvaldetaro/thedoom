import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.peterpan.doom"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.peterpan.doom"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    externalNativeBuild {
        cmake {
            path ("CMakeLists.txt")
        }
    }

    applicationVariants.configureEach {
        if (buildType.name == "release") {
            var originalOutputFile = File("")
            outputs.forEach { file -> originalOutputFile = file.outputFile}
            tasks.named("bundle${name.uppercaseFirstChar()}") {
               doLast {
                   val newFileName = "${applicationId}-release-${versionName}(${versionCode}).aab"
                   val parent = originalOutputFile.parent.toString().replace("apk", "bundle")
                   val originalBundle = file("${parent}/app-release.aab")

                   println("renaming ${parent}/app-release.aab to ${parent}/${newFileName}")
                   originalBundle.renameTo(file("$parent/$newFileName"))
               }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.circum)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
