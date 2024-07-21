package com.allutils.feature_emi.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.animations.LottieAssetLoader
import com.allutils.app_style_guide.styles.lightBlue
import com.allutils.app_style_guide.styles.mediumBlue
import com.allutils.feature_emi.R
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.presentation.home.model.EmiIntents
import com.allutils.feature_emi.presentation.home.model.EmiViewState
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import java.text.NumberFormat
import java.util.Currency

@Composable
internal fun EMICalculatorHome(emiCalculatorViewModel: EMICalculatorViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val guideline1 = createGuidelineFromTop(0.6f)
        val guideline2 = createGuidelineFromTop(0.7f)
        val (image, loginForm) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guideline2)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(mediumBlue, lightBlue)
                    )
                )
                .padding(6.dp)
        ) {
            val uiState: EmiViewState by emiCalculatorViewModel.uiStateFlow.collectAsStateWithLifecycle()

            val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = "INR")

            uiState.let { emi ->
                when (emi) {
                    is EmiViewState.UserUpdateContent -> {
                        UserUpdateUI(emi, currencyVisualTransformation, emiCalculatorViewModel)
                    }

                    is EmiViewState.EmiDetailsContent -> {
                        CalculationsUI(emi, currencyVisualTransformation, emiCalculatorViewModel)
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
            val uiState: EmiViewState by emiCalculatorViewModel.uiStateFlow.collectAsStateWithLifecycle()

            val currencyVisualTransformation = rememberCurrencyVisualTransformation(currency = "INR")

            uiState.let { emi ->
                when (emi) {
                    is EmiViewState.UserUpdateContent -> {
                        if (emi.emiDetails.emi.equals(""))
                            HeaderUI_Two()
                    }

                    is EmiViewState.EmiDetailsContent -> {
                        HeaderUI(emi, currencyVisualTransformation, emiCalculatorViewModel)
                    }
                }
            }
        }
    }
}

