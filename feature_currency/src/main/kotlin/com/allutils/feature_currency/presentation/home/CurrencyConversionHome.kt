package com.allutils.feature_currency.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.allutils.app_style_guide.R
import com.allutils.app_style_guide.animations.LottieAssetLoader
import com.allutils.app_style_guide.components.NumberField
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.base.presentation.composable.DataNotFoundAnim
import com.allutils.base.presentation.composable.ProgressIndicator
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.BottomSheetLayouts
import com.allutils.feature_currency.presentation.BottomSheetType
import com.allutils.feature_currency.presentation.basecode.BasecodeViewModel
import com.allutils.feature_currency.presentation.conversion.CurrencyListItem
import com.allutils.feature_currency.presentation.countries.CountriesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CurrencyConversionHome(
    conversionsViewModel: ConversionListViewModel,
    basecodeViewModel: BasecodeViewModel,
    countriesViewModel: CountriesViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    var currentBottomSheet: BottomSheetType? by remember { mutableStateOf(null) }

    @OptIn(ExperimentalMaterialApi::class)
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    @OptIn(ExperimentalMaterialApi::class)
    val openSheet = { coroutineScope.launch { sheetState.show() } }

    @OptIn(ExperimentalMaterialApi::class)
    val closeSheet = { coroutineScope.launch { sheetState.hide() } }

    BackHandler(sheetState.isVisible) { closeSheet() }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            currentBottomSheet?.let {
                BottomSheetLayouts(
                    basecodeViewModel = basecodeViewModel,
                    countriesViewModel = countriesViewModel,
                    conversionsViewModel = conversionsViewModel,
                    bottomSheetType = it,
                    sheetState = sheetState,
                    closeSheet = { closeSheet() }
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.fillMaxSize()
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
                                fontFamily = FontFamily(Font(R.font.roboto_bold))
                            ),
                            start = 0,
                            end = loginText.length
                        )
                        addStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.roboto_medium))
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
                            currentBottomSheet = BottomSheetType.BASE_CODE_LIST
                            openSheet()
                        }) {
                            Text(
                                text = "USD",
                                style = headingH4,
                                color = lightestGrey
                            )
                        }

                        NumberField("Enter amount ") {
                            //basecodeViewModel?.amount = it.toDouble()
                            basecodeViewModel?.loadBasecodeList()
                        }
                    }
                }
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
                        width = Dimension.fillToConstraints
                    },
            ) {
                val uiState: ConversionListViewModel.UiState by conversionsViewModel.uiStateFlow.collectAsStateWithLifecycle()

                uiState.let {
                    when (it) {
                        ConversionListViewModel.UiState.Error -> DataNotFoundAnim()
                        ConversionListViewModel.UiState.Loading -> ProgressIndicator()
                        is ConversionListViewModel.UiState.FavoriteContent ->  {
                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                val (localCurrency, button) = createRefs()
                                LazyColumn(
                                    Modifier
                                        .constrainAs(localCurrency) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                            height = Dimension.wrapContent
                                        }
                                ) {
                                    items(items = it.conversionRates, key = { it.currencyCode }) { currency ->
                                        CurrencyListItem(
                                            currency.baseCode,
                                            currency.currencyCode,
                                            currency.rate.toString(),
                                            "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png",
                                            conversionsViewModel.amount ?: 1.0
                                        )
                                    }
                                }

                                FloatingActionButton(
                                    onClick = {
                                        currentBottomSheet = BottomSheetType.COUNTRIES_LIST
                                        openSheet()
                                    },
                                    modifier = Modifier.padding(16.dp)
                                        .constrainAs(button) {
                                            bottom.linkTo(parent.bottom)
                                            end.linkTo(parent.end)
                                            height = Dimension.wrapContent
                                            width = Dimension.wrapContent
                                        }
                                ) {
                                    Icon(Icons.Filled.Add, contentDescription = "Add")
                                }
                            }
                        }

                        is ConversionListViewModel.UiState.LocalContent -> LocalCurrencyListCard(
                            conversionsViewModel,
                            it.conversionRates
                        )

                    }
                }
            }
        }
    }
}

fun onShowCountriesClick() {

}

fun onShowBaseCodesClick() {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ConversionList(
    viewModel: ConversionListViewModel,
    sheetState: ModalBottomSheetState
) {
    val uiState: ConversionListViewModel.UiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    uiState.let {
        when (it) {
            ConversionListViewModel.UiState.Error -> DataNotFoundAnim()
            ConversionListViewModel.UiState.Loading -> ProgressIndicator()
            is ConversionListViewModel.UiState.FavoriteContent -> FavoriteCurrencyListCard(
                viewModel,
                it.conversionRates,
                sheetState
            )

            is ConversionListViewModel.UiState.LocalContent -> LocalCurrencyListCard(
                viewModel,
                it.conversionRates
            )

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
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                ),
                start = 0,
                end = loginText.length
            )
            addStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
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
                onShowBaseCodesClick()
            }) {
                Text(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FavoriteCurrencyListCard(
    viewModel: ConversionListViewModel,
    conversionRates: List<ConversionRatesOutput>,
    sheetState: ModalBottomSheetState
) {
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (localCurrency, button) = createRefs()
        LazyColumn(
            Modifier
                .constrainAs(localCurrency) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
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

        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    sheetState.show()
                }
            },
            modifier = Modifier.padding(16.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun LocalCurrencyListCard(
    viewModel: ConversionListViewModel,
    conversionRates: List<ConversionRatesOutput>
) {
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (localCurrency, lottie, text, button) = createRefs()

        LazyColumn(
            Modifier
                .constrainAs(localCurrency) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                }
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

        Column(
            modifier = Modifier
                .offset(x = (-40).dp)
                .constrainAs(lottie) {
                    top.linkTo(localCurrency.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },
            horizontalAlignment = Alignment.Start
        ) {
            LottieAssetLoader(com.allutils.feature_currency.R.raw.hello)
        }

        Text(
            text = "Please add your favorite currencies",
            style = headingH4,
            color = darkestBlack,
            modifier = Modifier.constrainAs(text) {
                start.linkTo(lottie.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        FloatingActionButton(
            onClick = {
                onShowCountriesClick()
            },
            modifier = Modifier.padding(16.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

