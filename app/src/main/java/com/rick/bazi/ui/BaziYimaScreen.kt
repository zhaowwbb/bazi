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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.getDizhiStr

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziYimaScreen(
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }


            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_bazi_yima_intro),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }

            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getHorseString(baziInfo),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }

            var records = getDayunHorseList(baziInfo)

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
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }

            var liunianHorseRecords = getLiunianHorseList(baziInfo)

            liunianHorseRecords.forEach { record ->

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
                        style = MaterialTheme.typography.headlineSmall
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
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
fun BaziYimaScreenPreview() {
    BaziTheme {
        BaziYimaScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

fun getYima(dz : DiZhi) : DiZhi {
    if (dz == DiZhi.DIZHI_SHEN  || dz == DiZhi.DIZHI_ZI || dz == DiZhi.DIZHI_CHEN) {
        return DiZhi.DIZHI_YIN
    }

    if (dz == DiZhi.DIZHI_YIN  || dz == DiZhi.DIZHI_WU || dz == DiZhi.DIZHI_XU) {
        return DiZhi.DIZHI_SHEN
    }

    if (dz == DiZhi.DIZHI_SI  || dz == DiZhi.DIZHI_YOU || dz == DiZhi.DIZHI_CHOU) {
        return DiZhi.DIZHI_HAI
    }

    if (dz == DiZhi.DIZHI_HAI  || dz == DiZhi.DIZHI_MOU || dz == DiZhi.DIZHI_WEI) {
        return DiZhi.DIZHI_SI
    }

    return DiZhi.DIZHI_YIN
}

@Composable
fun getHorseString(baziInfo: BaziInfo) : String {
    val builder = StringBuilder()
    var yearDz = baziInfo.yearDizhi
    var dayDz = baziInfo.dayDizhi

    //check year horse
    var yearHorse = getYima(yearDz)
    println("yearHorse = $yearHorse  yearDz=$yearDz ")
    if(baziInfo.monthDizhi == yearHorse){
        builder.append(stringResource(R.string.bazi_month))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, yearHorse))
        builder.append(")")
        builder.append("\n")
    }

    if(baziInfo.dayDizhi == yearHorse){
        builder.append(stringResource(R.string.bazi_day))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, yearHorse))
        builder.append(")")
        builder.append("\n")
    }

    if(baziInfo.hourDizhi == yearHorse){
        builder.append(stringResource(R.string.bazi_hour))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, yearHorse))
        builder.append(")")
        builder.append("\n")
    }

    //check day horse
    var dayHorse = getYima(dayDz)
    println("dayHorse = $dayHorse  dayDz=$dayDz ")

    if(baziInfo.yearDizhi == dayHorse){
        builder.append(stringResource(R.string.bazi_year))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, dayHorse))
        builder.append(")")
        builder.append("\n")
    }

    if(baziInfo.monthDizhi == dayHorse){
        builder.append(stringResource(R.string.bazi_month))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, dayHorse))
        builder.append(")")
        builder.append("\n")
    }

    if(baziInfo.hourDizhi == dayHorse){
        builder.append(stringResource(R.string.bazi_hour))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, dayHorse))
        builder.append(")")
        builder.append("\n")
    }

    return builder.toString()
}

@Composable
fun getDayunHorseString(baziInfo: BaziInfo, dz : DiZhi) : String {
    val builder = StringBuilder()
    var yearDz = baziInfo.yearDizhi
    var dayDz = baziInfo.dayDizhi

    //check year horse
    var yearHorse = getYima(yearDz)
    //check day horse
    var dayHorse = getYima(dayDz)

    if(dz == yearHorse){
        builder.append(stringResource(R.string.bazi_year))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, yearHorse))
        builder.append(")")
    }

    if(dz == dayHorse){
        builder.append(stringResource(R.string.bazi_day))
        builder.append(stringResource(R.string.app_bazi_yima))
        builder.append("(")
        builder.append(getDizhiStr(baziInfo, dayHorse))
        builder.append(")")
    }

