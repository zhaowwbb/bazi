package com.rick.bazi.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rick.bazi.BaziScreen
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
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

//        val tianganStrMap : Map<TianGan, Int> = baziInfo.tianganStrMap
        Column(
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                Text(
                    text = stringResource(R.string.bazi_date),
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_year),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_month),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_day),
                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.bazi_hour),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge

                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                Text(
                    text = stringResource(R.string.tiangan),
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                    Text(
                        text = getTianganStr(baziInfo, baziInfo.yearTiangan),

                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.monthTiangan),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.dayTiangan),
                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.hourTiangan),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                Text(
                    text = stringResource(R.string.dizhi),
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.yearDizhi),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.monthDizhi),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.dayDizhi),
                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.hourDizhi),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge

                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                Button(onClick = {
                    navController.navigate(BaziScreen.Summary.name)
                }) {
                    Text(text = stringResource(R.string.app_bazi_suanming))
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                Divider(
                    thickness = dimensionResource(R.dimen.thickness_divider),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )
            }
        }
//        Column(
//            modifier = modifier
//        ) {
//            Text(text = "Gender: ")
//            Text(text = baziInfo.gender)
//            Divider(
//                thickness = dimensionResource(R.dimen.thickness_divider),
//                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
//            )
//            Text(text = "Date year: ")
//            Text(text = baziInfo.birthDateYear.toString())
//            Divider(
//                thickness = dimensionResource(R.dimen.thickness_divider),
//                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
//            )
//            Text(text = "Date Month: ")
//            Text(text = baziInfo.birthDateMonth.toString())
//            Divider(
//                thickness = dimensionResource(R.dimen.thickness_divider),
//                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
//            )
//
//            Text(text = "Date Day: ")
//            Text(text = baziInfo.birthDateDay.toString())
//            Divider(
//                thickness = dimensionResource(R.dimen.thickness_divider),
//                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
//            )
//
//            Text(text = "Hour: ")
//            Text(text = baziInfo.birthHour.toString())
//            Divider(
//                thickness = dimensionResource(R.dimen.thickness_divider),
//                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
//            )
//            Text(text = "Minute: ")
//            Text(text = baziInfo.birthMinute.toString())
//            Divider(
//                thickness = dimensionResource(R.dimen.thickness_divider),
//                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
//            )
//        }
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
