package com.allutils.feature_currency.presentation.conversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.components.NumberField
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.ConversionListViewModel
import com.allutils.feature_currency.presentation.basecode.BasecodeList
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(viewModel: ConversionListViewModel) {
    val uiState: ConversionListViewModel.UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            ConversionListViewModel.UiState.Error -> DataNotFoundAnim()
            ConversionListViewModel.UiState.Loading -> ProgressIndicator()
            is ConversionListViewModel.UiState.Content -> ConversionList(
                it.conversionRates,
                viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ConversionList(
    conversionRates: List<ConversionRatesOutput>,
    viewModel: ConversionListViewModel
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    ModalBottomSheetLayout(
        sheetState = sheetState, sheetContent = {
            BasecodeList(sheetState, viewModel)
        }, modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val guideline1 = createGuidelineFromTop(0.25f)
            val guideline2 = createGuidelineFromTop(0.35f)
            val (image, loginForm) = createRefs()
            Card(
                backgroundColor = darkestBlue,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(guideline2)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
            ) {
                HeaderCard(viewModel, sheetState)
            }
            Card(
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .constrainAs(loginForm) {
                        top.linkTo(guideline1)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
            ) {
                CurrencyListCard(viewModel, conversionRates)
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HeaderCard(
    viewModel: ConversionListViewModel,
    sheetState: ModalBottomSheetState
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {

        val loginText = "Convert Currency Here"
        val loginWord = "Convert"
        val loginAnnotatedString = buildAnnotatedString {
            append(loginText)
            addStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(com.allutils.app_style_guide.R.font.roboto_bold))
                ),
                start = 0,
                end = loginText.length
            )
            addStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontFamily = FontFamily(Font(com.allutils.app_style_guide.R.font.roboto_medium))
                ),
                start = 0,
                end = loginWord.length
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 20.dp),
            text = loginAnnotatedString,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
        )

        Row {
            IconButton(onClick = {
                coroutineScope.launch {
                    sheetState.show()
                }
            }) {
                androidx.compose.material3.Text(
                    text = viewModel?.baseCode.toString(),
                    style = headingH4,
                    color = lightestGrey
                )
            }

            NumberField("Enter amount ") {
                viewModel?.amount = it.toDouble()
                viewModel?.showConversionRates()
            }
        }
    }
}

@Composable
internal fun CurrencyListCard(
    viewModel: ConversionListViewModel,
    conversionRates: List<ConversionRatesOutput>
) {
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(items = conversionRates, key = { it.currencyCode }) { currency ->
            CurrencyListItem(
                currency.baseCode,
                currency.currencyCode,
                currency.rate.toString(),
                "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png",
                viewModel.amount ?: 1.0
            )
        }
    }
}




