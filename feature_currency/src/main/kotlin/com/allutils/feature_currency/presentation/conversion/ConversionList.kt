package com.allutils.feature_currency.presentation.conversion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
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
import com.allutils.feature_currency.R
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.presentation.ConversionListViewModel
import com.allutils.feature_currency.presentation.basecode.BasecodeList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ConversionAppBar(
    sheetState: ModalBottomSheetState,
    viewModel: ConversionListViewModel?
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {
            NumberField("Enter amount ") {
                viewModel?.amount = it.toDouble()
                viewModel?.getConversionRates()
            }
        },
        navigationIcon = {
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
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = darkestBlue,
            titleContentColor = lightestGrey
        )
    )
}

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
    viewModel: ConversionListViewModel? = null
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    ModalBottomSheetLayout(
        sheetState = sheetState, sheetContent = {
            viewModel?.let { BasecodeList(sheetState, it) }
        }, modifier = Modifier.fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()

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
                            viewModel?.getConversionRates()
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
                    },
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
                            viewModel?.amount ?: 1.0
                        )
                    }
                }
            }
        }

//        Scaffold(
//            topBar = {
//                ConversionAppBar(sheetState, viewModel)
//            }
//        ) { innerPadding ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//            ) {
//                LazyColumn(
//                    Modifier.fillMaxWidth()
//                ) {
//                    items(items = conversionRates, key = { it.currencyCode }) { currency ->
//                        CurrencyListItem(
//                            currency.baseCode,
//                            currency.currencyCode,
//                            currency.rate.toString(),
//                            "https://flagsapi.com/" + currency.currencyCode.take(2) + "/shiny/64.png",
//                            viewModel?.amount ?: 1.0
//                        )
//                    }
//                }
//
//            }
//        }
    }

}

@Composable
fun CustomStyleTextField(
    placeHolder: String,
    leadingIconId: Int,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        value = textState.value,
        onValueChange = { valueChanged ->
            textState.value = valueChanged
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = { Text(text = placeHolder) },
        leadingIcon = {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Image(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .size(18.dp),
                        bitmap = ImageBitmap.imageResource(id = leadingIconId),  // material icon
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = "custom_text_field"
                    )
                    Canvas(
                        modifier = Modifier.height(24.dp)
                    ) {
                        // Allows you to draw a line between two points (p1 & p2) on the canvas.
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 2.0F
                        )
                    }
                }
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.White,
            trailingIconColor = Color.White,
//            disabledTextColor = NaviBlue
        ),
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        visualTransformation = visualTransformation
    )

    /*OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = textState.value,
        onValueChange = { valueChanged ->
            textState.value = valueChanged
        },
        placeholder = { Text(text = placeHolder) },
        leadingIcon = {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Image(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .size(18.dp),
                        bitmap = ImageBitmap.imageResource(id = leadingIconId),  // material icon
                        colorFilter = ColorFilter.tint(colorPrimary),
                        contentDescription = "custom_text_field"
                    )
                    Canvas(
                        modifier = Modifier.height(24.dp)
                    ) {
                        // Allows you to draw a line between two points (p1 & p2) on the canvas.
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 2.0F
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        activeColor = colorPrimary,
        inactiveColor = Color.Transparent,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        visualTransformation = visualTransformation
    )*/
    /* TextField(
         value = textState.value,
         onValueChange = { valueChanged ->
             textState.value = valueChanged
         },
         modifier = Modifier.fillMaxSize(),
         placeholder = Text(text = placeHolder, style = TextStyle(color = text_hint_color)),
         leadingIcon = Row(
             modifier = Modifier.fillMaxSize(),
             verticalAlignment = Alignment.CenterVertically,
             content = {
                 Image(
                     modifier = Modifier.padding(start = 10.dp, end = 10.dp).size(18.dp),
                     bitmap = imageResource(id = leadingIconId),  // material icon
                     colorFilter = ColorFilter.tint(colorPrimary),
                 )
                 Canvas(
                     modifier = Modifier.preferredHeight(24.dp)
                 ) {
                     // Allows you to draw a line between two points (p1 & p2) on the canvas.
                     drawLine(
                         color = Color.LightGray,
                         start = Offset(0f, 0f),
                         end = Offset(0f, size.height),
                         strokeWidth = 2.0F
                     )
                 }
             }
         ),
         activeColor = colorPrimary,
         inactiveColor = Color.Transparent,
         shape = RoundedCornerShape(10.dp),
         backgroundColor = Color.White,
         textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
         keyboardType = keyboardType,
         visualTransformation = visualTransformation
     )*/
}




