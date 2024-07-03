package com.allutils.feature_emi.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.allutils.app_style_guide.R
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.app_style_guide.styles.mediumBlue
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.presentation.home.model.EmiIntents
import com.allutils.feature_emi.presentation.home.model.EmiViewState
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer

@Composable
internal fun EMICalculatorHome(emiCalculatorViewModel: EMICalculatorViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val guideline1 = createGuidelineFromTop(0.45f)
        val guideline2 = createGuidelineFromTop(0.55f)
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
                        colors = listOf(darkestBlue, mediumBlue)
                    )
                )
                .padding(16.dp)
        ) {
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
            var nameText by remember { mutableStateOf(TextFieldValue()) }
            var cardNumber by remember { mutableStateOf(TextFieldValue()) }
            var expiryNumber by remember { mutableStateOf(TextFieldValue()) }
            var cvcNumber by remember { mutableStateOf(TextFieldValue()) }

            uiState.let {emi->
                when (emi) {
                    is EmiViewState.EmiDetailsContent -> {

                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            item {
                                InputItem(
                                    textFieldValue = TextFieldValue(
                                        emi.emiDetails.principal.toString(),
                                        selection = TextRange(emi.emiDetails.principal.toString().length)
                                    ),
                                    label = stringResource(id = com.allutils.feature_emi.R.string.label_principle),
                                    keyboardType = KeyboardType.Number,
                                    onTextChanged = {
                                        nameText = it

                                        emiCalculatorViewModel.processIntent(
                                            EmiIntents.UserUpdates(nameText.text)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            }

                            item {
                                InputItem(
                                    textFieldValue = TextFieldValue(
                                        emi.emiDetails.interest.toString(),
                                        selection = TextRange(emi.emiDetails.interest.toString().length)
                                    ),
                                    label = stringResource(id = com.allutils.feature_emi.R.string.label_interest),
                                    keyboardType = KeyboardType.Number,
                                    onTextChanged = { cardNumber = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    InputItem(
                                        textFieldValue = expiryNumber,
                                        label = stringResource(id = com.allutils.feature_emi.R.string.label_tenure),
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = { expiryNumber = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 8.dp)
                                    )
                                    InputItem(
                                        textFieldValue = cvcNumber,
                                        label = stringResource(id = com.allutils.feature_emi.R.string.label_EMI),
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = { cvcNumber = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp)
                                    )
                                }
                            }

                            item {
                                Button(
                                    onClick = {
                                        emiCalculatorViewModel.processIntent(
                                            EmiIntents.CalculateEMIDetails(
                                                EmiDetailsInput(
                                                    principal = nameText.text.toDouble(),
                                                    interest = cardNumber.text.toDouble(),
                                                    tenure = expiryNumber.text.toDouble(),
                                                    emi = cvcNumber.text.toDouble()
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = com.allutils.feature_emi.R.string.button_calculate),
                                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    EmiViewState.EmiDetailsInitialContent -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            item {
                                InputItem(
                                    textFieldValue = nameText,
                                    label = stringResource(id = com.allutils.feature_emi.R.string.label_principle),
                                    keyboardType = KeyboardType.Number,
                                    onTextChanged = { nameText = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            }

                            item {
                                InputItem(
                                    textFieldValue = cardNumber,
                                    label = stringResource(id = com.allutils.feature_emi.R.string.label_interest),
                                    keyboardType = KeyboardType.Number,
                                    onTextChanged = { cardNumber = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    InputItem(
                                        textFieldValue = expiryNumber,
                                        label = stringResource(id = com.allutils.feature_emi.R.string.label_tenure),
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = { expiryNumber = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 8.dp)
                                    )
                                    InputItem(
                                        textFieldValue = cvcNumber,
                                        label = stringResource(id = com.allutils.feature_emi.R.string.label_EMI),
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = { cvcNumber = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp)
                                    )
                                }
                            }

                            item {
                                Button(
                                    onClick = {
                                        emiCalculatorViewModel.processIntent(
                                            EmiIntents.CalculateEMIDetails(
                                                EmiDetailsInput(
                                                    principal = nameText.text.toDouble(),
                                                    interest = cardNumber.text.toDouble(),
                                                    tenure = expiryNumber.text.toDouble(),
                                                    emi = cvcNumber.text.toDouble()
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = com.allutils.feature_emi.R.string.button_calculate),
                                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    is EmiViewState.UserUpdateContent -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            item {
                                InputItem(
                                    textFieldValue = TextFieldValue(
                                        emi.emiDetails.principal.toString(),
                                        selection = TextRange(emi.emiDetails.principal.toString().length)
                                    ),
                                    label = stringResource(id = com.allutils.feature_emi.R.string.label_principle),
                                    keyboardType = KeyboardType.Number,
                                    onTextChanged = {
                                        nameText = it

                                        emiCalculatorViewModel.processIntent(
                                            EmiIntents.UserUpdates(nameText.text)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            }

                            item {
                                InputItem(
                                    textFieldValue = TextFieldValue(
                                        emi.emiDetails.interest.toString(),
                                        selection = TextRange(emi.emiDetails.interest.toString().length)
                                    ),
                                    label = stringResource(id = com.allutils.feature_emi.R.string.label_interest),
                                    keyboardType = KeyboardType.Number,
                                    onTextChanged = { cardNumber = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    InputItem(
                                        textFieldValue = expiryNumber,
                                        label = stringResource(id = com.allutils.feature_emi.R.string.label_tenure),
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = { expiryNumber = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 8.dp)
                                    )
                                    InputItem(
                                        textFieldValue = cvcNumber,
                                        label = stringResource(id = com.allutils.feature_emi.R.string.label_EMI),
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = { cvcNumber = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp)
                                    )
                                }
                            }

                            item {
                                Button(
                                    onClick = {
                                        emiCalculatorViewModel.processIntent(
                                            EmiIntents.CalculateEMIDetails(
                                                EmiDetailsInput(
                                                    principal = nameText.text.toDouble(),
                                                    interest = cardNumber.text.toDouble(),
                                                    tenure = expiryNumber.text.toDouble(),
                                                    emi = cvcNumber.text.toDouble()
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = com.allutils.feature_emi.R.string.button_calculate),
                                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
