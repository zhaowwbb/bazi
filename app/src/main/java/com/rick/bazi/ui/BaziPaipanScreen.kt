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
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.MALE
import com.rick.bazi.data.TianGan
import com.rick.bazi.util.getTianganStr
import com.rick.bazi.util.getDizhiStr
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.DateUtils


data class DayunRecord(
    val dyLabel: String,
    val dyAgeLabel: String
)

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

        var liunianBase: Int = BaziUtil().getTodayCyclicalYearBase()
        var liunianTgStr = BaziUtil().getTianGanLabel(BaziUtil().getTianGan(liunianBase))
        var liunianDzStr = BaziUtil().getDizhiLabel(BaziUtil().getDiZhi(liunianBase))
        var liunianStr = stringResource(R.string.bazi_liunian)

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
                    text = "$liunianStr $liunianTgStr$liunianDzStr",
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(300),
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineMedium
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
        }

        //show main star
        val yearStarLabel = BaziUtil().getMainStarLabel(baziInfo.yearTiangan, baziInfo.dayTiangan)
        val monthStarLabel = BaziUtil().getMainStarLabel(baziInfo.monthTiangan, baziInfo.dayTiangan)
        val hourStarLabel = BaziUtil().getMainStarLabel(baziInfo.hourTiangan, baziInfo.dayTiangan)

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
                    text = stringResource(R.string.bazi_main_star),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "$yearStarLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "$monthStarLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.bazi_owner),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Green,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "$hourStarLabel",
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
                    color = Color.Magenta,
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
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = getDizhiStr(baziInfo, baziInfo.yearDizhi),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Magenta,
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

            //show cang gan
            val yearCangganLabel =
                BaziUtil().getCangganLabel(BaziUtil().getCanggan(baziInfo.yearDizhi))
            val monthCangganLabel =
                BaziUtil().getCangganLabel(BaziUtil().getCanggan(baziInfo.monthDizhi))
            val dayCangganLabel =
                BaziUtil().getCangganLabel(BaziUtil().getCanggan(baziInfo.dayDizhi))
            val hourCangganLabel =
                BaziUtil().getCangganLabel(BaziUtil().getCanggan(baziInfo.hourDizhi))
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.bazi_canggan),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "$yearCangganLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(200),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$monthCangganLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(200),
//                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$dayCangganLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(200),
//                    color = Color.Green,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$hourCangganLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(200),
//                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            //second star
            val yearCangganStarLabel = BaziUtil().getCangganStarLabel(
                BaziUtil().getCanggan(baziInfo.yearDizhi),
                baziInfo.dayTiangan
            )
            val monthCangganStarLabel = BaziUtil().getCangganStarLabel(
                BaziUtil().getCanggan(baziInfo.monthDizhi),
                baziInfo.dayTiangan
            )
            val dayCangganStarLabel = BaziUtil().getCangganStarLabel(
                BaziUtil().getCanggan(baziInfo.dayDizhi),
                baziInfo.dayTiangan
            )
            val hourCangganStarLabel = BaziUtil().getCangganStarLabel(
                BaziUtil().getCanggan(baziInfo.hourDizhi),
                baziInfo.dayTiangan
            )
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.bazi_second_star),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Red,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "$yearCangganStarLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Magenta,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$monthCangganStarLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$dayCangganStarLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Green,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$hourCangganStarLabel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
//                    color = Color.Cyan,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

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
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.Summary.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_taohua_query),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.TianKeDiChong.name)
                }) {
                    Text(
                        text = stringResource(R.string.bazi_tkdc),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
            }
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.Sample.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_sample),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.Version.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_version),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
            }
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.Introduction.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_introduction),
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

@Composable
fun getDayunList(baziInfo: BaziInfo) : MutableList<DayunRecord>{
    var records: MutableList<DayunRecord> = arrayListOf()
    var startIndex = 1
    var endIndex = 10
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