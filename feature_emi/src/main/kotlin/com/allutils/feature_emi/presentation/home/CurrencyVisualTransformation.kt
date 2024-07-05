package com.allutils.feature_emi.presentation.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import java.text.NumberFormat
import java.util.Currency

private class CurrencyVisualTransformation(
    currencyCode: String
) : VisualTransformation {

    private val numberFormatter = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance(currencyCode)
        maximumFractionDigits = 0
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }
        if (originalText.isDigitsOnly().not()) {
            Log.w("TAG", "Prize visual transformation require using digits only but found [$originalText]")
            return TransformedText(text, OffsetMapping.Identity)
        }

        val formattedText = numberFormatter.format(originalText.toInt())
        return TransformedText(
            AnnotatedString(formattedText),
            CurrencyOffsetMapping(originalText, formattedText)
        )
    }
}

@Composable
fun rememberCurrencyVisualTransformation(currency: String): VisualTransformation {
    val inspectionMode = LocalInspectionMode.current
    return remember(currency) {
        if (inspectionMode) {
            VisualTransformation.None
        } else {
            CurrencyVisualTransformation(currency)
        }
    }
}
