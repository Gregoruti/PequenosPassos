# MVP12 - Backup e Sincronização

**Versão do Documento:** 1.0  
**Data:** 23/10/2025  
**Status:** 📋 Planejado  
**Prioridade:** 🟡 MÉDIA-BAIXA

---

## 1. VISÃO GERAL

### 1.1. Objetivo

Implementar sistema de backup e sincronização de dados para garantir que as 
informações não sejam perdidas e possam ser acessadas em múltiplos 
dispositivos.

### 1.2. Situação Atual

**✅ Implementado:**
- Persistência local via Room Database
- Dados salvos localmente no dispositivo

**⚠️ Pendente:**
- Sistema de backup local (export/import)
- Backup em nuvem
- Sincronização entre dispositivos
- Restauração de dados
- Versionamento de backups
- Backup automático agendado

### 1.3. Casos de Uso

- 📱 Trocar de dispositivo sem perder dados
- 💾 Criar backup antes de atualizar app
- 🔄 Sincronizar entre tablet e celular
- 👨‍👩‍👧‍👦 Compartilhar perfis entre dispositivos familiares
- 🛡️ Proteção contra perda de dados

---

## 2. REQUISITOS FUNCIONAIS

### RF01 - Export/Import Local

**Descrição:** Exportar dados para arquivo JSON e importar posteriormente

**Critérios de Aceitação:**
- [ ] Exportar todos os dados para arquivo JSON
- [ ] Escolher local de salvamento
- [ ] Importar arquivo JSON válido
- [ ] Validação de integridade do arquivo
- [ ] Suporte a exportação parcial (apenas perfil selecionado)
- [ ] Backup inclui imagens (como Base64 ou refs)

**Formato do Arquivo:**
```json
{
  "version": "1.9.6",
  "exportDate": "2025-10-23T10:30:00",
  "childProfiles": [...],
  "tasks": [...],
  "steps": [...],
  "achievements": [...],
  "settings": {...}
}
```

### RF02 - Backup em Nuvem

**Descrição:** Salvar dados automaticamente em serviço de nuvem

**Critérios de Aceitação:**
- [ ] Integração com Google Drive ou Firebase
- [ ] Backup automático configurável
- [ ] Backup manual sob demanda
- [ ] Lista de backups disponíveis
- [ ] Restauração de backup específico
- [ ] Sincronização de imagens

**Opções de Serviço:**
1. **Google Drive** (Recomendado)
   - Usa conta Google do dispositivo
   - Armazenamento gratuito (15 GB)
   - API bem documentada
   
2. **Firebase Storage**
   - Integrado com Firebase
   - Bom para sincronização real-time
   - Requer configuração adicional

### RF03 - Sincronização Multi-Dispositivo

**Descrição:** Manter dados sincronizados entre dispositivos

**Critérios de Aceitação:**
- [ ] Detectar conflitos de sincronização
- [ ] Resolver conflitos automaticamente quando possível
- [ ] Permitir escolha manual em conflitos complexos
- [ ] Sincronizar apenas mudanças (delta sync)
- [ ] Indicador visual de status de sincronização
- [ ] Funcionar em background

**Estratégias de Conflito:**
- **Last Write Wins:** Última modificação prevalece
- **Merge:** Mesclar mudanças quando possível
- **Manual:** Usuario escolhe qual versão manter

### RF04 - Backup Automático

**Descrição:** Agendar backups automáticos

**Critérios de Aceitação:**
- [ ] Configurar frequência (diário, semanal, mensal)
- [ ] Backup apenas em WiFi (opcional)
- [ ] Backup apenas ao carregar (opcional)
- [ ] Notificação de backup concluído
- [ ] Retry automático em caso de falha
- [ ] Limite de backups mantidos

**Configurações:**
```kotlin
data class BackupConfig(
    val autoBackupEnabled: Boolean = true,
    val frequency: BackupFrequency = BackupFrequency.WEEKLY,
    val wifiOnly: Boolean = true,
    val chargingOnly: Boolean = false,
    val maxBackupsKept: Int = 5,
    val includeImages: Boolean = true
)

enum class BackupFrequency {
    DAILY, WEEKLY, MONTHLY
}
```

### RF05 - Restauração de Dados

**Descrição:** Restaurar dados de backup

**Critérios de Aceitação:**
- [ ] Listar backups disponíveis (local e nuvem)
- [ ] Visualizar detalhes do backup (data, tamanho, versão)
- [ ] Restaurar completamente
- [ ] Restaurar apenas perfil específico
- [ ] Confirmar antes de sobrescrever dados atuais
- [ ] Criar backup automático antes de restaurar

