package com.allutils.feature_currency.presentation.basecode

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
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.templates.PlaceholderImage
import com.allutils.app_style_guide.theme.ConvertTheme

@Composable
fun BasecodeListItem(
    currencyCode: String,
    countryFlag: String,
    onItemClick: (String) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxWidth()
            .clickable { onItemClick(currencyCode) }
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (flag, code) = createRefs()

        PlaceholderImage(
            url = countryFlag,
            color = Color.Transparent,
            contentDescription = countryFlag,
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
                top.linkTo(flag.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
private fun Preview_1() {
    ConvertTheme {
        Column {
            BasecodeListItem("INR", "https://flagsapi.com/BE/shiny/64.png") {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
private fun Preview_2() {
    ConvertTheme {
        Column {
            BasecodeListItem("USD", "https://flagsapi.com/BE/shiny/64.png") {}
        }
    }
}