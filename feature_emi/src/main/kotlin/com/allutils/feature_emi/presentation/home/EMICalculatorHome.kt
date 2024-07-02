package com.allutils.feature_emi.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.allutils.app_style_guide.R
import com.allutils.app_style_guide.components.NumberField
import com.allutils.app_style_guide.styles.darkestBlack
import com.allutils.app_style_guide.styles.darkestBlue
import com.allutils.app_style_guide.styles.headingH4
import com.allutils.app_style_guide.styles.lightestGrey
import com.allutils.app_style_guide.styles.mediumBlue

@Composable
fun EMICalculatorHome() {
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
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (favorite, localCurrency, button) = createRefs()


            }
        }
    }
}
