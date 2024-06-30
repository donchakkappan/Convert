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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.allutils.app_style_guide.R
import com.allutils.app_style_guide.styles.bodyS
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightBlack
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.data.utils.CurrencyCode
import com.allutils.feature_currency.data.utils.CurrencyCode.Companion.CURRENCIES
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.home.ConversionListViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BasecodeBottomSheet(
    viewModel: BasecodeViewModel,
    conversionsViewModel: ConversionListViewModel,
    closeSheet: () -> Unit
) {
    val uiState: BasecodeViewModel.UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            BasecodeViewModel.UiState.Error -> DataNotFoundAnim()
            BasecodeViewModel.UiState.Loading -> ProgressIndicator()
            is BasecodeViewModel.UiState.Content -> BasecodeList(
                conversionsViewModel,
                it.conversionRates,
                closeSheet
            )
        }
    }

}

@Composable
internal fun BasecodeList(
    conversionsViewModel: ConversionListViewModel,
    conversionRates: List<ConversionRatesOutput>,
    closeSheet: () -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(items = CURRENCIES, key = { it.code.toString() }) { currency ->
            BasecodeListItem(currencyCode = currency) {
                //conversionsViewModel.baseCode = it.code.toString()
                conversionsViewModel.showConversionRates(baseCode = it.code.toString())
                closeSheet.invoke()
            }
        }
    }
}

@Composable
internal fun BasecodeListItem(
    currencyCode: CurrencyCode,
    onItemClick: (CurrencyCode) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxWidth()
            .clickable { onItemClick(currencyCode) }
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val (flag, code, description) = createRefs()

        val randomPlaceHolder by rememberSaveable {
            mutableStateOf(R.drawable.ic_image_placeholder)
        }

        AsyncImage(
            model = currencyCode.flag,
            contentDescription = currencyCode.code,
            placeholder = painterResource(randomPlaceHolder),
            modifier = Modifier
                .size(64.dp)
                .constrainAs(flag) {
                    linkTo(start = parent.start, end = code.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = currencyCode.code.toString(),
            style = headingH4,
            color = darkestBlack,
            modifier = Modifier.constrainAs(code) {
                start.linkTo(flag.end, 16.dp)
                top.linkTo(flag.top, 6.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = currencyCode.name.toString(),
            style = bodyS,
            color = lightBlack,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(flag.end, 16.dp)
                top.linkTo(code.bottom, 8.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}
