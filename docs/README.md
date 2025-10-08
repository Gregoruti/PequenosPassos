# FonoVirtual

**Propósito**: Documentação principal e ponto de entrada para o projeto 
FonoVirtual. Fornece visão geral completa, instruções de instalação, guia de 
uso e informações de contato seguindo as melhores práticas de documentação de 
projetos open source.

**Escopo**: Documentação externa para desenvolvedores, usuários e contribuidores 
do projeto

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    README.md (ESTE)                        │
│              Portal de Entrada do Projeto                  │
│           (Instalação | Uso | Tecnologias)                 │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Referencia Documentação Técnica)
                   ▼
┌──────────────────┬──────────────────┬──────────────────────┐
│   GUIDELINES.md  │ SPECIFICATION    │   CHANGELOG.md       │
│  (Metodologia)   │ (Funcionalidades)│   (Histórico)        │
└──────────────────┴──────────────────┴──────────────────────┘
                   │
                   ▼ (Detalha Estrutura)
┌─────────────────────────────────────────────────────────────┐
│                     PATHS.md                               │
│              (Arquivos e Telas Detalhados)                 │
└─────────────────────────────────────────────────────────────┘
```

**Versão**: 1.0.13 | **Data**: 07/10/2025 | **Status**: Documentação 
sincronizada

---

> **Aplicativo Android para Exercícios de Fonoaudiologia com Reconhecimento de Voz**

[![Versão](https://img.shields.io/badge/versão-1.0.13-blue.svg)](./docs/CHANGELOG.md)
[![Status](https://img.shields.io/badge/status-estável-green.svg)](#status-do-projeto)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.0-purple.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/android-API%2024+-green.svg)](https://developer.android.com/)

## 📋 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Requisitos](#-requisitos)
- [Instalação](#-instalação)
- [Uso](#-uso)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Documentação](#-documentação)
- [Contribuição](#-contribuição)
- [Licença](#-licença)
- [Contato](#-contato)

## 🎯 Sobre o Projeto

O **FonoVirtual** é um aplicativo Android desenvolvido como projeto integrador 
para auxiliar profissionais de fonoaudiologia em sessões de terapia de fala. O 
app utiliza tecnologias modernas de reconhecimento de voz (ASR) e síntese de 
fala (TTS) para criar exercícios interativos e personalizados.

### Objetivos

- ✅ Facilitar exercícios de pronúncia e articulação
- ✅ Fornecer feedback imediato através de reconhecimento de voz
- ✅ Organizar exercícios por categorias fonoaudiológicas
- ✅ Oferecer interface intuitiva para profissionais e pacientes
- ⏳ Acompanhar progresso dos pacientes (versão futura)

### Contexto Acadêmico

**Universidade**: UNIVESP (Universidade Virtual do Estado de São Paulo)  
**Projeto**: Integrador VI - 2025  
**Área**: Tecnologia da Informação aplicada à Saúde  

## ✨ Funcionalidades

### 🗣️ Sistema de Exercícios
- **4 Categorias**: Número de Sílabas, Sons Consonantais, Dígrafos, Sílaba Tônica
- **24 Grupos**: Diferentes tipos de exercícios organizados
- **100 Palavras**: Banco completo para exercícios diversificados
- **Reconhecimento Contínuo**: ASR automático sem necessidade de botões
- **Feedback Visual**: Exibição do texto reconhecido em tempo real

### 🔊 Tecnologia de Voz
- **ASR Offline**: Reconhecimento de voz em português brasileiro (Vosk)
- **TTS Nativo**: Síntese de fala para exemplos de pronúncia
- **Processamento Local**: Funcionamento sem conexão à internet

### 📱 Interface Moderna
- **Jetpack Compose**: Interface nativa Android moderna
- **Modo Claro**: Design limpo com fundo branco
- **Títulos Dinâmicos**: Interface contextual conforme seleção
- **Navegação Intuitiva**: Fluxo otimizado para uso terapêutico

### 🔧 Ferramentas de Debug
- **Teste de ASR**: Validação isolada do reconhecimento de voz
- **Teste de TTS**: Validação da síntese de fala
- **Monitoramento**: Ferramentas para desenvolvedores

## 🛠️ Tecnologias Utilizadas

### Core
- **[Kotlin](https://kotlinlang.org/)** - Linguagem de programação principal
- **[Android SDK](https://developer.android.com/)** - Plataforma de desenvolvimento
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Framework de UI moderna

### Arquitetura
- **MVVM** - Model-View-ViewModel pattern
- **[Navigation Component](https://developer.android.com/guide/navigation)** - Navegação entre telas
- **[StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)** - Gerenciamento de estado reativo

### Reconhecimento de Voz
- **[Vosk](https://alphacephei.com/vosk/)** - ASR offline em português brasileiro
- **Modelo**: `vosk-model-small-pt-0.3`
- **Tamanho**: Otimizado para dispositivos móveis

### Síntese de Fala
- **[Android TTS](https://developer.android.com/reference/android/speech/tts/TextToSpeech)** - Text-to-Speech nativo
- **Idioma**: Português brasileiro (pt-BR)

### Build e Dependências
- **[Gradle](https://gradle.org/)** - Sistema de build com Kotlin DSL
- **[Java 17 LTS](https://openjdk.org/projects/jdk/17/)** - Runtime environment
- **Target SDK**: Android 34 (API Level 34)
- **Min SDK**: Android 24 (API Level 24)

## 📋 Requisitos

### Sistema
- **Android**: 7.0 (API 24) ou superior
- **RAM**: Mínimo 2GB recomendado
- **Armazenamento**: 200MB livres
- **Processador**: ARMv7 ou ARM64

### Permissões
- 🎤 **Microfone**: Para reconhecimento de voz
- 🔊 **Áudio**: Para síntese de fala

### Desenvolvimento
- **Android Studio**: Arctic Fox (2020.3.1) ou superior
- **Gradle**: 8.0+
- **JDK**: 17 LTS
- **Kotlin**: 1.9.0+

## 🚀 Instalação

### 1. Clone o Repositório
```bash
git clone https://github.com/Gregoruti/PequenosPassos.git
cd PequenosPassos
```

### 2. Abra no Android Studio
```bash
# Abra o Android Studio e selecione:
# File > Open > [pasta do projeto]
```

### 3. Sync e Build
```bash
# No Android Studio:
# 1. Aguarde o Gradle Sync automático
# 2. Build > Make Project (Ctrl+F9)
```

### 4. Execute o App
```bash
# Conecte um dispositivo Android ou use o emulador
# Run > Run 'app' (Shift+F10)
```

### 5. Instalação via APK (Alternativa)
```bash
# Para instalar APK pré-compilado:
adb install app-release.apk
```

## 📖 Uso

### Fluxo Principal

1. **Inicialização**: Tela splash com logo UNIVESP (3s)
2. **Home**: Selecione "Teste Rápido" para exercícios
3. **Categorias**: Escolha entre 4 tipos de exercícios
4. **Exercício**: Pronuncie as palavras apresentadas
5. **Resultado**: Visualize estatísticas de desempenho

### Categorias de Exercícios

#### 🔢 Número de Sílabas
- **2 Sílabas**: casa, gato, livro, mesa, água
- **3 Sílabas**: cadeira, boneca, janela, banana, sapato
- **4 Sílabas**: computação, integrado, chocolate, telefone, abacaxi
- **5 Sílabas**: geografia, matemática, aplicativo, comunicação, universidade

#### 🗣️ Sons Consonantais
- **BR**: brasil, braço, branco, brincar, abraço
- **CR**: criança, cravo, crescer, criado, criativo
- **FR**: frango, fruta, frio, fraco, frase
- **GR**: grande, grupo, grau, grave, gritar

#### 📝 Dígrafos
- **LH**: folha, olho, milho, calha, ralhar
- **NH**: ninho, sonho, linha, ganhar, manhã
- **RR**: carro, ferro, terra, serra, torrar
- **SS**: passar, massa, classe, grosso, osso

#### 📚 Sílaba Tônica
- **Á**: matemática, prática, fantástico, rápido, árvore
- **É**: café, pé, mané, chulé, filé
- **Ê**: você, bebê, português, inglês, três
- **Ú**: último, público, úmido, número, fúria

### Debug e Testes

- **Debug**: Acesse via HomeScreen > Debug
- **Teste ASR**: Valide reconhecimento de voz
- **Teste TTS**: Valide síntese de fala

## 📁 Estrutura do Projeto

```
PequenosPassos/
├── app/
│   ├── src/main/
│   │   ├── assets/vosk-model-small-pt-0.3/    # Modelo ASR
│   │   ├── java/com/example/fonovirtual_v2/
│   │   │   ├── ui/                            # Camada de Apresentação
│   │   │   │   ├── exercises/                 # Telas de exercícios
│   │   │   │   ├── theme/                     # Sistema de design
│   │   │   │   └── ...                        # Outras telas
│   │   │   ├── speech/                        # Módulos ASR/TTS
│   │   │   └── data/                          # Camada de dados
│   │   └── res/                               # Recursos Android
│   └── build.gradle.kts                       # Build configuration
├── docs/                                      # Documentação
│   ├── GUIDELINES.md                          # Diretrizes de desenvolvimento
│   ├── PATHS.md                               # Estrutura de arquivos/telas
│   ├── CHANGELOG.md                           # Histórico de versões
│   ├── SPECIFICATION_FOR_APP.md               # Especificações técnicas
│   └── README.md                              # Este arquivo
├── images/                                    # Assets de imagem
└── gradle/                                    # Configurações Gradle
```

### Arquitetura por Camadas

```
📱 Apresentação (UI)
    ↓
