package com.pequenospassos.presentation.components

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * ImagePicker Component.
 *
 * Componente para seleção de imagem via câmera ou galeria.
 * Suporta preview, remoção e redimensionamento automático.
 *
 * Features:
 * - Seleção via câmera (captura foto)
 * - Seleção via galeria
 * - Preview de imagem selecionada
 * - Botão para remover imagem
 * - Redimensionamento automático (max 1024px)
 * - Salvamento em storage local
 * - Suporte a permissões (Android 13+)
 *
 * @param imageUri URI da imagem atual (pode ser null)
 * @param onImageSelected Callback quando uma imagem é selecionada
 * @param onImageRemoved Callback quando a imagem é removida
 * @param modifier Modificador do componente
 * @param label Label do campo (padrão: "Imagem")
 * @param maxSize Tamanho máximo da imagem em pixels (padrão: 1024)
 * @param showPreview Se deve exibir preview da imagem (padrão: true)
 *
 * @since MVP-07 (16/10/2025)
 * @author PequenosPassos Development Team
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePicker(
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onImageRemoved: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Imagem",
    maxSize: Int = 1024,
    showPreview: Boolean = true
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    // Temporary file for camera capture
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != null) {
            // Resize and save image
            val resizedUri = resizeAndSaveImage(context, tempPhotoUri!!, maxSize)
            resizedUri?.let { onImageSelected(it) }
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Resize and save image
            val resizedUri = resizeAndSaveImage(context, it, maxSize)
            resizedUri?.let { newUri -> onImageSelected(newUri) }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Label
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Image preview or placeholder
        if (showPreview) {
            ImagePreview(
                imageUri = imageUri,
                onRemove = if (imageUri != null) onImageRemoved else null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Camera button
            OutlinedButton(
                onClick = {
                    // Check camera permission
                    if (cameraPermissionState.status.isGranted) {
                        // Permission granted, launch camera
                        tempPhotoUri = createTempImageFile(context)
                        tempPhotoUri?.let { cameraLauncher.launch(it) }
                    } else {
                        // Request permission
                        cameraPermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Câmera",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Câmera")
            }

            // Gallery button
            OutlinedButton(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Galeria",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Galeria")
            }
        }
    }

    // Dialog for image source selection (alternative UI)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Selecionar imagem") },
            text = { Text("Escolha a fonte da imagem:") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        // Check camera permission before launching
                        if (cameraPermissionState.status.isGranted) {
                            tempPhotoUri = createTempImageFile(context)
                            tempPhotoUri?.let { cameraLauncher.launch(it) }
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }
                ) {
                    Text("Câmera")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        galleryLauncher.launch("image/*")
                    }
                ) {
                    Text("Galeria")
                }
            }
        )
    }
}

/**
 * Preview de imagem com botão de remover.
 */
@Composable
private fun ImagePreview(
    imageUri: Uri?,
    onRemove: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        if (imageUri != null) {
            // Show image
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Imagem selecionada",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Remove button
            if (onRemove != null) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remover imagem",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        } else {
            // Placeholder
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Nenhuma imagem",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Nenhuma imagem selecionada",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Cria um arquivo temporário para captura de foto.
 */
private fun createTempImageFile(context: Context): Uri? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        // Prefer external files dir, mas faz fallback para cacheDir se null
        val storageDir = context.getExternalFilesDir("Pictures") ?: context.cacheDir

        val imageFile = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )

        try {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Redimensiona e salva a imagem no storage local.
 * Retorna o URI da imagem salva.
 */
private fun resizeAndSaveImage(context: Context, sourceUri: Uri, maxSize: Int): Uri? {
    return try {
        // Decode bitmap com checagem de null
        val inputStream = try { context.contentResolver.openInputStream(sourceUri) } catch (e: Exception) { null }
        val originalBitmap = inputStream?.use { BitmapFactory.decodeStream(it) }

        if (originalBitmap == null) {
            // Não foi possível decodificar a imagem
            return null
        }

        // Correct orientation if needed
        val rotatedBitmap = correctImageOrientation(context, originalBitmap, sourceUri)

        // Calculate new dimensions
        val (newWidth, newHeight) = calculateNewDimensions(
            rotatedBitmap.width,
            rotatedBitmap.height,
            maxSize
        )

        // Resize bitmap
        val resizedBitmap = Bitmap.createScaledBitmap(
            rotatedBitmap,
            newWidth,
            newHeight,
            true
        )

        // Save to file
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_${timeStamp}.jpg"
        val storageDir = context.getExternalFilesDir("Pictures") ?: context.cacheDir
        val imageFile = File(storageDir, fileName)

        FileOutputStream(imageFile).use { out ->
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }

        // Clean up
        originalBitmap.recycle()
        rotatedBitmap.recycle()
        resizedBitmap.recycle()

        // Return URI
        try {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Corrige a orientação da imagem com base nos metadados EXIF.
 */
private fun correctImageOrientation(context: Context, bitmap: Bitmap, uri: Uri): Bitmap {
    return try {
        // Obter os metadados EXIF da imagem
        val inputStream = context.contentResolver.openInputStream(uri)
        val exif = inputStream?.let { ExifInterface(it) }

        // Obter a orientação da imagem
        val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        // Se a orientação não for normal, aplicar a rotação necessária
        if (orientation != null && orientation != ExifInterface.ORIENTATION_NORMAL) {
            val matrix = Matrix()

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }

            // Criar uma nova bitmap com a rotação aplicada
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            // Limpar a bitmap original
            bitmap.recycle()

            rotatedBitmap
        } else {
            // Se a orientação é normal ou não pôde ser determinada, retornar a bitmap original
            bitmap
        }
    } catch (e: Exception) {
        e.printStackTrace()
        bitmap // Retornar a bitmap original em caso de erro
    }
}

/**
 * Calcula novas dimensões mantendo aspect ratio.
 */
private fun calculateNewDimensions(
    originalWidth: Int,
    originalHeight: Int,
    maxSize: Int
): Pair<Int, Int> {
    if (originalWidth <= maxSize && originalHeight <= maxSize) {
        return Pair(originalWidth, originalHeight)
    }

    val ratio = originalWidth.toFloat() / originalHeight.toFloat()

    return if (originalWidth > originalHeight) {
        Pair(maxSize, (maxSize / ratio).toInt())
    } else {
        Pair((maxSize * ratio).toInt(), maxSize)
    }
}

/**
 * Versão compacta do ImagePicker sem preview.
 * Útil para formulários com espaço limitado.
 */
@Composable
fun CompactImagePicker(
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    onImageRemoved: () -> Unit,
    modifier: Modifier = Modifier
) {
    ImagePicker(
        imageUri = imageUri,
        onImageSelected = onImageSelected,
        onImageRemoved = onImageRemoved,
        modifier = modifier,
        showPreview = false
    )
}
