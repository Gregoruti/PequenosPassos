package com.pequenospassos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class para PequenosPassos MVP.
 *
 * Configurada com Hilt para injeção de dependência seguindo Clean Architecture.
 * Implementação do MVP-01: Estrutura Base do Projeto.
 *
 * @since MVP 1.3.0 (12/10/2025) - DIA 1 - Fundação
 * @author MVP Development Team
 * @validationStatus ✅ Implementado - MVP-01
 */
@HiltAndroidApp
class PequenosPassosApplication : Application()