//    builder.append("\n")
    return builder.toString()
}

@Composable
fun getDayunHorseList(baziInfo: BaziInfo) : MutableList<DayunRecord>{
    var records: MutableList<DayunRecord> = arrayListOf()
    var startIndex = 1
    var endIndex = 12
    var base = baziInfo.monthBase
    var tg = TianGan.TIANGAN_JIA
    var dz = DiZhi.DIZHI_ZI
    var builder = StringBuilder()
    var days = baziInfo.dayunDays
    var dayunStartYear = BaziUtil().getDayunStartYear(baziInfo.birthDateYear, days)
    var isDYForward = baziInfo.dayunForward
    var yearOffet = BaziUtil().getDayunStartOffset(days)
    var labelstr = ""
    var ageStr = ""
    for (i in startIndex..endIndex) {
        if(isDYForward){
            base = baziInfo.monthBase + i
        }else{
            base = baziInfo.monthBase - i
        }
        tg = BaziUtil().getTianganFromBase(base)
        dz = BaziUtil().getDizhiFromBase(base)
        builder = StringBuilder()
        builder.append(BaziUtil().getTianGanLabel(tg))
        builder.append(BaziUtil().getDizhiLabel(dz))
        builder.append("(")
        builder.append(stringResource(R.string.bazi_tkdc_dayun))
        builder.append(")")
        builder.append(" ")

        builder.append(dayunStartYear + (i - 1) * 10)
        builder.append(stringResource(R.string.app_to))
        builder.append(dayunStartYear + (i - 1) * 10 + 9)

        builder.append(" ")
        builder.append(getDayunHorseString(baziInfo, dz))

        labelstr = builder.toString()

        builder = StringBuilder()
        builder.append((i - 1) * 10 + yearOffet + 1)
        builder.append(stringResource(R.string.app_to))
        builder.append((i - 1) * 10 + yearOffet + 10)
        builder.append(stringResource(R.string.age_unit))

        ageStr = builder.toString()
        val r = DayunRecord(labelstr, ageStr)
        records.add(r)
    }
    return records
}

@Composable
fun getLiunianHorseList(baziInfo: BaziInfo) : MutableList<DayunRecord>{
    var records: MutableList<DayunRecord> = arrayListOf()
    var startIndex = 0
    var endIndex = 80
    var year = baziInfo.birthDateYear
    var base = baziInfo.yearBase
    var tg = baziInfo.yearTiangan
    var dz = baziInfo.yearDizhi
    var builder = StringBuilder()

//    var days = baziInfo.dayunDays
//    var dayunStartYear = BaziUtil().getDayunStartYear(baziInfo.birthDateYear, days)
//    var isDYForward = baziInfo.dayunForward
//    var yearOffet = BaziUtil().getDayunStartOffset(days)
    var labelstr = ""
    var ageStr = ""
    for (i in startIndex..endIndex) {

        tg = BaziUtil().getTianganFromBase(base + i)
        dz = BaziUtil().getDizhiFromBase(base + i)
        builder = StringBuilder()
        builder.append(BaziUtil().getTianGanLabel(tg))
        builder.append(BaziUtil().getDizhiLabel(dz))
        builder.append("(")
        builder.append(stringResource(R.string.bazi_tkdc_liunian))
        builder.append(")")
        builder.append(" ")

        builder.append(year + i)
//        builder.append(stringResource(R.string.app_to))
//        builder.append(dayunStartYear + (i - 1) * 10 + 9)

        builder.append(" ")

        builder.append(getDayunHorseString(baziInfo, dz))

        labelstr = builder.toString()

        builder = StringBuilder()
        builder.append(i)
        builder.append(stringResource(R.string.age_unit))

        ageStr = builder.toString()
        val r = DayunRecord(labelstr, ageStr)
        records.add(r)
    }
    return records
}