---

## 3. MODELO DE DADOS

### 3.1. BackupMetadata

```kotlin
@Entity(tableName = "backup_metadata")
data class BackupMetadata(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val fileName: String,
    val backupDate: LocalDateTime,
    val appVersion: String,
    val location: BackupLocation,
    val size: Long, // em bytes
    val profilesCount: Int,
    val tasksCount: Int,
    val includesImages: Boolean,
    val isAutomatic: Boolean,
    val cloudUrl: String? = null
)

enum class BackupLocation {
    LOCAL, GOOGLE_DRIVE, FIREBASE
}
```

### 3.2. SyncStatus

```kotlin
@Entity(tableName = "sync_status")
data class SyncStatus(
    @PrimaryKey val id: Long = 1,
    val lastSyncDate: LocalDateTime?,
    val syncEnabled: Boolean = false,
    val lastSyncResult: SyncResult = SyncResult.NEVER_SYNCED,
    val pendingChanges: Int = 0,
    val cloudBackupId: String? = null
)

enum class SyncResult {
    NEVER_SYNCED, SUCCESS, FAILED, CONFLICT, IN_PROGRESS
}
```

### 3.3. BackupData (Data Class para Export)

```kotlin
data class BackupData(
    val version: String,
    val exportDate: LocalDateTime,
    val childProfiles: List<ChildProfile>,
    val tasks: List<Task>,
    val steps: List<Step>,
    val achievements: List<Achievement>?,
    val rewards: List<Reward>?,
    val settings: AppSettings,
    val images: Map<String, String>? = null // URI -> Base64
)
```

---

## 4. CASOS DE USO

### 4.1. ExportDataUseCase

