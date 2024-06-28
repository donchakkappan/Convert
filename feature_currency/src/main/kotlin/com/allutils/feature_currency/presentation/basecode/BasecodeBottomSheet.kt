package com.allutils.feature_currency.presentation.basecode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.styles.bodyS
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightBlack
import com.allutils.app_style_guide.templates.PlaceholderImage
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BasecodeBottomSheet(
    viewModel: BasecodeViewModel,
    closeSheet: () -> Unit
) {
    val uiState: BasecodeViewModel.UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            BasecodeViewModel.UiState.Error -> DataNotFoundAnim()
            BasecodeViewModel.UiState.Loading -> ProgressIndicator()
            is BasecodeViewModel.UiState.Content -> BasecodeList(it.conversionRates)
        }
    }

}

@Composable
internal fun BasecodeList(conversionRates: List<ConversionRatesOutput>) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(items = conversionRates, key = { it.currencyCode }) { currency ->
            BasecodeListItem(
                currency.baseCode,
                currency.currencyCode,
                currency.rate.toString(),
                "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png",
                1.0
            )
        }
    }
}

@Composable
internal fun BasecodeListItem(
    baseCode: String,
    currencyCode: String,
    rate: String,
    countryFlag: String,
    amount: Double
) {

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
            text = formattedNumber,
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
