@file:Suppress("DEPRECATION")

import java.io.File
import java.util.UUID

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.pequenospassos"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pequenospassos"
        minSdk = 24
        targetSdk = 36
        versionCode = 16
        versionName = "1.9.6"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation - MVP Dependencies
    implementation(libs.androidx.navigation.compose)

    // Room Database - MVP Dependencies
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Hilt - MVP Dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Image Loading - MVP Dependencies
    implementation(libs.coil.compose)

    // ExifInterface for image orientation correction - MVP-07
    implementation("androidx.exifinterface:exifinterface:1.3.7")

    // ViewModel Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Existing dependencies
    implementation(libs.vosk.android)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.accompanist.permissions)

    // Testing dependencies
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Task para gerar UUID no modelo Vosk
val generateModelUuid by tasks.registering {
    description = "Gera arquivo UUID para o modelo Vosk se não existir"
    group = "vosk"

    val modelDir = File(projectDir, "src/main/assets/vosk-model-small-pt-0.3")
    val uuidFile = File(modelDir, "uuid")

    // Define outputs para cache do Gradle
    outputs.file(uuidFile)
    outputs.upToDateWhen { uuidFile.exists() }

    doLast {
        if (!modelDir.exists()) {
            modelDir.mkdirs()
            println("Diretório criado: ${modelDir.absolutePath}")
        }

        if (!uuidFile.exists()) {
            val uuid = UUID.randomUUID().toString()
            uuidFile.writeText(uuid)
            println("UUID criado: $uuid")
            println("Arquivo salvo em: ${uuidFile.absolutePath}")
        } else {
            val existingUuid = uuidFile.readText().trim()
            println("UUID já existe: $existingUuid")
            println("Arquivo localizado em: ${uuidFile.absolutePath}")
        }
    }
}

// Executa antes do build
tasks.named("preBuild") {
    dependsOn(generateModelUuid)
}