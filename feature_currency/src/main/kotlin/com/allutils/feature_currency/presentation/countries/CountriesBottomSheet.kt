package com.allutils.feature_currency.presentation.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.templates.PlaceholderImage
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.home.ConversionListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CountriesBottomSheet(
    sheetState: ModalBottomSheetState,
    countriesViewModel: CountriesViewModel,
    conversionRatesViewModel: ConversionListViewModel? = null,
    closeSheet: () -> Unit
) {
    val uiState: CountriesViewModel.UiState by countriesViewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            CountriesViewModel.UiState.Error -> DataNotFoundAnim()
            CountriesViewModel.UiState.Loading -> ProgressIndicator()
            is CountriesViewModel.UiState.Content -> CountriesList(
                sheetState,
                it.conversionRates,
                countriesViewModel,
                conversionRatesViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun CountriesList(
    sheetState: ModalBottomSheetState,
    conversionRates: List<ConversionRatesOutput>,
    countriesViewModel: CountriesViewModel? = null,
    conversionRatesViewModel: ConversionListViewModel? = null
) {
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(items = conversionRates, key = { it.currencyCode }) { currency ->
            CountriesListItem(
                currency.currencyCode,
                "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png"
            ) {

                countriesViewModel?.baseCode = it
                coroutineScope.launch {
                    conversionRatesViewModel?.markFavoriteAndGetAll(favoriteCode = it)
                }

                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        }
    }

}

@Composable
fun CountriesListItem(
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

