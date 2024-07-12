package com.allutils.feature_emi.presentation.home

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputItem(
    textFieldValue: TextFieldValue,
    label: String,
    onTextChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true
) {
    val colors: TextFieldColors = if (enabled) {
        TextFieldDefaults.outlinedTextFieldColors()
    } else {
        TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
            cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            errorCursorColor = Color.Red,
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            errorBorderColor = Color.Red.copy(alpha = 0.3f),
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            errorLabelColor = Color.Red.copy(alpha = 0.3f)
        )
    }
    OutlinedTextField(
        enabled = enabled,
        value = textFieldValue,
        onValueChange = {
            onTextChanged(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        textStyle = textStyle,
        maxLines = 1,
        singleLine = true,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        colors = colors,
        modifier = modifier,
        visualTransformation = visualTransformation
    )
}
