package com.geekementvotre.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.ui.theme.DMSansFontFamily
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekSubtitle
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthField(
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier) {
        Text(
            text = "*Date de naissance",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                color = GeekSubtitle,
                fontWeight = FontWeight.Medium
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2C2C2E))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { showDatePicker = true }
                )
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = "JJ / MM / AAAA",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GeekSubtitle.copy(alpha = 0.5f)
                    )
                )
            }
            BasicTextField(
                value = value,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = DMSansFontFamily
                ),
                cursorBrush = SolidColor(GeekGold),
                singleLine = true,
                interactionSource = interactionSource
            )
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Input
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Date(millis)
                        val formatter = SimpleDateFormat("dd / MM / yyyy", Locale.FRANCE)
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        onDateSelected(formatter.format(date))
                    }
                    showDatePicker = false
                }) { Text("OK", color = GeekGold) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Annuler", color = GeekGold) }
            },
            colors = DatePickerDefaults.colors(containerColor = Color(0xFF1C1C1E))
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                title = null, headline = null,
                colors = DatePickerDefaults.colors(
                    titleContentColor = GeekGold,
                    headlineContentColor = Color.White,
                    weekdayContentColor = GeekSubtitle,
                    subheadContentColor = GeekGold,
                    yearContentColor = Color.White,
                    currentYearContentColor = GeekGold,
                    selectedYearContentColor = Color.Black,
                    selectedYearContainerColor = GeekGold,
                    dayContentColor = Color.White,
                    selectedDayContainerColor = GeekGold,
                    selectedDayContentColor = Color.Black,
                    todayContentColor = GeekGold,
                    todayDateBorderColor = GeekGold,
                    dateTextFieldColors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = GeekGold,
                        unfocusedIndicatorColor = GeekSubtitle,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = GeekGold,
                        focusedLabelColor = GeekGold,
                        unfocusedLabelColor = GeekSubtitle
                    )
                )
            )
        }
    }
}
