package com.rick.bazi.ui

import android.icu.util.ChineseCalendar
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rick.bazi.BaziScreen
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.data.DataSource
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.FEMALE
import com.rick.bazi.data.MALE
import com.rick.bazi.data.TianGan
import com.rick.bazi.util.DateUtils
import com.rick.bazi.util.VietCalendar.convertSolar2Lunar
import com.rick.bazi.util.VietCalendar.jdFromDate
import com.rick.bazi.util.VietCalendar.jdToDate
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziStartScreen(
    onNextButtonClicked: (Int) -> Unit,
    onSelectionChanged: (String) -> Unit = {},
    navController: NavHostController,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf(MALE) }
    val dateState = rememberDatePickerState()

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    var formatDateStr by remember { mutableStateOf("YYYY-MM-DD") }
    var formatTimeStr by remember { mutableStateOf("MM-SS") }
    var testResultStr by remember { mutableStateOf("XXX-XXX") }

    formatDateStr = dateState.selectedDateMillis.toString()

    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }

    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: "YYYY-MM-DD"
    formatDateStr = dateToString
    formatTimeStr = "${timePickerState.hour}:${timePickerState.minute}"

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var year = 0
    millisToLocalDate?.let { year = it.year }
//    var year = millisToLocalDate.year
    var year2 = year - 3
    var year3 = year2 % 10

//    var yearDz = 0
    var yd2 = year % 100
    var yd3 = 0
    var yd4 = 0
    if (year >= 2000 && year < 2100) {
        yd3 = yd2 + 5
    } else if (year < 2000 && year >= 1900) {
        yd3 = yd2 + 1
    } else if (year < 1900 && year >= 1800) {
        yd3 = yd2 + 9
    } else {
        yd3 = year2
    }
    yd4 = yd3 % 12

    var tmp = 0

    var days = 0
    millisToLocalDate?.let { days = it.dayOfYear }
    if (year > 1900) {
        tmp = (year - 1900) * 5 + (year - 1900 + 3) / 4 + 9 + days
    }

    yd2 = tmp % 60
    yd3 = yd2 % 10
    yd4 = yd2 % 12
//    testResultStr = "${year}->${year2}->${year3} ${year}->${yd2}->${yd3}->${yd4}"

    testResultStr = "days of the year = ${days}"

//    testResultStr = "Day TG = ${tmp}->${yd2}->${yd3}->${yd4}"

//    val timeZone = 7.0
//    val jd = jdFromDate(9, 8, 1978)
//    val s = jdToDate(jd)
//    val l = convertSolar2Lunar(s[0], s[1], s[2], timeZone)
    var month = 0
    var day = 0
    millisToLocalDate?.let { baziModel.setBirthDateYear(it.year) }
    millisToLocalDate?.let { month = it.monthValue }
    millisToLocalDate?.let { day = it.dayOfMonth }
    val timeZone = 7.0
    val jd = jdFromDate(day, month, year)
    val s = jdToDate(jd)
    val l = convertSolar2Lunar(s[0], s[1], s[2], timeZone)

//    var str = DateUtils().getDayTianganBaseStr(year, month, day)

//    var r = DateUtils().getDayTianganBase(year, month, day)
//    var rs = 78/4
//    str = "${rs} |${str}=${r}"
//
//    testResultStr = str

//    testResultStr = "[${l[0]}->${l[1]}->${l[2]}]"

//    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//    var localDate = LocalDate.parse("9-8-1978", formatter)
//    var date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//    val chineseCalendar = ChineseCalendar(date)


