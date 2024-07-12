package com.allutils.feature_emi.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
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
import com.allutils.app_style_guide.R
import com.allutils.app_style_guide.styles.lightBlue
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.app_style_guide.styles.mediumBlue
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.presentation.home.model.EmiIntents
import com.allutils.feature_emi.presentation.home.model.EmiViewState
import com.github.tehras.charts.piechart.PieChart
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
            HeaderUI()
        }
    }
}

@Composable
internal fun HeaderUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {

        val loginText = "EMI Calculator"
        val loginWord = "EMI"
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

        val randomPlaceHolder by rememberSaveable {
            mutableStateOf(R.drawable.ic_image_placeholder)
        }

        val pieChartDataModel = remember { PieChartDataModel() }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 4.dp),
            text = loginAnnotatedString,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            val (lastupdated, basecode, amount) = createRefs()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = 16.dp)
            ) {
                PieChart(
                    pieChartData = pieChartDataModel.pieChartData,
                    sliceDrawer = SimpleSliceDrawer(
                        sliceThickness = pieChartDataModel.sliceThickness
                    )
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 4.dp, start = 6.dp),
                text = "Enter any 3 fields to calculate the 4th one",
                textAlign = TextAlign.Start,
                color = lightestGrey,
                fontSize = 9.sp,
            )
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

                    Text(
                        text = principleErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
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
                    Text(
                        text = principleErrorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
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

