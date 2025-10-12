# PequenosPassos

**Propósito**: Documentação principal e ponto de entrada para o projeto
PequenosPassos. Fornece visão geral completa, instruções de instalação, guia de
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

**Versão**: 1.1.0 | **Data**: 12/10/2025 | **Status**: Documentação sincronizada

---

## Novidade
A modelagem da entidade Task agora inclui subtarefas (Steps), cada uma com tempo determinado e ordem de execução. Veja detalhes em [SPECIFICATION_FOR_APP.md](./SPECIFICATION_FOR_APP.md).

> **Aplicativo Android para apoio a Atividades da Vida Diária com Reconhecimento de Voz**

[![Versão](https://img.shields.io/badge/versão-1.0.0-blue.svg)](./docs/CHANGELOG.md)
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

O **PequenosPassos** é um aplicativo Android desenvolvido para apoiar crianças
com TEA e seus cuidadores na execução de atividades diárias, promovendo
autonomia e integração familiar. O projeto foi migrado do antigo FonoVirtual_V2,
com foco em funcionalidades de ASR (Vosk), TTS nativo, SplashScreen, HomeScreen,
e Debug.

## 🚀 Funcionalidades

- SplashScreen com logo UNIVESP
- HomeScreen com navegação para Cadastro, Atividades, Resultados, Debug
- Módulos de ASR (Vosk) e TTS nativo
- Tela de Debug

## 🛠 Tecnologias Utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Vosk ASR
- TTS Android

## 📦 Requisitos

- Android API 24+
- Permissão de microfone para ASR

## 📝 Instalação

1. Clone o repositório
2. Abra o projeto no Android Studio
3. Compile e execute em um dispositivo ou emulador

## 💡 Uso

- Inicie o app
- Navegue pelas telas principais
- Teste ASR/TTS na tela de Debug

## 🗂 Estrutura do Projeto

Consulte [PATHS.md](./docs/PATHS.md) para detalhes da estrutura de arquivos e telas.

## 📚 Documentação

- [CHANGELOG.md](./docs/CHANGELOG.md)
- [PATHS.md](./docs/PATHS.md)
- [GUIDELINES.md](./docs/GUIDELINES.md)
- [SPECIFICATION_FOR_APP.md](./docs/SPECIFICATION_FOR_APP.md)

## 🤝 Contribuição

Contribuições são bem-vindas! Siga as diretrizes em [GUIDELINES.md](./docs/GUIDELINES.md).

## 📄 Licença

MIT

## 📬 Contato

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

## 🚀 Sincronização com GitHub

Em 09/10/2025, o projeto PequenosPassos foi sincronizado com o repositório remoto oficial:
[https://github.com/Gregoruti/PequenosPassos](https://github.com/Gregoruti/PequenosPassos)

Todos os commits e histórico do projeto estão disponíveis online para colaboração.

<div align="center">

**Desenvolvido com ❤️ para a comunidade de apoio a atividades diárias**

[⬆️ Voltar ao topo](#pequenospassos)

</div>