//    chineseCalendar
//    var date : Date =

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    painter = painterResource(R.drawable.you_believe_science),
                    contentDescription = null
                )

                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopStart
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = 64.dp)
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = dateState,
                                showModeToggle = true
                            )
                        }
                    }
                }

                if (showTimePicker) {
                    Popup(
                        onDismissRequest = { showTimePicker = false },
                        alignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = 64.dp)
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            TimePicker(
                                state = timePickerState
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = selectedValue == MALE,
                    onClick = {
                        selectedValue = MALE
                        baziModel.setGender(MALE)
                        onSelectionChanged(MALE)
                    }
                )
                Text(text = stringResource(R.string.app_bazi_male))
                RadioButton(
                    selected = selectedValue == FEMALE,
                    onClick = {
                        selectedValue = FEMALE
                        baziModel.setGender(FEMALE)
                        onSelectionChanged(FEMALE)
                    }
                )
                Text(text = stringResource(R.string.app_bazi_female))


            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.app_bazi_birth_date)
                )

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showDatePicker = !showDatePicker
                    }
                ) {
                    Text(stringResource(R.string.pick))
                }

                Text(
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    text = "${formatDateStr}"
                )
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.app_bazi_birth_time)
                )

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showTimePicker = !showTimePicker
                    }
                ) {
                    Text(stringResource(R.string.pick))
                }

                Text(
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    text = "${formatTimeStr}"
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    val millisToLocalDate = dateState.selectedDateMillis?.let {
                        DateUtils().convertMillisToLocalDate(it)
                    }

                    var dayOfYear = 0
                    millisToLocalDate?.let { days = it.dayOfYear }

                    millisToLocalDate?.let { baziModel.setBirthDateYear(it.year) }
                    millisToLocalDate?.let { baziModel.setBirthDateMonth(it.monthValue) }
                    millisToLocalDate?.let { baziModel.setBirthDateDay(it.dayOfMonth) }

                    baziModel.setBirthHour(timePickerState.hour)
                    baziModel.setBirthMinute(timePickerState.minute)
                    baziModel.setGender(selectedValue)

                    var year = 0
                    var month = 0
                    var day = 0
                    val hour = timePickerState.hour
                    millisToLocalDate?.let { year = it.year }
                    millisToLocalDate?.let { month = it.monthValue }
                    millisToLocalDate?.let { day = it.dayOfMonth }

                    paipan(year, month, day, hour, dayOfYear, baziModel, baziInfo)

                    navController.navigate(BaziScreen.Paipan.name)
                }
            ) {
                Text(stringResource(R.string.app_bazi_paipan))
            }
        }

//        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    textAlign = TextAlign.Start,
//                    modifier = Modifier.weight(1f).fillMaxWidth(),
//                    fontSize = 32.sp,
//                    text = "${testResultStr}"
//                )
//            }
//        }
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(

                    modifier = Modifier.fillMaxSize().fillMaxHeight(),
                    painter = painterResource(R.drawable.baizisuanming),
                    contentDescription = null
                )
            }
        }
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(

                    modifier = Modifier.fillMaxSize().fillMaxHeight(),
                    painter = painterResource(R.drawable.baizisuanming),
                    contentDescription = null
                )
            }
        }
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(

                    modifier = Modifier.fillMaxSize().fillMaxHeight(),
                    painter = painterResource(R.drawable.baizisuanming),
                    contentDescription = null
                )
            }
        }

