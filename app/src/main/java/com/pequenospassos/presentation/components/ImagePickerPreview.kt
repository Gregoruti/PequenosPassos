package com.pequenospassos.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Preview do ImagePicker Component.
 *
 * Mostra diferentes estados do componente:
 * - Sem imagem
 * - Com imagem
 * - Versão compacta
 *
 * @since MVP-07 (16/10/2025)
 */

@Preview(name = "ImagePicker - Sem Imagem", showBackground = true)
@Composable
fun ImagePickerEmptyPreview() {
    MaterialTheme {
        Surface {
            var imageUri by remember { mutableStateOf<Uri?>(null) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ImagePicker(
                    imageUri = imageUri,
                    onImageSelected = { imageUri = it },
                    onImageRemoved = { imageUri = null },
                    label = "Imagem do Step"
                )
            }
        }
    }
}

@Preview(name = "CompactImagePicker", showBackground = true)
@Composable
fun CompactImagePickerPreview() {
    MaterialTheme {
        Surface {
            var imageUri by remember { mutableStateOf<Uri?>(null) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CompactImagePicker(
                    imageUri = imageUri,
                    onImageSelected = { imageUri = it },
                    onImageRemoved = { imageUri = null }
                )
            }
        }
    }
}

@Preview(name = "ImagePicker - Múltiplos", showBackground = true)
@Composable
fun ImagePickerMultiplePreview() {
    MaterialTheme {
        Surface {
            var image1 by remember { mutableStateOf<Uri?>(null) }
            var image2 by remember { mutableStateOf<Uri?>(null) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ImagePicker(
                    imageUri = image1,
                    onImageSelected = { image1 = it },
                    onImageRemoved = { image1 = null },
                    label = "Imagem Principal"
                )

                ImagePicker(
                    imageUri = image2,
                    onImageSelected = { image2 = it },
                    onImageRemoved = { image2 = null },
                    label = "Imagem do Step 1"
                )
            }
        }
    }
}
