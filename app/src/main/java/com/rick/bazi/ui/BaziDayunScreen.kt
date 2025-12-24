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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziMeasureUtil
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.DaYunUtil
import com.rick.bazi.util.GeJuUtil
import com.rick.bazi.util.WuXingUtil
import com.rick.bazi.util.YongShenUtil

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

        val birthDateStr = BaziUtil().getBirthDateLabel(baziInfo)

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
                    text = "${birthDateStr}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val SPACE = "    "
                val analysisResult = buildAnnotatedString {

                    //1.
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
//                        append(stringResource(R.string.app_bazi_analyze_label1) + "\n")
//                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append("" + stringResource(R.string.app_bazi_str) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + BaziUtil().createBaziStringOneLine(baziInfo) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_dayun_start_time) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + DaYunUtil().getDaYunStartTimeString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dayun_intro_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + stringResource(R.string.app_bazi_dayun_intro) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_yong_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getYongShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_xi_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getXiShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_ji_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getJiShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_tiaohou_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + YongShenUtil().getTiaohouString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_yongshen_tongguan_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + YongShenUtil().getTongGuanYongshenString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_final_yong_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
//                        append(SPACE + BaziMeasureUtil().getBaziYongshenSummary(baziInfo.baziData) + "\n")
                        append(YongShenUtil().getYongshenString(baziInfo.baziData) )
                    }


                    //5
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_analyze_label5)+ "\n")
                    }
//                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
//                        append(stringResource(R.string.app_bazi_dayun) + ":")
//                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DaYunUtil().getDaYunSummaryString(baziInfo.baziData) + "\n")
                    }
                }

                Text(
                    text = analysisResult,
                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
//                    style = MaterialTheme.typography.headlineSmall
                )
            }
//            Row(
//                modifier = Modifier.padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = stringResource(R.string.app_bazi_dayun_intro),
//                    textAlign = TextAlign.Left,
//                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
//                    style = MaterialTheme.typography.headlineSmall
//                )
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                HorizontalDivider(thickness = 2.dp)
//            }

        }


        //dayun
//        var dayunLabel = stringResource(R.string.app_bazi_10yeardayun)
//        var dayunForward: Boolean =
//            BaziUtil().isDayunDirectionForward(baziInfo.gender, baziInfo.yearDizhi)
//        var days = BaziUtil().getDayunStartDays(
//            baziInfo.birthDateYear,
//            baziInfo.birthDateMonth,
//            baziInfo.birthDateDay,
//            dayunForward,
//            baziInfo.birthHour,
//            baziInfo.birthMinute
//        )

//        var afterBirthStr = stringResource(R.string.app_after_birth)
//        var startDayunStr = stringResource(R.string.app_start_dayun)
//        var yearLabelStr = stringResource(R.string.app_label_year)
//        var monthLabelStr = stringResource(R.string.app_label_month)
//        var monthOffset = 0
//        var yearOffset = days/3
//        var remains =  days % 3
//        if(remains == 2){
//            yearOffset = yearOffset + 1
//            monthOffset = 8
//        }else if(remains == 1){
//            monthOffset = 4
//        }

//        var dayunLabelStr = "$afterBirthStr $yearOffset$yearLabelStr $monthOffset$monthLabelStr  $startDayunStr"

//        baziModel.setDayunForward(dayunForward)
//        baziModel.setDayunDays(days)

//        Row(
//            modifier = Modifier.padding(5.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = DaYunUtil().getDaYunStartTimeString(baziInfo.baziData),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.weight(1f),
//                fontWeight = FontWeight(500),
//                style = MaterialTheme.typography.headlineSmall
//            )
//        }

//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                HorizontalDivider(thickness = 2.dp)
//            }
//        }
//
//        Row(
//            modifier = Modifier.padding(5.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = DaYunUtil().getDaYunSummaryString(baziInfo.baziData),
//                textAlign = TextAlign.Left,
//                modifier = Modifier.weight(1f),
//                fontWeight = FontWeight(500),
//                style = MaterialTheme.typography.headlineSmall
//            )
//        }


//        var records = DaYunUtil().getDayunList(baziInfo)
//
//        records.forEach { record ->
//
//            Row(
//                modifier = Modifier.padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = record.dyLabel + "  " + record.dyAgeLabel,
//                    textAlign = TextAlign.Left,
//                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
////                    color = Color.Magenta,
//                    style = MaterialTheme.typography.headlineSmall
//                )
//            }
//
//        }

//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                HorizontalDivider(thickness = 2.dp)
//            }
//        }
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier.padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                OutlinedButton(
//                    modifier = Modifier.weight(1f),
//                    onClick = onCancelButtonClicked
//                ) {
//                    Text(
//                        text = stringResource(R.string.back_button),
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.weight(1f),
//                        fontWeight = FontWeight(500),
//                        fontSize = 24.sp,
//                    )
//                }
//            }
//        }

//        Column(
//            modifier = modifier
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .selectableGroup()
////                .padding(all = 10.dp)
//            ) {
//                HorizontalDivider(thickness = 2.dp)
//            }
//        }

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

