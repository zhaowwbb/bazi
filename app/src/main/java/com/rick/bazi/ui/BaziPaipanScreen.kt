package com.rick.bazi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rick.bazi.BaziScreen
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.MALE
import com.rick.bazi.data.TianGan
import com.rick.bazi.util.getTianganStr
import com.rick.bazi.util.getDizhiStr
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.DateUtils


@Composable
fun BaziPaipanScreen(
    subtotal: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    navController: NavHostController,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.SpaceBetween
    ) {

        val calendarStr = stringResource(R.string.app_calendar)
        var yearLabel = stringResource(R.string.app_label_year)
        var monthLabel = stringResource(R.string.app_label_month)
        var dayLabel = stringResource(R.string.app_label_day)
        var hourLabel = stringResource(R.string.app_label_hour)

        var genderStr = MALE
        if (baziInfo.gender == MALE) {
            genderStr = stringResource(R.string.app_bazi_male)
//            println("genderStr: $genderStr")
        } else {
            genderStr = stringResource(R.string.app_bazi_female)
        }

        val birthDateStr =
            "$calendarStr ${baziInfo.birthDateYear}$yearLabel ${baziInfo.birthDateMonth}$monthLabel ${baziInfo.birthDateDay}$dayLabel ${baziInfo.birthHour}$hourLabel  $genderStr"



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    fontSize = 26.sp,
                    text = "${birthDateStr}"
                )

            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 10.dp)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.bazi_date),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_year),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_month),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_owner),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_hour),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge

                )
            }

            //show bazi tiangan details
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.tiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.yearTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.monthTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.dayTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.hourTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            //show bazi dizhi details
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.yearDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.monthDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.dayDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.hourDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 10.dp)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(BaziScreen.Summary.name)
                    }) {
                    Text(
                        text = stringResource(R.string.app_bazi_suanming),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    thickness = dimensionResource(R.dimen.thickness_divider),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )

                Divider(
                    thickness = dimensionResource(R.dimen.thickness_divider),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.yhzp_y),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.yhzp_h),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.yhzp_z),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.yhzp_p),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}


@Preview
@Composable
fun BaziPaipanPreview() {
    BaziTheme {
        BaziPaipanScreen(
            subtotal = "299.99",
            options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            navController = rememberNavController(),
            baziModel = viewModel(),
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}
