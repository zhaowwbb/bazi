package com.rick.bazi.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziDayunScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    baziModel: BaziViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(thickness = 2.dp)
        }


        //dayun
        var dayunLabel = stringResource(R.string.bazi_dayun)
        var dayunForward: Boolean =
            BaziUtil().isDayunDirectionForward(baziInfo.gender, baziInfo.yearDizhi)
        var days = BaziUtil().getDayunStartDays(
            baziInfo.birthDateYear,
            baziInfo.birthDateMonth,
            baziInfo.birthDateDay,
            dayunForward,
            baziInfo.birthHour,
            baziInfo.birthMinute
        )

        var afterBirthStr = stringResource(R.string.app_after_birth)
        var startDayunStr = stringResource(R.string.app_start_dayun)
        var yearLabelStr = stringResource(R.string.app_label_year)
        var monthLabelStr = stringResource(R.string.app_label_month)
        var monthOffset = 0
        var yearOffset = days/3
        var remains =  days % 3
        if(remains == 2){
            yearOffset = yearOffset + 1
            monthOffset = 8
        }else if(remains == 1){
            monthOffset = 4
        }

        var dayunLabelStr = "$dayunLabel  $afterBirthStr $yearOffset$yearLabelStr $monthOffset$monthLabelStr  $startDayunStr"

        baziModel.setDayunForward(dayunForward)
        baziModel.setDayunDays(days)

        Row(
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$dayunLabelStr",
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight(500),

                style = MaterialTheme.typography.headlineSmall
            )
        }

        var records = getDayunList(baziInfo)

        records.forEach { record ->

            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = record.dyLabel,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = record.dyAgeLabel,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
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
                    onClick = onCancelButtonClicked
                ) {
                    Text(
                        text = stringResource(R.string.back_button),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
            }
        }

        Column(
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .selectableGroup()
//                .padding(all = 10.dp)
            ) {
                HorizontalDivider(thickness = 2.dp)
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BaziDayunScreenPreview() {
    BaziTheme {
        BaziDayunScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

