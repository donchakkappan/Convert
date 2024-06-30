package com.allutils.app_style_guide.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.styles.bodyM
import com.allutils.app_style_guide.styles.darkBlue
import com.allutils.app_style_guide.styles.lightestBlue
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.app_style_guide.theme.ConvertTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberField(placeholder: String, modifier: Modifier = Modifier, onDone: (String) -> Unit) {
    var numberText by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(value = numberText,
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth().clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = darkBlue,
            unfocusedBorderColor = darkBlue
        ),
        maxLines = 1,
        textStyle = bodyM.copy(color = lightestGrey),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone(numberText.text)
                keyboardController?.hide()
            }
        ),
        placeholder = { Text(text = placeholder, style = bodyM.copy(color = lightestGrey)) },
        onValueChange = {
            numberText = it
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
private fun Preview_1() {
    ConvertTheme {
        Column {
            NumberField("Enter a number") {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
private fun Preview_2() {
    ConvertTheme {
        Column {
            NumberField("Enter a number") {}
        }
    }
}
