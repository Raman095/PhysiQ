package com.example.measuremate.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NewValueInputBar(
    modifier: Modifier = Modifier,
    date: String,
    isNewValueInputBarOpen: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    onDoneIconClick: () -> Unit,
    onDoneImeActionClick: () -> Unit,
    onCalendarIconClick: () -> Unit
) {
    var inputError by rememberSaveable { mutableStateOf<String?>(null) }
    inputError = when {
        value.isBlank() -> "Please enter a value"
        value.toFloatOrNull() == null -> "Invalid number"
        value.toFloat() < 1f -> "Value must be greater than 0"
        value.toFloat() > 1000f -> "Value must be less than 1000"
        else -> null
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isNewValueInputBarOpen,
        enter = slideInVertically() {h -> h},
        exit = slideOutVertically() {h -> h}
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(7.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //<editor-fold desc = "Content folded"
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                isError = inputError != null && value.isNotBlank(),
                supportingText = {Text(text = inputError.orEmpty())},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = {onDoneImeActionClick()}),
                // keyboardActions = here, when the user finishes typing in the text field and presses
                // ok button on the 'KEYBOARD', the keyboard will be removed, i.e we will clear focus
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = date)
                        IconButton( onClick = { onCalendarIconClick() } ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Date"
                            )
                        }
                    }
                }
            )
            FilledIconButton(
                onClick = { onDoneIconClick() },
                enabled = inputError == null
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done"
                )
            }
            //</editor-fold>
        }
    }

}

@Preview
@Composable
private fun NewValueInputBarPreview() {
    NewValueInputBar(
        value = "",
        onValueChange = {},
        onDoneIconClick = {},
        date = "7 Dec 2025",
        onDoneImeActionClick = {},
        isNewValueInputBarOpen = true,
        onCalendarIconClick = {}
    )
}