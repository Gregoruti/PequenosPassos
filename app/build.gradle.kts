@file:Suppress("DEPRECATION")

import java.io.File
import java.util.Properties
import java.util.UUID

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.pequenospassos"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pequenospassos"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

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

    // Dependência para Jetpack Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.0-beta05")
    implementation("com.alphacephei:vosk-android:0.3.70") // Check Version
    implementation(kotlin("stdlib-jdk8"))

    // Google Accompanist - Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling) // Correção aqui
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