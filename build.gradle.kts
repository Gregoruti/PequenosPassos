// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
}

tasks.register("validateMVP01") {
    group = "validation"
    description = "Valida estrutura base do MVP-01 (arquitetura, dependências, build)"
    doLast {
        println("[MVP-01] Iniciando validação...")
        val requiredDirs = listOf(
            "app/src/main/java/com/pequenospassos/data",
            "app/src/main/java/com/pequenospassos/domain",
            "app/src/main/java/com/pequenospassos/presentation",
            "app/src/main/java/com/pequenospassos/di",
            "app/src/main/java/com/pequenospassos/utils"
        )
        requiredDirs.forEach {
            if (!file(it).exists()) {
                throw GradleException("[MVP-01] Estrutura ausente: $it")
            }
        }
        println("[MVP-01] Estrutura de pastas validada.")
        println("[MVP-01] Dependências e build devem ser validados manualmente conforme MVP01_VALIDATION.md.")
    }
}
