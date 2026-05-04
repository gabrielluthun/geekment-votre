package com.geekementvotre.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.ui.theme.*

@Composable
fun ReservationSection(
    icon: ImageVector,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(GeekCardBackground) // Utilisation d'une couleur de thème si possible, sinon Color(0xFF141416)
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GeekGold,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = PlayfairDisplayFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            )
        }

        content()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "*Champ obligatoire",
            style = MaterialTheme.typography.labelSmall.copy(
                fontStyle = FontStyle.Italic,
                color = GeekSubtitle.copy(alpha = 0.7f)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = GeekSubtitle,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GeekInputBackground)
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = selectedValue.ifEmpty { "Sélectionner" },
                        color = if (selectedValue.isEmpty()) GeekSubtitle.copy(alpha = 0.5f) else Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        tint = GeekGold
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(GeekInputBackground)
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = selectionOption,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            onValueChange(selectionOption)
                            expanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = if (isError) GeekError else GeekSubtitle,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            cursorBrush = SolidColor(GeekGold),
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GeekInputBackground)
                        .let { 
                            if (isError) it.border(1.dp, GeekError, RoundedCornerShape(8.dp)) 
                            else it 
                        }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            color = GeekSubtitle.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
