# CHANGELOG.MD

**Propósito**: Histórico completo de versões e mudanças do projeto PequenosPassos
com status de validação integrado para cada funcionalidade implementada. Serve
como registro oficial de evolução do projeto e guia de referência para
desenvolvimento futuro.

**Escopo**: Rastreamento de versões, validações, bugs corrigidos e roadmap do
PequenosPassos

**Interconexões da Documentação**:
```
┌─────────────────────────────────────────────────────────────┐
│                    GUIDELINES.md                            │
│              (Define Processo de Versionamento)             │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Estabelece Critérios)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│              SPECIFICATION_FOR_APP.md                       │
│                (Define Funcionalidades)                     │
└──────────────────┬──────────────────────────────────────────┘
                   │ (Implementa Features)
                   ▼
┌─────────────────────────────────────────────────────────────┐
│                   CHANGELOG.md (ESTE)                       │
│            Histórico + Validações Integradas                │
│                                                             │  
└─────────────────┬───────────────────────────────────────────┘
                  │ (Informa Status)
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                     PATHS.md                                │
│              (Estrutura Atual Validada)                     │
└─────────────────────────────────────────────────────────────┘
```

**Versão**: 1.1.0 | **Data**: 12/10/2025 | **Status**: Modelagem de Task atualizada para incluir Steps (subtarefas)

---

## Sumário
1. [Histórico de Versões](#1-histórico-de-versões)
2. [Status de Validação Integrado](#2-status-de-validação-integrado)
3. [Roadmap de Funcionalidades](#3-roadmap-de-funcionalidades)

---

## 1. Histórico de Versões

### Versão 1.0.0 (09/10/2025) - Primeira Versão do App Inicializando após Renomeações

**Status da Versão**: `✅ Implementado e Validado`

#### Mudanças Implementadas
- **Renomeação Completa do Projeto**: Transição de "FonoVirtual_V2" para "PequenosPassos"
- **Atualização de Interface**: 
  - Título principal alterado para "Pequenos Passos"
  - Informação do projeto alterada de "Projeto Integrador VI" para "TCC-540"
- **Correção de Configurações**:
  - Ajustes de navegação e temas
  - Remoção de módulos de exercícios e Teste Rápido
- **Funcionalidades Mantidas**:
  - SplashScreen com logo UNIVESP
  - HomeScreen com navegação para Cadastro, Atividades, Resultados, Debug
  - Módulos de ASR (Vosk) e TTS nativo
  - Tela de Debug

#### Validação
| Funcionalidade | Status | Data |
|----------------|--------|------|
| SplashScreen   | OK     | 09/10/2025 |
| HomeScreen     | OK     | 09/10/2025 |
| ASR (Vosk)     | OK     | 09/10/2025 |
| TTS            | OK     | 09/10/2025 |
| DebugScreen    | OK     | 09/10/2025 |

---

## 1.1 Registro de Sincronização com GitHub

- Em 09/10/2025, o projeto PequenosPassos foi sincronizado com o repositório remoto no GitHub.
- Todos os commits locais foram enviados para: [https://github.com/Gregoruti/PequenosPassos](https://github.com/Gregoruti/PequenosPassos)
- Status: Repositório remoto criado e atualizado com histórico completo.

---

## 2. Status de Validação Integrado

Todas as funcionalidades acima foram validadas após a migração e renomeação.

---

## 3. Roadmap de Funcionalidades
- Cadastro de Profissionais e Pacientes (em desenvolvimento)
- Atividades (em desenvolvimento)
- Resultados (em desenvolvimento)
- Integração com módulos de ASR/TTS
- Expansão futura para novas rotinas e relatórios

---

**Versão do Documento**: 1.1.0
**Data**: 12/10/2025
**Status**: Documentação completa e sincronizada com projeto PequenosPassos
