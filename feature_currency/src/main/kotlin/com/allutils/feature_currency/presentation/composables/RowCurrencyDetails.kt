package com.allutils.feature_currency.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.theme.ConvertTheme
import com.allutils.base.common.res.Dimen
import com.allutils.base.presentation.composable.PlaceholderImage
import com.allutils.feature_currency.R
import com.allutils.feature_currency.presentation.CurrencyListViewModel

@Composable
internal fun RowCurrencyDetails(
    currencyCode: String,
    rate: String,
    countryFlag: String,
    viewModel: CurrencyListViewModel? = null
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PlaceholderImage(
                url = countryFlag,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                contentDescription = stringResource(id = R.string.country_flag),
                modifier = Modifier.size(Dimen.spaceXXL).padding(start = Dimen.spaceM)
            )
            Text(
                text = currencyCode,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = currencyCode,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.End).padding(bottom = 8.dp)
                )
                Text(
                    text = rate,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.End).padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
@Composable
fun Preview_1() {
    ConvertTheme {
        RowCurrencyDetails("USD", "80", "https://flagsapi.com/BE/shiny/64.png")
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
@Composable
fun Preview_2() {
    ConvertTheme {
        RowCurrencyDetails("INR", "76", "https://flagsapi.com/BE/shiny/64.png")
    }
}
