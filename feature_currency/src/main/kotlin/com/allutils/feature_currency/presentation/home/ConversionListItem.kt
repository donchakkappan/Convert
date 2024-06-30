package com.allutils.feature_currency.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.allutils.app_style_guide.styles.bodyS
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightBlack
import com.allutils.app_style_guide.templates.PlaceholderImage
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.feature_currency.data.utils.CurrencyCode.Companion.CURRENCIES
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun CurrencyListItem(
    baseCode: String,
    currencyCode: String,
    rate: String,
    countryFlag: String,
    amount: Double
) {

    val symbol = CURRENCIES.find { it.code.toString() == currencyCode }?.symbol
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (flag, code, description, convertedRate) = createRefs()

        PlaceholderImage(
            url = countryFlag,
            color = Color.Transparent,
            contentDescription = rate,
            modifier = Modifier
                .size(64.dp)
                .constrainAs(flag) {
                    linkTo(start = parent.start, end = code.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = currencyCode,
            style = headingH4,
            color = darkestBlack,
            modifier = Modifier.constrainAs(code) {
                start.linkTo(flag.end, 16.dp)
                top.linkTo(flag.top, 6.dp)
                width = Dimension.fillToConstraints
            }
        )
        val formattedRate =
            BigDecimal(rate.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        Text(
            text = "1 $baseCode = $formattedRate $currencyCode",
            style = bodyS,
            color = lightBlack,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(flag.end, 16.dp)
                top.linkTo(code.bottom, 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        val formattedNumber =
            BigDecimal(rate.toDouble() * amount).setScale(2, RoundingMode.HALF_EVEN).toString()
        Text(
            text = "$symbol $formattedNumber",
            style = headingH4,
            color = darkestBlack,
            modifier = Modifier.constrainAs(convertedRate) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
private fun Preview_1() {
    ConvertTheme {
        Column {
            CurrencyListItem("INR", "USD", "80", "https://flagsapi.com/BE/shiny/64.png", 1.0)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
private fun Preview_2() {
    ConvertTheme {
        Column {
            CurrencyListItem("USD", "INR", "80", "https://flagsapi.com/BE/shiny/64.png", 1.0)
        }
    }
}
