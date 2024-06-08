package com.allutils.feature_currency.presentation.composables

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

@Composable
fun CurrencyListItem(
    baseCode: String,
    currencyCode: String,
    rate: String,
    countryFlag: String,
) {

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (image, title, subtitle, source, starButton, time) = createRefs()

        PlaceholderImage(
            url = countryFlag,
            color = Color.Transparent,
            contentDescription = rate,
            modifier = Modifier
                .size(64.dp)
                .constrainAs(image) {
                    linkTo(start = parent.start, end = title.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = currencyCode,
            style = headingH4,
            color = darkestBlack,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(image.end, 16.dp)
                top.linkTo(image.top, 6.dp)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "1 $baseCode = $rate $currencyCode",
            style = bodyS,
            color = lightBlack,
            modifier = Modifier.constrainAs(subtitle) {
                start.linkTo(image.end, 16.dp)
                top.linkTo(title.bottom, 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = rate,
            style = headingH4,
            color = darkestBlack,
            modifier = Modifier.constrainAs(source) {
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
            CurrencyListItem("INR", "USD", "80", "https://flagsapi.com/BE/shiny/64.png")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
private fun Preview_2() {
    ConvertTheme {
        Column {
            CurrencyListItem("USD", "INR", "80", "https://flagsapi.com/BE/shiny/64.png")
        }
    }
}