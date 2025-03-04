package com.rick.bazi.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.getDizhiStr
import com.rick.bazi.util.getTianganStr

data class TianKeDiChongRecord(
    val tkdcLabel: String,
    val ageLabel: String,
    var descriptionList: MutableList<String>
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziTianKeDiChongScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {

    var records = getTianKeDiChongList(baziInfo)
    var dayunLabel = stringResource(R.string.bazi_dayun)
    var dayunForward: Boolean =
        BaziUtil().isDayunDirectionForward(baziInfo.gender, baziInfo.yearDizhi)

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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    fontSize = 26.sp,
                    text = "${birthDateStr}"
                )
            }
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
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = BaziUtil().getTitleLable(baziInfo),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.bazi_tkdc_introduction),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
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
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.bazi_year),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.bazi_month),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.bazi_day),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Green,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.bazi_hour),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineMedium

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
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.yearTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.monthTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.dayTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getTianganStr(baziInfo, baziInfo.hourTiangan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Cyan,
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
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.yearDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.monthDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.dayDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Green,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.hourDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            records.forEach { record ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(thickness = 2.dp)
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Left,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        text = record.tkdcLabel
                    )
                    Text(
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        text = record.ageLabel
                    )
                }
                record.descriptionList.forEach { desc ->
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight(500),
                            style = MaterialTheme.typography.headlineSmall,
                            text = desc
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(thickness = 2.dp)
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
    }
}

@Preview
@Composable
fun BaziTianKeDiChongScreenPreview() {
    BaziTheme {
        BaziTianKeDiChongScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun getTianKeDiChongList(baziInfo: BaziInfo): MutableList<TianKeDiChongRecord> {
    var records: MutableList<TianKeDiChongRecord> = arrayListOf()
    var days = baziInfo.dayunDays
    var dayunStartYear = BaziUtil().getDayunStartYear(baziInfo.birthDateYear, days)
    var yearOffet = BaziUtil().getDayunStartOffset(days)
    var isDayunForward = baziInfo.dayunForward
    val startIndex = 1
    val endIndex = 10

    var builder = StringBuilder()
    var base = baziInfo.monthBase
    var monthBase = baziInfo.monthBase

    var tg: TianGan = TianGan.TIANGAN_JIA
    var dz: DiZhi = DiZhi.DIZHI_ZI
//    var dyLabel = stringResource(R.string.bazi_tkdc_dayun)
//    var lnLabel = stringResource(R.string.bazi_tkdc_liunian)

    println("year base=" + baziInfo.yearBase + ",month base=" + baziInfo.monthBase)

    for (i in startIndex..endIndex) {
        if (isDayunForward) {
            base = monthBase + i
        } else {
            base = monthBase - i
        }

        tg = BaziUtil().getTianganFromBase(base)
        dz = BaziUtil().getDizhiFromBase(base)

        var dayunLabelStr = getDayunLabel(dayunStartYear, i, tg, dz)
        var ageStr = getDayunAgeLabel(i, yearOffet)

        var descriptions: MutableList<String> = arrayListOf()

        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.yearTiangan,
                baziInfo.yearDizhi
            )
        ) {
            descriptions.add(getDayunDescription(R.string.bazi_tkdc_year))
        }
        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.monthTiangan,
                baziInfo.monthDizhi
            )
        ) {
            descriptions.add(getDayunDescription(R.string.bazi_tkdc_month))
        }

        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.dayTiangan,
                baziInfo.dayDizhi
            )
        ) {
            descriptions.add(getDayunDescription(R.string.bazi_tkdc_day))
        }
        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.hourTiangan,
                baziInfo.hourDizhi
            )
        ) {
            descriptions.add(getDayunDescription(R.string.bazi_tkdc_hour))
        }

        if (descriptions.size > 0) {
            var r: TianKeDiChongRecord =
                TianKeDiChongRecord(
                    dayunLabelStr,
                    ageStr,
                    descriptions
                )
            records.add(r)
        }

        var yearBase = baziInfo.yearBase
        var base = baziInfo.yearBase
        val startYearIndex = yearBase + (i - 1) * 10
        val endYearIndex = yearBase + (i - 1) * 10 + 9

        var lnTg = TianGan.TIANGAN_JIA
        var lnDz = DiZhi.DIZHI_ZI
        for (j in 0..9) {
            base = yearBase + (i - 1) * 10 + j + yearOffet + 1

            var liunianYear = baziInfo.birthDateYear + (i - 1) * 10 + j + yearOffet + 1

            lnTg = BaziUtil().getTianganFromBase(base)
            lnDz = BaziUtil().getDizhiFromBase(base)

//            println("liunianYear=" + liunianYear + ",base=" + base + " " + BaziUtil().getTianGanLabel(lnTg) + BaziUtil().getDizhiLabel(lnDz))
            descriptions = arrayListOf()

            var liunianLabelStr = getLiunianLabel(dayunStartYear, liunianYear, lnTg, lnDz)
            println("liunianLabelStr=" + liunianLabelStr)

            builder = StringBuilder()
            builder.append((i - 1) * 10 + yearOffet + j)
            builder.append(stringResource(R.string.age_unit))
            var liunianAgeStr = builder.toString()

            //Dayun liunian conflict
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    tg,
                    dz
                )
            ) {
                descriptions.add(getDayunLiunianDescription(tg, dz, lnTg, lnDz))
//                descriptions.add(lnLabel + stringResource(R.string.bazi_tkdc_dayun_liunian))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.yearTiangan,
                    baziInfo.yearDizhi
                )
            ) {
                descriptions.add(getLiunianDescription(R.string.bazi_tkdc_year))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.monthTiangan,
                    baziInfo.monthDizhi
                )
            ) {
                descriptions.add(getLiunianDescription(R.string.bazi_tkdc_month))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.dayTiangan,
                    baziInfo.dayDizhi
                )
            ) {
                descriptions.add(getLiunianDescription(R.string.bazi_tkdc_day))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.hourTiangan,
                    baziInfo.hourDizhi
                )
            ) {
                descriptions.add(getLiunianDescription(R.string.bazi_tkdc_hour))
            }

            if (descriptions.size > 0) {
                var r: TianKeDiChongRecord =
                    TianKeDiChongRecord(
                        liunianLabelStr,
                        liunianAgeStr,
                        descriptions
                    )
                records.add(r)
            }
        }
    }
    return records
}