@Composable
internal fun HeaderUI(
    data: EmiViewState.EmiDetailsContent,
    currencyVisualTransformation: VisualTransformation,
    emiCalculatorViewModel: EMICalculatorViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        val pieChartDataModel = remember { PieChartDataModel() }
        val numberFormatter = NumberFormat.getCurrencyInstance().apply {
            currency = Currency.getInstance("INR")
            maximumFractionDigits = 0
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            val (chart, interest, principle, total, emi) = createRefs()

            val guideline1 = createGuidelineFromTop(0.1f)
            val guideline2 = createGuidelineFromTop(0.9f)
            val guideline3 = createGuidelineFromStart(0.2f)
            val guideline4 = createGuidelineFromStart(0.7f)

            Column(
                modifier = Modifier
                    .constrainAs(interest) {
                        top.linkTo(guideline1)
                        start.linkTo(guideline3)
                        height = Dimension.fillToConstraints
                    }
            ) {
                androidx.compose.material.Text(
                    text = numberFormatter.format(data.emiDetails.interestComponent?.toDouble()),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                androidx.compose.material.Text(
                    text = "interest",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

            }
            Column(
                modifier = Modifier
                    .constrainAs(principle) {
                        top.linkTo(guideline1)
                        start.linkTo(guideline4)
                        height = Dimension.fillToConstraints
                    }
            ) {
                androidx.compose.material.Text(
                    text = numberFormatter.format(data.emiDetails.principalComponent?.toDouble()),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                androidx.compose.material.Text(
                    text = "principle",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

            }

            Column(
                modifier = Modifier
                    .constrainAs(total) {
                        top.linkTo(guideline2)
                        start.linkTo(guideline3)
                        height = Dimension.fillToConstraints
                    }
            ) {
                androidx.compose.material.Text(
                    text = numberFormatter.format(data.emiDetails.totalComponent?.toDouble()),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                androidx.compose.material.Text(
                    text = "total",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

            }

            Column(
                modifier = Modifier
                    .constrainAs(emi) {
                        top.linkTo(guideline2)
                        start.linkTo(guideline4)
                        height = Dimension.fillToConstraints
                    }
            ) {
                androidx.compose.material.Text(
                    text = numberFormatter.format(data.emiDetails.emi.toDouble()),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                androidx.compose.material.Text(
                    text = "EMI",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

            }

            Row(
                modifier = Modifier
                    .constrainAs(chart) {
                        top.linkTo(guideline1)
                        start.linkTo(interest.end)
                        bottom.linkTo(guideline2)
                        end.linkTo(principle.start)
                        height = Dimension.fillToConstraints
                    }.padding(4.dp)
            ) {

                val slices = listOf(
                    PieChartData.Slice(
                        data.emiDetails.interestComponent?.toFloat() ?: 0f,
                        randomColor()
                    ),
                    PieChartData.Slice(
                        data.emiDetails.principalComponent?.toFloat() ?: 0f,
                        randomColor()
                    )
                )

                val pieChartData by remember {
                    mutableStateOf(
                        PieChartData(slices = slices)
                    )
                }

                PieChart(
                    pieChartData = pieChartData,
                    sliceDrawer = SimpleSliceDrawer(
                        sliceThickness = pieChartDataModel.sliceThickness
                    )
                )
            }
        }
    }
}

private var colors = mutableListOf(
    Color(0XFFF44336),
    Color(0XFFE91E63),
    Color(0XFF9C27B0),
    Color(0XFF673AB7),
    Color(0XFF3F51B5),
    Color(0XFF03A9F4),
    Color(0XFF009688),
    Color(0XFFCDDC39),
    Color(0XFFFFC107),
    Color(0XFFFF5722),
    Color(0XFF795548),
    Color(0XFF9E9E9E),
    Color(0XFF607D8B)
)

private fun randomColor(): Color {
    val randomIndex = (Math.random() * colors.size).toInt()
    val color = colors[randomIndex]
    colors.removeAt(randomIndex)

    return color
}

@Composable
internal fun HeaderUI_Two() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        val (chart) = createRefs()

        val guideline1 = createGuidelineFromTop(0.2f)
        val guideline2 = createGuidelineFromTop(0.9f)
        val guideline3 = createGuidelineFromStart(0.2f)
        val guideline4 = createGuidelineFromStart(0.7f)

        Row(
            modifier = Modifier
                .constrainAs(chart) {
                    top.linkTo(guideline1)
                    start.linkTo(guideline3)
                    bottom.linkTo(guideline2)
                    end.linkTo(guideline4)
                    height = Dimension.fillToConstraints
                }.padding(4.dp)
        ) {
            LottieAssetLoader(R.raw.emi)
        }
    }
}

@Composable
internal fun UserUpdateUI(
    emi: EmiViewState.UserUpdateContent,
    currencyVisualTransformation: VisualTransformation,
    emiCalculatorViewModel: EMICalculatorViewModel
) {
    var principleErrorMessage by remember { mutableStateOf("") }
    var tenureErrorMessage by remember { mutableStateOf("") }
    var interestErrorMessage by remember { mutableStateOf("") }
    var emiErrorMessage by remember { mutableStateOf("") }

    var principleInWords by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "P",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "P"
                            )
                        )
                    })

                Column {
                    InputItem(
                        enabled = emi.emiDetails.selected != "P",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.principal,
                            selection = TextRange(emi.emiDetails.principal.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() }
                            if (trimmed.isNotEmpty())
                                principleErrorMessage = ""
                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    principle = trimmed,
                                    interest = emi.emiDetails.interest,
                                    tenure = emi.emiDetails.tenure,
                                    emi = emi.emiDetails.emi
                                )
                            )
                        },
                        visualTransformation = currencyVisualTransformation,
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_principle),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    Box {
                        Text(
                            text = principleErrorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = principleInWords,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "I",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "I"
                            )
                        )
                    })
                Column {
                    InputItem(
                        enabled = emi.emiDetails.selected != "I",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.interest,
                            selection = TextRange(emi.emiDetails.interest.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() && it != '.' }
                            if (trimmed.isNotEmpty()) {
                                interestErrorMessage = ""

                                if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isNotEmpty())
                                    principleInWords = numberToWords(emi.emiDetails.principal.toLongOrNull())
                            }

                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    interest = trimmed,
                                    principle = emi.emiDetails.principal,
                                    tenure = emi.emiDetails.tenure,
                                    emi = emi.emiDetails.emi
                                )
                            )
                        },
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_interest),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    Text(
                        text = interestErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "T",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "T"
                            )
                        )
                    })

                Column(modifier = Modifier.weight(1f)) {
                    InputItem(
                        enabled = emi.emiDetails.selected != "T",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.tenure,
                            selection = TextRange(emi.emiDetails.tenure.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { !it.isDigit() }
                            if (trimmed.isNotEmpty())
                                tenureErrorMessage = ""

                            if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isNotEmpty())
                                principleInWords = numberToWords(emi.emiDetails.principal.toLongOrNull())

                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    tenure = trimmed,
                                    principle = emi.emiDetails.principal,
                                    emi = emi.emiDetails.emi,
                                    interest = emi.emiDetails.interest
                                )
                            )
                        },
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_tenure),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = tenureErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "E",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "E"
                            )
                        )
                    })

                Column(modifier = Modifier.weight(1f)) {
                    InputItem(
                        enabled = emi.emiDetails.selected != "E",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.emi,
                            selection = TextRange(emi.emiDetails.emi.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() }
                            if (trimmed.isNotEmpty())
                                emiErrorMessage = ""

                            if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isNotEmpty())
                                principleInWords = numberToWords(emi.emiDetails.principal.toLongOrNull())

                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    emi = trimmed,
                                    principle = emi.emiDetails.principal,
                                    tenure = emi.emiDetails.tenure,
                                    interest = emi.emiDetails.interest
                                )
                            )
                        },
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_EMI),
                        visualTransformation = currencyVisualTransformation,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = emiErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isEmpty()) {
                            principleErrorMessage = "Enter Amount"
                            interestErrorMessage = ""
                            tenureErrorMessage = ""
                            emiErrorMessage = ""
                        } else if (emi.emiDetails.selected !== "I" && emi.emiDetails.interest.isEmpty()) {
                            interestErrorMessage = "Enter Interest Rate"
                            principleErrorMessage = ""
                            tenureErrorMessage = ""
                            emiErrorMessage = ""
                        } else if (emi.emiDetails.selected !== "T" && emi.emiDetails.tenure.isEmpty()) {
                            tenureErrorMessage = "Enter Tenure"
                            principleErrorMessage = ""
                            interestErrorMessage = ""
                            emiErrorMessage = ""
                        } else if (emi.emiDetails.selected !== "E" && emi.emiDetails.emi.isEmpty()) {
                            emiErrorMessage = "Enter EMI"
                            principleErrorMessage = ""
                            tenureErrorMessage = ""
                            interestErrorMessage = ""
                        } else {
                            emiCalculatorViewModel.processIntent(
                                EmiIntents.CalculateEMIDetails(
                                    EmiDetailsInput(
                                        selected = emi.emiDetails.selected,
                                        principal = emi.emiDetails.principal.toIntOrNull(),
                                        interest = emi.emiDetails.interest.toDoubleOrNull(),
                                        tenure = emi.emiDetails.tenure.toIntOrNull(),
                                        emi = emi.emiDetails.emi.toIntOrNull()
                                    )
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = com.allutils.feature_emi.R.string.button_calculate),
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
                Button(
                    onClick = {
                        principleErrorMessage = ""
                        tenureErrorMessage = ""
                        interestErrorMessage = ""
                        emiErrorMessage = ""
                        principleInWords = ""
                        emiCalculatorViewModel.processIntent(EmiIntents.Reset)
                    },
                    modifier = Modifier
                        .weight(.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = com.allutils.feature_emi.R.string.button_reset),
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
internal fun CalculationsUI(
    emi: EmiViewState.EmiDetailsContent,
    currencyVisualTransformation: VisualTransformation,
    emiCalculatorViewModel: EMICalculatorViewModel
) {
    val numberFormatter = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("INR")
        maximumFractionDigits = 0
    }
    var principleErrorMessage by remember { mutableStateOf("") }
    var tenureErrorMessage by remember { mutableStateOf("") }
    var interestErrorMessage by remember { mutableStateOf("") }
    var emiErrorMessage by remember { mutableStateOf("") }

    var principleInWords by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "P",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "P"
                            )
                        )
                    })
                Column {
                    InputItem(
                        enabled = emi.emiDetails.selected != "P",
                        textFieldValue = TextFieldValue(
                            numberFormatter.format(emi.emiDetails.principal.toDouble()),
                            selection = TextRange(emi.emiDetails.principal.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() }
                            if (trimmed.isNotEmpty())
                                principleErrorMessage = ""
                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    principle = trimmed,
                                    interest = emi.emiDetails.interest,
                                    tenure = emi.emiDetails.tenure,
                                    emi = emi.emiDetails.emi
                                )
                            )
                        },
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_principle),
                        keyboardType = KeyboardType.Number,
                        visualTransformation = currencyVisualTransformation,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    Box {
                        Text(
                            text = principleErrorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = principleInWords,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "I",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "I"
                            )
                        )
                    })
                Column {
                    InputItem(
                        enabled = emi.emiDetails.selected != "I",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.interest,
                            selection = TextRange(emi.emiDetails.interest.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() && it != '.' }
                            if (trimmed.isNotEmpty())
                                interestErrorMessage = ""

                            if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isNotEmpty())
                                principleInWords = numberToWords(emi.emiDetails.principal.toLongOrNull())

                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    interest = trimmed,
                                    principle = emi.emiDetails.principal,
                                    tenure = emi.emiDetails.tenure,
                                    emi = emi.emiDetails.emi
                                )
                            )
                        },
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_interest),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    Text(
                        text = interestErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "T",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "T"
                            )
                        )
                    })

                Column(modifier = Modifier.weight(1f)) {
                    InputItem(
                        enabled = emi.emiDetails.selected != "T",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.tenure,
                            selection = TextRange(emi.emiDetails.tenure.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() }
                            if (trimmed.isNotEmpty())
                                tenureErrorMessage = ""

                            if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isNotEmpty())
                                principleInWords = numberToWords(emi.emiDetails.principal.toLongOrNull())

                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    tenure = trimmed,
                                    principle = emi.emiDetails.principal,
                                    emi = emi.emiDetails.emi,
                                    interest = emi.emiDetails.interest
                                )
                            )
                        },
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_tenure),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = tenureErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                        disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    ),
                    selected = emi.emiDetails.selected == "E",
                    onClick = {
                        emiCalculatorViewModel.processIntent(
                            EmiIntents.UserUpdates(
                                selected = "E"
                            )
                        )
                    })

                Column(modifier = Modifier.weight(1f)) {
                    InputItem(
                        enabled = emi.emiDetails.selected != "E",
                        textFieldValue = TextFieldValue(
                            emi.emiDetails.emi,
                            selection = TextRange(emi.emiDetails.emi.length)
                        ),
                        onTextChanged = { newValue ->
                            val trimmed = newValue.text.trimStart('0').trim { it.isDigit().not() }
                            if (trimmed.isNotEmpty())
                                emiErrorMessage = ""

                            if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isNotEmpty())
                                principleInWords = numberToWords(emi.emiDetails.principal.toLongOrNull())

                            emiCalculatorViewModel.processIntent(
                                EmiIntents.UserUpdates(
                                    selected = emi.emiDetails.selected,
                                    emi = trimmed,
                                    principle = emi.emiDetails.principal,
                                    tenure = emi.emiDetails.tenure,
                                    interest = emi.emiDetails.interest
                                )
                            )
                        },
                        visualTransformation = currencyVisualTransformation,
                        label = stringResource(id = com.allutils.feature_emi.R.string.label_EMI),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = emiErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (emi.emiDetails.selected !== "P" && emi.emiDetails.principal.isEmpty()) {
                            principleErrorMessage = "Enter Amount"
                            interestErrorMessage = ""
                            tenureErrorMessage = ""
                            emiErrorMessage = ""
                        } else if (emi.emiDetails.selected !== "I" && emi.emiDetails.interest.isEmpty()) {
                            interestErrorMessage = "Enter Interest Rate"
                            principleErrorMessage = ""
                            tenureErrorMessage = ""
                            emiErrorMessage = ""
                        } else if (emi.emiDetails.selected !== "T" && emi.emiDetails.tenure.isEmpty()) {
                            tenureErrorMessage = "Enter Tenure"
                            principleErrorMessage = ""
                            interestErrorMessage = ""
                            emiErrorMessage = ""
                        } else if (emi.emiDetails.selected !== "E" && emi.emiDetails.emi.isEmpty()) {
                            emiErrorMessage = "Enter EMI"
                            principleErrorMessage = ""
                            tenureErrorMessage = ""
                            interestErrorMessage = ""
                        } else {
                            emiCalculatorViewModel.processIntent(
                                EmiIntents.CalculateEMIDetails(
                                    EmiDetailsInput(
                                        selected = emi.emiDetails.selected,
                                        principal = emi.emiDetails.principal.toIntOrNull(),
                                        interest = emi.emiDetails.interest.toDoubleOrNull(),
                                        tenure = emi.emiDetails.tenure.toIntOrNull(),
                                        emi = emi.emiDetails.emi.toIntOrNull()
                                    )
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = com.allutils.feature_emi.R.string.button_calculate),
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        principleErrorMessage = ""
                        tenureErrorMessage = ""
                        interestErrorMessage = ""
                        emiErrorMessage = ""
                        principleInWords = ""
                        emiCalculatorViewModel.processIntent(EmiIntents.Reset)
                    },
                    modifier = Modifier
                        .weight(.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = com.allutils.feature_emi.R.string.button_reset),
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

fun numberToWords(number: Long?): String {
    if (number == null) {
        return ""
    }

    if (number == 0L) {
        return "zero"
    }

    val unitsArray = arrayOf(
        "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    )

    val tensArray = arrayOf(
        "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    )

    val thousandsArray = arrayOf(
        "", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion"
    )

    var num = number
    var word = ""
    var thousandIndex = 0

    while (num > 0) {
        val remainder = (num % 1000).toInt()
        if (remainder != 0) {
            val remainderInWords = convertLessThanThousand(remainder, unitsArray, tensArray)
            word = "$remainderInWords ${thousandsArray[thousandIndex]} $word"
        }
        num /= 1000
        thousandIndex++
    }

    return word.trim()
}

private fun convertLessThanThousand(number: Int, unitsArray: Array<String>, tensArray: Array<String>): String {
    var num = number
    var word = ""

    if (num % 100 < 20) {
        word = unitsArray[num % 100]
        num /= 100
    } else {
        word = unitsArray[num % 10]
        num /= 10

        word = tensArray[num % 10] + if (word.isNotEmpty()) "-$word" else ""
        num /= 10
    }

    if (num > 0) {
        word = unitsArray[num] + " hundred" + if (word.isNotEmpty()) " and $word" else ""
    }

    return word
}