//        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    modifier = Modifier.fillMaxSize(),
//                    painter = painterResource(R.drawable.baizisuanming),
//                    contentDescription = null
//                )
//            }
//        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun paipan(
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    dayOfYear: Int,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo
) {

    var yearTG = 1
    var yearDZ = 1
    var monthTG = 1
    var monthDZ = 1
    var dayTG = 1
    var dayDZ = 1
    var hourTG = 1
    var hourDZ = 1
    var tg: TianGan = TianGan.TIANGAN_JIA
    var dz: DiZhi = DiZhi.DIZHI_ZI

    var tmp = 0
    //calculate Year Tiangan & Dizhi
    tmp = year - 3
    yearTG = tmp % 10

    val tgLookupMap: Map<Int, TianGan> = baziInfo.tgLookupMap
    val dzLookupMap: Map<Int, DiZhi> = baziInfo.dzLookupMap

    tg = tgLookupMap[yearTG]!!
    baziModel.setYearTiangan(tg)

    var yearDZTmp = 0

    yearDZTmp = year % 100

    if (year >= 2000 && year < 2100) {
        tmp = yearDZTmp + 5
    } else if (year < 2000 && year >= 1900) {
        tmp = yearDZTmp + 1
    } else if (year < 1900 && year >= 1800) {
        tmp = yearDZTmp + 9
    } else {
        yearDZTmp = year - 3
        tmp = yearDZTmp
    }
    yearDZ = tmp % 12

    dz = dzLookupMap.get(yearDZ)!!
    baziModel.setYearDiZhi(dz)

    //calculate Month Tiangan & Dizhi
    //get lunar month
    val timeZone = 7.0
    val jd = jdFromDate(day, month, year)
    val s = jdToDate(jd)
    val l = convertSolar2Lunar(s[0], s[1], s[2], timeZone)
    val lunarMonth = l[1]

    tmp = yearTG * 2 + lunarMonth
    monthTG = tmp % 10

    tg = tgLookupMap[monthTG]!!
    baziModel.setMonthTiangan(tg)

    if (lunarMonth == 11) {
        dz = DiZhi.DIZHI_ZI
    } else if (lunarMonth == 12) {
        dz = DiZhi.DIZHI_CHOU
    } else {
        tmp = lunarMonth + 2
        dz = dzLookupMap.get(tmp)!!
    }
    baziModel.setMonthDiZhi(dz)

    //calculate Day Tiangan & Dizhi

    var r = 0
    //23:00 to 23:59 is belong to the next day in lunar calendar
    if (hour == 23) {
        r = DateUtils().getDayTianganBase(year, month, day + 1)
    } else {
        r = DateUtils().getDayTianganBase(year, month, day)
    }

    val dayBase = r % 60

    dayTG = dayBase % 10
    dayDZ = dayBase % 12
    tg = tgLookupMap[dayTG]!!
    dz = dzLookupMap.get(dayDZ)!!

    baziModel.setDayTiangan(tg)
    baziModel.setDayDiZhi(dz)

    //calculate Hour Tiangan & Dizhi
    if (hour == 23 || hour == 0) {
        dz = DiZhi.DIZHI_ZI
        hourDZ = 1
    } else if (hour == 1 || hour == 2) {
        dz = DiZhi.DIZHI_CHOU
        hourDZ = 2
    } else if (hour == 3 || hour == 4) {
        dz = DiZhi.DIZHI_YIN
        hourDZ = 3
    } else if (hour == 5 || hour == 6) {
        dz = DiZhi.DIZHI_MOU
        hourDZ = 4
    } else if (hour == 7 || hour == 8) {
        dz = DiZhi.DIZHI_CHEN
        hourDZ = 5
    } else if (hour == 9 || hour == 10) {
        dz = DiZhi.DIZHI_SI
        hourDZ = 6
    } else if (hour == 11 || hour == 12) {
        dz = DiZhi.DIZHI_WU
        hourDZ = 7
    } else if (hour == 13 || hour == 14) {
        dz = DiZhi.DIZHI_WEI
        hourDZ = 8
    } else if (hour == 15 || hour == 16) {
        dz = DiZhi.DIZHI_SHEN
        hourDZ = 9
    } else if (hour == 17 || hour == 18) {
        dz = DiZhi.DIZHI_YOU
        hourDZ = 10
    } else if (hour == 19 || hour == 20) {
        dz = DiZhi.DIZHI_XU
        hourDZ = 11
    } else if (hour == 21 || hour == 22) {
        dz = DiZhi.DIZHI_HAI
        hourDZ = 12
    }

    tmp = dayTG * 2 + hourDZ - 2
    hourTG = tmp % 10
    println("hourTG=${hourTG}")
    if (hourTG < 0) {
        hourTG = 1
        println("####: found hourTG 0")
    }

    tg = tgLookupMap[hourTG]!!
    baziModel.setHourTiangan(tg)

    baziModel.setHourDiZhi(dz)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BaziStartPreview() {
    BaziTheme {
        BaziStartScreen(
            onNextButtonClicked = {},
            onSelectionChanged = {},
            navController = rememberNavController(),
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