@Composable
fun getDayunLabel(dayunStartYear: Int, i: Int, tg: TianGan, dz: DiZhi): String {
    var builder = StringBuilder()
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
//    builder.append(stringResource(R.string.app_label_year))
    return builder.toString()
}

@Composable
fun getDayunAgeLabel(i: Int, yearOffet: Int): String {
    var builder = StringBuilder()
    builder.append((i - 1) * 10 + yearOffet)
    builder.append(stringResource(R.string.app_to))
    builder.append((i - 1) * 10 + yearOffet + 9)
    builder.append(stringResource(R.string.age_unit))
    return builder.toString()
}

@Composable
fun getLiunianLabel(dayunStartYear: Int, liunianYear: Int, lnTg: TianGan, lnDz: DiZhi): String {
    var builder = StringBuilder()
    builder.append(BaziUtil().getTianGanLabel(lnTg))
    builder.append(BaziUtil().getDizhiLabel(lnDz))
    builder.append("(")
    builder.append(stringResource(R.string.bazi_tkdc_liunian))
    builder.append(")")
    builder.append(" ")
    builder.append(liunianYear)
//    builder.append(stringResource(R.string.app_label_year))
    return builder.toString()
}

@Composable
fun getDayunDescription(msgId : Int ) :String{
    var builder = StringBuilder()
    builder.append(stringResource(R.string.bazi_tkdc_dayun))
    builder.append(stringResource(msgId))
    return builder.toString()
}

@Composable
fun getLiunianDescription(msgId : Int ) :String{
    var builder = StringBuilder()
    builder.append(stringResource(R.string.bazi_tkdc_liunian))
    builder.append(stringResource(msgId))
    return builder.toString()
}

@Composable
fun getDayunLiunianDescription(dyTg : TianGan, dyDz : DiZhi, lnTg : TianGan, lnDz : DiZhi) :String{
    var builder = StringBuilder()
    builder.append(BaziUtil().getTianGanLabel(dyTg))
    builder.append(BaziUtil().getDizhiLabel(dyDz))
    builder.append("(")
    builder.append(stringResource(R.string.bazi_tkdc_dayun))
    builder.append(")")
    builder.append(" ")
    builder.append(BaziUtil().getTianGanLabel(lnTg))
    builder.append(BaziUtil().getDizhiLabel(lnDz))
    builder.append("(")
    builder.append(stringResource(R.string.bazi_tkdc_liunian))
    builder.append(")")
    builder.append(" ")
    builder.append(stringResource(R.string.bazi_tkdc))
    return builder.toString()
}
