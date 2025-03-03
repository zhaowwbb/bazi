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

data class TianKeDiChongRecord(
    val dayunLabel: String,
    val liunianLabel: String,
    val year: String,
    val age: String,
    var descriptionList: MutableList<String>,
    val description: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziTianKeDiChongScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {


//    var records : List<TianKeDiChongRecord> = listOf()
    var records = getTianKeDiChongList(baziInfo)
    var dayunLabel = stringResource(R.string.bazi_dayun)
    var dayunForward: Boolean =
        BaziUtil().isDayunDirectionForward(baziInfo.gender, baziInfo.yearDizhi)


//    var days = baziInfo.dayunDays
//
//    var dayunStartYear = BaziUtil().getDayunStartYear(baziInfo.birthDateYear, days)
//    var yearOffet = BaziUtil().getDayunStartOffset(days)
//    var isDayunForward = baziInfo.dayunForward
//
//
//    val yearDz = baziInfo.yearDizhi
//    val yearTg = baziInfo.yearTiangan
//    val startIndex = 1
//    val endIndex = 10
//
////    var base = baziInfo.monthBase
//    val builder = StringBuilder()
//    var base = baziInfo.monthBase
//    var monthBase = baziInfo.monthBase
//    var dayTG = 0
//    var dayDZ = 0
//    var tg: TianGan = TianGan.TIANGAN_JIA
//    var dz: DiZhi = DiZhi.DIZHI_ZI
//    var yearUnit = stringResource(R.string.age_unit)
//
//
//    if (isDayunForward) {
//        for (i in startIndex..endIndex) {
//            base = baziInfo.monthBase + i
//            base = base % 60
//
//            dayTG = base % 10
//            dayDZ = base % 12
//            tg = BaziUtil().tgLookupMap.get(dayTG)!!
//            dz = BaziUtil().dzLookupMap.get(dayDZ)!!
//
//            var dayunLabelStr = BaziUtil().getTianGanLabel(tg) + BaziUtil().getDizhiLabel(dz)
//            var dayunStartYearStr = dayunStartYear + (i - 1) * 10
//            var ageStr = (i - 1) * 10 + yearOffet
//
//
//            var r : TianKeDiChongRecord = TianKeDiChongRecord(dayunLabelStr, "", dayunStartYearStr.toString(), ageStr.toString())
//
////            records.toMutableList().add(r)
//
//            records.add(r)
//            println("[Rick] add " + r)
//        }
//    }
//    //backward
//    if (!isDayunForward) {
//
//        for (i in startIndex..endIndex) {
//            base = monthBase - i
//            if (base < 0) {
//                base = base + 60
//            }
//            base = base % 60
//
//            dayTG = base % 10
//            dayDZ = base % 12
//            tg = BaziUtil().tgLookupMap.get(dayTG)!!
//            dz = BaziUtil().dzLookupMap.get(dayDZ)!!
//
//            var dayunLabelStr = BaziUtil().getTianGanLabel(tg) + BaziUtil().getDizhiLabel(dz)
//            var dayunStartYearStr = dayunStartYear + (i - 1) * 10
//            var ageStr = (i - 1) * 10 + yearOffet
//
//            var r : TianKeDiChongRecord = TianKeDiChongRecord(dayunLabelStr, "", dayunStartYearStr.toString(), ageStr.toString())
//
////            records.toMutableList().add(r)
//
//            records.add(r)
//            println("[Rick] add " + r)
//        }
//    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val birthDateStr = BaziUtil().getBirthDateLabel(baziInfo)

//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
//                    fontSize = 26.sp,
//                    text = "${birthDateStr}"
//                )
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
//            ) {
//                HorizontalDivider(thickness = 2.dp)
//            }
//        }

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
                        text = record.dayunLabel
                    )
//                    Text(
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.weight(1f),
//                        fontWeight = FontWeight(500),
//                        style = MaterialTheme.typography.headlineSmall,
//                        text = record.liunianLabel
//                    )

//                    Text(
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.weight(1f),
//                        fontWeight = FontWeight(500),
//                        style = MaterialTheme.typography.headlineSmall,
//                        text = record.year
//                    )
                    Text(
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        text = record.age
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


    val yearDz = baziInfo.yearDizhi
    val yearTg = baziInfo.yearTiangan
    val startIndex = 1
    val endIndex = 10

//    var base = baziInfo.monthBase
    var builder = StringBuilder()
    var base = baziInfo.monthBase
    var monthBase = baziInfo.monthBase
    var dayunTG = 0
    var dayunDZ = 0
    var tg: TianGan = TianGan.TIANGAN_JIA
    var dz: DiZhi = DiZhi.DIZHI_ZI
    var yearUnit = stringResource(R.string.age_unit)
    var dyLabel = stringResource(R.string.bazi_tkdc_dayun)
    var lnLabel = stringResource(R.string.bazi_tkdc_liunian)


    for (i in startIndex..endIndex) {
        if (isDayunForward) {
            base = monthBase + i
        } else {
            base = monthBase - i
        }
        if (base < 0) {
            base = base + 60
        }
        base = base % 60

        dayunTG = base % 10
        dayunDZ = base % 12
        tg = BaziUtil().tgLookupMap.get(dayunTG)!!
        dz = BaziUtil().dzLookupMap.get(dayunDZ)!!

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
        builder.append(stringResource(R.string.app_label_year))
        var dayunLabelStr = builder.toString()

        var dayunStartYearStr = ""
//            //(dayunStartYear + (i - 1) * 10) + stringResource(R.string.app_label_year) + stringResource(R.string.app_label_year)

        builder = StringBuilder()
        builder.append((i - 1) * 10 + yearOffet)
        builder.append(stringResource(R.string.app_to))
        builder.append((i - 1) * 10 + yearOffet + 9)
        builder.append(stringResource(R.string.age_unit))
        var ageStr = builder.toString()

        var descriptions: MutableList<String> = arrayListOf()

        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.yearTiangan,
                baziInfo.yearDizhi
            ) || BaziUtil().isTianKeDiChong(baziInfo.yearTiangan, baziInfo.yearDizhi, tg, dz)
        ) {
            descriptions.add(dyLabel + stringResource(R.string.bazi_tkdc_year))
        }
        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.monthTiangan,
                baziInfo.monthDizhi
            ) || BaziUtil().isTianKeDiChong(baziInfo.monthTiangan, baziInfo.monthDizhi, tg, dz)
        ) {
            descriptions.add(dyLabel + stringResource(R.string.bazi_tkdc_month))
        }

        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.dayTiangan,
                baziInfo.dayDizhi
            ) || BaziUtil().isTianKeDiChong(baziInfo.dayTiangan, baziInfo.dayDizhi, tg, dz)
        ) {
            descriptions.add(dyLabel + stringResource(R.string.bazi_tkdc_day))
        }
        if (BaziUtil().isTianKeDiChong(
                tg,
                dz,
                baziInfo.hourTiangan,
                baziInfo.hourDizhi
            ) || BaziUtil().isTianKeDiChong(baziInfo.hourTiangan, baziInfo.hourDizhi, tg, dz)
        ) {
            descriptions.add(dyLabel + stringResource(R.string.bazi_tkdc_hour))
        }

        if (descriptions.size > 0) {
            var r: TianKeDiChongRecord =
                TianKeDiChongRecord(
                    dayunLabelStr,
                    "",
                    dayunStartYearStr.toString(),
                    ageStr,
                    descriptions,
                    ""
                )
            records.add(r)
        }

        var yearBase = baziInfo.yearBase
        var base = baziInfo.yearBase
        val startYearIndex = yearBase + (i - 1) * 10
        val endYearIndex = yearBase + (i - 1) * 10 + 9
        var lnTG = 0
        var lnDZ = 0
        var lnTg = TianGan.TIANGAN_JIA
        var lnDz = DiZhi.DIZHI_ZI
        for (j in 0..9) {
            base = startYearIndex + j
            lnTG = base % 10
            lnDZ = base % 12
            lnTg = BaziUtil().tgLookupMap.get(lnTG)!!
            lnDz = BaziUtil().dzLookupMap.get(lnDZ)!!
            descriptions = arrayListOf()

            builder = StringBuilder()
            builder.append(BaziUtil().getTianGanLabel(lnTg))
            builder.append(BaziUtil().getDizhiLabel(lnDz))
            builder.append("(")
            builder.append(stringResource(R.string.bazi_tkdc_liunian))
            builder.append(")")
            builder.append(" ")
            builder.append(dayunStartYear + (i - 1) * 10 + j)
            builder.append(stringResource(R.string.app_label_year))

            var liunianLabelStr = builder.toString()

            builder = StringBuilder()
            builder.append((i - 1) * 10 + yearOffet + j)
            builder.append(stringResource(R.string.age_unit))
            var liunianAgeStr = builder.toString()

            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    tg,
                    dz
                ) || BaziUtil().isTianKeDiChong(tg, dz, lnTg, lnDz)
            ) {
                descriptions.add(lnLabel + stringResource(R.string.bazi_tkdc_dayun_liunian))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.yearTiangan,
                    baziInfo.yearDizhi
                ) || BaziUtil().isTianKeDiChong(
                    baziInfo.yearTiangan,
                    baziInfo.yearDizhi, lnTg, lnDz
                )
            ) {
                descriptions.add(lnLabel + stringResource(R.string.bazi_tkdc_year))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.monthTiangan,
                    baziInfo.monthDizhi
                ) || BaziUtil().isTianKeDiChong(
                    baziInfo.monthTiangan,
                    baziInfo.monthDizhi, lnTg, lnDz
                )
            ) {
                descriptions.add(lnLabel + stringResource(R.string.bazi_tkdc_month))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.dayTiangan,
                    baziInfo.dayDizhi
                ) || BaziUtil().isTianKeDiChong(
                    baziInfo.dayTiangan,
                    baziInfo.dayDizhi, lnTg, lnDz
                )
            ) {
                descriptions.add(lnLabel + stringResource(R.string.bazi_tkdc_day))
            }
            if (BaziUtil().isTianKeDiChong(
                    lnTg,
                    lnDz,
                    baziInfo.hourTiangan,
                    baziInfo.hourDizhi
                ) || BaziUtil().isTianKeDiChong(
                    baziInfo.hourTiangan,
                    baziInfo.hourDizhi, lnTg, lnDz
                )
            ) {
                descriptions.add(lnLabel + stringResource(R.string.bazi_tkdc_hour))
            }

            if (descriptions.size > 0) {
                var r: TianKeDiChongRecord =
                    TianKeDiChongRecord(
                        liunianLabelStr,
                        "",
                        dayunStartYearStr.toString(),
                        liunianAgeStr,
                        descriptions,
                        ""
                    )
                records.add(r)
            }
        }
    }
    return records
}