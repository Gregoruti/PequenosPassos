package com.pequenospassos.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pequenospassos.domain.model.CategoryGroup
import com.pequenospassos.domain.model.TaskCategory

/**
 * CategoryPicker Component.
 *
 * Componente para seleção de categoria de tarefa.
 * Exibe um dropdown com as 27 categorias organizadas em 6 grupos.
 *
 * Features:
 * - Dropdown com ExposedDropdownMenu
 * - Categorias agrupadas por tipo
 * - Exibe emoji + nome de cada categoria
 * - Cabeçalhos de grupo visíveis
 * - Busca/filtro (opcional)
 *
 * @param selectedCategory Categoria atualmente selecionada (pode ser null)
 * @param onCategorySelected Callback quando uma categoria é selecionada
 * @param modifier Modificador do componente
 * @param label Label do campo (padrão: "Categoria")
 * @param isError Se o campo está em estado de erro
 * @param errorMessage Mensagem de erro a exibir
 *
 * @since MVP-07 (16/10/2025)
 * @author PequenosPassos Development Team
 *
 * @sample CategoryPickerPreview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPicker(
    selectedCategory: TaskCategory?,
    onCategorySelected: (TaskCategory) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Categoria",
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val categoriesByGroup = remember { TaskCategory.getCategoriesByGroup() }

    Column(modifier = modifier) {
        // Campo de seleção (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedCategory?.getFullDisplay() ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Selecionar categoria"
                    )
                },
                isError = isError,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors()
            )

            // Menu dropdown com categorias agrupadas
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                ) {
                    // Iterar por cada grupo
                    categoriesByGroup.forEach { (group, categories) ->
                        // Cabeçalho do grupo
                        item {
                            CategoryGroupHeader(group = group)
                        }

                        // Categorias do grupo
                        items(categories) { category ->
                            CategoryItem(
                                category = category,
                                isSelected = category == selectedCategory,
                                onClick = {
                                    onCategorySelected(category)
                                    expanded = false
                                }
                            )
                        }

                        // Divisor entre grupos (exceto no último)
                        if (group != CategoryGroup.OUTROS) {
                            item {
                                Divider(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                }
            }
        }

        // Mensagem de erro
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

/**
 * Cabeçalho de grupo de categorias.
 */
@Composable
private fun CategoryGroupHeader(group: CategoryGroup) {
    Text(
        text = group.getFullDisplay(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

/**
 * Item individual de categoria.
 */
@Composable
private fun CategoryItem(
    category: TaskCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Emoji + Nome da categoria
        Text(
            text = category.getFullDisplay(),
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )

        // Indicador de selecionado
        if (isSelected) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Selecionado",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Versão simplificada do CategoryPicker com apenas dropdown básico.
 * Útil quando não há necessidade de agrupamento visual.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCategoryPicker(
    selectedCategory: TaskCategory?,
    onCategorySelected: (TaskCategory) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Categoria"
) {
    var expanded by remember { mutableStateOf(false) }
    val allCategories = remember { TaskCategory.entries.toList() }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedCategory?.getFullDisplay() ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Selecionar categoria"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            allCategories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.getFullDisplay()) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