🎯 Aplicação (ViewModels)
    ↓
💾 Dados (Repositories)
    ↓
🗣️ Infraestrutura (ASR/TTS)
```

## 📚 Documentação

### Documentação Técnica
- **[Guidelines](./docs/GUIDELINES.md)**: Diretrizes de desenvolvimento com IA
- **[Paths](./docs/PATHS.md)**: Estrutura de arquivos e telas
- **[Changelog](./docs/CHANGELOG.md)**: Histórico detalhado de versões
- **[Specifications](./docs/SPECIFICATION_FOR_APP.md)**: Especificações técnicas

### Status do Projeto
- **Versão Atual**: 1.0.13 (Estável)
- **Cobertura**: 95% das funcionalidades MVP implementadas
- **Telas**: 8 telas validadas e funcionais
- **Exercícios**: 100 palavras em 24 grupos organizados

### Relatórios
- **[Relatório Técnico](./RELATORIO_TECNICO_FONOVIRTUAL_V2.md)**: Documentação acadêmica completa

## 🤝 Contribuição

### Como Contribuir

1. **Fork** o projeto
2. **Crie** uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'Add: MinhaFeature'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. **Abra** um Pull Request

### Diretrizes

- Siga as [Guidelines de Desenvolvimento](./docs/GUIDELINES.md)
- Mantenha cobertura de testes adequada
- Documente mudanças no CHANGELOG.md
- Use commits semânticos

### Code Review

- Todas as mudanças passam por review
- Testes automatizados obrigatórios
- Documentação sincronizada

## 📄 Licença

Este projeto é desenvolvido como **Projeto Integrador Acadêmico** da UNIVESP.

### Uso Acadêmico
- ✅ Permitido para fins educacionais
- ✅ Referência em trabalhos acadêmicos
- ✅ Adaptação para projetos similares

### Uso Comercial
- ⚠️ Requer autorização prévia
- 📧 Contate os autores para licenciamento

## 📞 Contato

### Desenvolvedor Principal
- **Nome**: Gregorio Ruti
- **Email**: [2100394i@aluno.univesp.br](mailto:gregorio.ruti@aluno.univesp.br)
- **GitHub**: [@Gregoruti](https://github.com/Gregoruti)


### Projeto Acadêmico
- **Universidade**: UNIVESP
- **Curso**: Tecnologia da Informação
- **Disciplina**: Projeto Integrador VI
- **Ano**: 2025

### Repositório
- **GitHub**: [PequenosPassos](https://github.com/Gregoruti/PequenosPassos)
- **Issues**: [Bug Reports & Feature Requests](https://github.com/Gregoruti/PequenosPassos/issues)
- **Discussions**: [Fórum da Comunidade](https://github.com/Gregoruti/PequenosPassos/discussions)

---

<div align="center">

**Desenvolvido com ❤️ para a comunidade fonoaudiológica**

[⬆️ Voltar ao topo](#fonovirtual)

</div>