```kotlin
class ExportDataUseCase(
    private val childProfileRepository: ChildProfileRepository,
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository,
    private val achievementRepository: AchievementRepository?,
    private val settingsRepository: AppSettingsRepository,
    private val imageExporter: ImageExporter
) {
    suspend operator fun invoke(
        includeImages: Boolean = true,
        profileId: Long? = null
    ): Result<BackupData> {
        return try {
            val profiles = if (profileId != null) {
                listOfNotNull(childProfileRepository.getProfileById(profileId))
            } else {
                childProfileRepository.getAllProfilesSync()
            }
            
            val tasks = if (profileId != null) {
                taskRepository.getTasksByProfileSync(profileId)
            } else {
                taskRepository.getAllTasksSync()
            }
            
            val steps = tasks.flatMap { task ->
                stepRepository.getStepsByTaskSync(task.id)
            }
            
            val achievements = achievementRepository?.let {
                if (profileId != null) {
                    it.getAchievementsByChildSync(profileId)
                } else {
                    it.getAllAchievementsSync()
                }
            }
            
            val settings = settingsRepository.getSettingsSync()
            
            val images = if (includeImages) {
                imageExporter.exportImages(tasks, steps)
            } else {
                null
            }
            
            val backupData = BackupData(
                version = BuildConfig.VERSION_NAME,
                exportDate = LocalDateTime.now(),
                childProfiles = profiles,
                tasks = tasks,
                steps = steps,
                achievements = achievements,
                rewards = null,
                settings = settings,
                images = images
            )
            
            Result.success(backupData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 4.2. ImportDataUseCase

```kotlin
class ImportDataUseCase(
    private val childProfileRepository: ChildProfileRepository,
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository,
    private val achievementRepository: AchievementRepository?,
    private val settingsRepository: AppSettingsRepository,
    private val imageImporter: ImageImporter,
    private val validator: BackupValidator
) {
    suspend operator fun invoke(
        backupData: BackupData,
        overwriteExisting: Boolean = false
    ): Result<ImportResult> {
        return try {
            // Validar backup
            if (!validator.isValid(backupData)) {
                return Result.failure(InvalidBackupException("Backup inválido"))
            }
            
            // Criar backup atual antes de importar
            if (overwriteExisting) {
                // TODO: criar backup de segurança
            }
            
            // Importar dados
            if (overwriteExisting) {
                clearAllData()
            }
            
            // Importar profiles
            backupData.childProfiles.forEach { profile ->
                childProfileRepository.saveProfile(profile)
            }
            
            // Importar tasks
            backupData.tasks.forEach { task ->
                taskRepository.saveTask(task)
            }
            
            // Importar steps
            backupData.steps.forEach { step ->
                stepRepository.saveStep(step)
            }
            
            // Importar achievements
            backupData.achievements?.forEach { achievement ->
                achievementRepository?.saveAchievement(achievement)
            }
            
            // Importar settings
            settingsRepository.updateSettings(backupData.settings)
            
            // Importar imagens
            backupData.images?.let { images ->
                imageImporter.importImages(images)
            }
            
            val result = ImportResult(
                profilesImported = backupData.childProfiles.size,
                tasksImported = backupData.tasks.size,
                stepsImported = backupData.steps.size,
                imagesImported = backupData.images?.size ?: 0
            )
            
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun clearAllData() {
        // Implementar limpeza de dados
    }
}

data class ImportResult(
    val profilesImported: Int,
    val tasksImported: Int,
    val stepsImported: Int,
    val imagesImported: Int
)
```

### 4.3. BackupToCloudUseCase

```kotlin
class BackupToCloudUseCase(
    private val exportDataUseCase: ExportDataUseCase,
    private val cloudStorage: CloudStorageService,
    private val backupMetadataRepository: BackupMetadataRepository
) {
    suspend operator fun invoke(
        includeImages: Boolean = true
    ): Result<BackupMetadata> {
        return try {
            // Exportar dados
            val backupData = exportDataUseCase(includeImages).getOrThrow()
            
            // Converter para JSON
            val json = Json.encodeToString(backupData)
            val bytes = json.toByteArray()
            
            // Upload para cloud
            val cloudUrl = cloudStorage.upload(
                fileName = "backup_${System.currentTimeMillis()}.json",
                data = bytes
            )
            
            // Salvar metadata
            val metadata = BackupMetadata(
                fileName = "backup_${System.currentTimeMillis()}.json",
                backupDate = LocalDateTime.now(),
                appVersion = BuildConfig.VERSION_NAME,
                location = BackupLocation.GOOGLE_DRIVE,
                size = bytes.size.toLong(),
                profilesCount = backupData.childProfiles.size,
                tasksCount = backupData.tasks.size,
                includesImages = includeImages,
                isAutomatic = false,
                cloudUrl = cloudUrl
            )
            
            backupMetadataRepository.save(metadata)
            
            Result.success(metadata)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 4.4. ScheduleAutoBackupUseCase

```kotlin
class ScheduleAutoBackupUseCase(
    private val workManager: WorkManager,
    private val context: Context
) {
    operator fun invoke(config: BackupConfig) {
        if (!config.autoBackupEnabled) {
            workManager.cancelUniqueWork(AUTO_BACKUP_WORK_NAME)
            return
        }
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(
                if (config.wifiOnly) NetworkType.UNMETERED else NetworkType.CONNECTED
            )
            .setRequiresCharging(config.chargingOnly)
            .build()
        
        val interval = when (config.frequency) {
            BackupFrequency.DAILY -> 1L to TimeUnit.DAYS
            BackupFrequency.WEEKLY -> 7L to TimeUnit.DAYS
            BackupFrequency.MONTHLY -> 30L to TimeUnit.DAYS
        }
        
        val workRequest = PeriodicWorkRequestBuilder<AutoBackupWorker>(
            interval.first, interval.second
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        workManager.enqueueUniquePeriodicWork(
            AUTO_BACKUP_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
    
    companion object {
        private const val AUTO_BACKUP_WORK_NAME = "auto_backup_work"
    }
}
```

---

## 5. SERVIÇOS DE CLOUD

### 5.1. CloudStorageService Interface

```kotlin
interface CloudStorageService {
    suspend fun upload(fileName: String, data: ByteArray): String
    suspend fun download(url: String): ByteArray
    suspend fun delete(url: String)
    suspend fun listBackups(): List<CloudBackupInfo>
}

data class CloudBackupInfo(
    val id: String,
    val name: String,
    val url: String,
    val size: Long,
    val createdDate: LocalDateTime
)
```

### 5.2. GoogleDriveService Implementation

```kotlin
class GoogleDriveService(
    private val context: Context,
    private val googleSignInAccount: GoogleSignInAccount
) : CloudStorageService {
    
    private val driveService: Drive by lazy {
        val credential = GoogleAccountCredential.usingOAuth2(
            context,
            listOf(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = googleSignInAccount.account
        
        Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("Pequenos Passos")
            .build()
    }
    
    override suspend fun upload(fileName: String, data: ByteArray): String {
        return withContext(Dispatchers.IO) {
            val fileMetadata = File().apply {
                name = fileName
                parents = listOf(getOrCreateAppFolder())
            }
            
            val mediaContent = ByteArrayContent(
                "application/json",
                data
            )
            
            val file = driveService.files()
                .create(fileMetadata, mediaContent)
                .setFields("id, webViewLink")
                .execute()
            
            file.webViewLink
        }
    }
    
    override suspend fun download(url: String): ByteArray {
        return withContext(Dispatchers.IO) {
            val fileId = extractFileIdFromUrl(url)
            val outputStream = ByteArrayOutputStream()
            
            driveService.files()
                .get(fileId)
                .executeMediaAndDownloadTo(outputStream)
            
            outputStream.toByteArray()
        }
    }
    
    override suspend fun delete(url: String) {
        withContext(Dispatchers.IO) {
            val fileId = extractFileIdFromUrl(url)
            driveService.files().delete(fileId).execute()
        }
    }
    
    override suspend fun listBackups(): List<CloudBackupInfo> {
        return withContext(Dispatchers.IO) {
            val folderId = getOrCreateAppFolder()
            
            val result = driveService.files().list()
                .setQ("'$folderId' in parents and trashed=false")
                .setSpaces("drive")
                .setFields("files(id, name, size, createdTime, webViewLink)")
                .execute()
            
            result.files.map { file ->
                CloudBackupInfo(
                    id = file.id,
                    name = file.name,
                    url = file.webViewLink,
                    size = file.getSize(),
                    createdDate = LocalDateTime.parse(file.createdTime.toString())
                )
            }
        }
    }
    
    private fun getOrCreateAppFolder(): String {
        // Implementar criação/busca de pasta do app
        return "folder_id"
    }
    
    private fun extractFileIdFromUrl(url: String): String {
        // Extrair ID do arquivo da URL
        return url.substringAfter("id=").substringBefore("&")
    }
}
```

---

## 6. WORKERS

### 6.1. AutoBackupWorker

```kotlin
class AutoBackupWorker(
    context: Context,
    params: WorkerParameters,
    private val backupToCloudUseCase: BackupToCloudUseCase
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        return try {
            val includeImages = inputData.getBoolean(KEY_INCLUDE_IMAGES, true)
            
            backupToCloudUseCase(includeImages).getOrThrow()
            
            showNotification("Backup concluído com sucesso")
            
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                showNotification("Falha no backup automático", isError = true)
                Result.failure()
            }
        }
    }
    
    private fun showNotification(message: String, isError: Boolean = false) {
        // Implementar notificação
    }
    
    companion object {
        const val KEY_INCLUDE_IMAGES = "include_images"
    }
}
```

---

## 7. TELAS

### 7.1. BackupScreen

```kotlin
@Composable
fun BackupScreen(
    viewModel: BackupViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val backups by viewModel.backups.collectAsState()
    val syncStatus by viewModel.syncStatus.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Backup e Sincronização") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.createBackup() }
            ) {
                Icon(Icons.Default.CloudUpload, "Criar Backup")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Status de sincronização
            SyncStatusCard(syncStatus)
            
            // Configurações rápidas
            BackupConfigSection(
                onExport = { viewModel.exportToFile() },
                onImport = { viewModel.importFromFile() }
            )
            
            // Lista de backups
            Text(
                text = "Backups Disponíveis",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyColumn {
                items(backups) { backup ->
                    BackupItem(
                        backup = backup,
                        onRestore = { viewModel.restore(backup) },
                        onDelete = { viewModel.delete(backup) }
                    )
                }
            }
        }
    }
}
```

---

## 8. CRONOGRAMA

### Fase 1 - Export/Import Local (2 dias)
- [ ] Implementar ExportDataUseCase
- [ ] Implementar ImportDataUseCase
- [ ] Validação de backups
- [ ] Testes

### Fase 2 - Integração Google Drive (2 dias)
- [ ] Configurar Google Drive API
- [ ] Implementar GoogleDriveService
- [ ] Upload/Download de backups
- [ ] Testes

### Fase 3 - Backup Automático (1 dia)
- [ ] Implementar AutoBackupWorker
- [ ] Agendamento com WorkManager
- [ ] Notificações
- [ ] Testes

### Fase 4 - UI e Sincronização (2 dias)
- [ ] Criar BackupScreen
- [ ] Implementar sincronização
- [ ] Resolução de conflitos
- [ ] Testes E2E

**Total: 7 dias**

---

## 9. DEPENDÊNCIAS

```kotlin
// Google Drive
implementation 'com.google.android.gms:play-services-auth:20.7.0'
implementation 'com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0'

// WorkManager
implementation 'androidx.work:work-runtime-ktx:2.8.1'

// Serialization
implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0'
```

---

**Status:** Planejado  
**Próxima Ação:** Implementar após MVP08, MVP09, MVP10, MVP11

