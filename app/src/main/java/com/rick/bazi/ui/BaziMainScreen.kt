package com.rick.bazi.ui

import android.app.Activity
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.rick.bazi.util.BaziUtil
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
    var selectedGenderValue by rememberSaveable { mutableStateOf(MALE) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //Gender pick
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = selectedGenderValue == MALE,
                    onClick = {
                        selectedGenderValue = MALE
                        baziModel.setGender(MALE)
                        onSelectionChanged(MALE)
                    }
                )
                Text(
                    text = stringResource(R.string.app_bazi_male),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 32.sp
                )
                RadioButton(
                    selected = selectedGenderValue == FEMALE,
                    onClick = {
                        selectedGenderValue = FEMALE
                        baziModel.setGender(FEMALE)
                        onSelectionChanged(FEMALE)
                    }
                )
                Text(
                    text = stringResource(R.string.app_bazi_female),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 32.sp
                )

            }
        }
        val defaultYear = Calendar.getInstance().get(Calendar.YEAR)
        //this is to reduce year pick list, the algrithm can support early date
        val maxYear = defaultYear + 100
        val defaultYearIndex = 120
        val minYear = defaultYear - defaultYearIndex
        val yearValues = remember { (minYear..maxYear).map { it.toString() } }
        val yearValuesPickerState = rememberPickerState()
        val monthValues = remember { (1..12).map { it.toString() } }
        val monthValuesPickerState = rememberPickerState()
        val dayValues = remember { (1..31).map { it.toString() } }
        val dayValuesPickerState = rememberPickerState()
        val hourValues = remember { (0..23).map { it.toString() } }
        val hourValuesPickerState = rememberPickerState()

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

        //year picker
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
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    text = stringResource(R.string.bazi_birth_year)
                )
                Picker(
                    state = yearValuesPickerState,
                    items = yearValues,
                    startIndex = defaultYearIndex,
                    visibleItemsCount = 1,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 22.sp),
                    dividerColor = Color(0xFFE8E8E8)
                )

                Text(
                    text = "${yearValuesPickerState.selectedItem}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }
        }

        //month picker
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
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    text = stringResource(R.string.bazi_birth_month)
                )
                Picker(
                    state = monthValuesPickerState,
                    items = monthValues,
                    visibleItemsCount = 1,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 22.sp),
                    dividerColor = Color(0xFFE8E8E8)
                )

                Text(
                    text = "${monthValuesPickerState.selectedItem}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }
        }

        //day picker
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
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    text = stringResource(R.string.bazi_birth_day)
                )
                Picker(
                    state = dayValuesPickerState,
                    items = dayValues,
                    visibleItemsCount = 1,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 22.sp),
                    dividerColor = Color(0xFFE8E8E8)
                )

                Text(
                    text = "${dayValuesPickerState.selectedItem}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }
        }

        //hour picker
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
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    text = stringResource(R.string.bazi_birth_hour)
                )
                Picker(
                    state = hourValuesPickerState,
                    items = hourValues,
                    visibleItemsCount = 1,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 22.sp),
                    dividerColor = Color(0xFFE8E8E8)
                )

                Text(
                    text = "${hourValuesPickerState.selectedItem}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 16.dp)
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
                HorizontalDivider(thickness = 2.dp)
            }
        }

        //Calculate bazi
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

                        val year = yearValuesPickerState.selectedItem.toInt()
                        val month = monthValuesPickerState.selectedItem.toInt()
                        val day = dayValuesPickerState.selectedItem.toInt()
                        val hour = hourValuesPickerState.selectedItem.toInt()

                        baziModel.setBirthDateYear(year)
                        baziModel.setBirthDateMonth(month)
                        baziModel.setBirthDateDay(day)
                        baziModel.setBirthHour(hour)

                        baziModel.setGender(selectedGenderValue)

                        paipan(year, month, day, hour, baziModel, baziInfo)

                        navController.navigate(BaziScreen.Paipan.name)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.app_bazi_paipan),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
                val activity = (LocalContext.current as? Activity)
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        activity?.finish()
                    }
                ){
                    Text(
                        text = stringResource(R.string.bazi_quit),
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }
        }

        //create bottom space
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
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun paipan(
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo
) {
    println("Bazi Paipan: year=$year,month=$month,day=$day,hour=$hour ")
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

    val tgLookupMap: Map<Int, TianGan> = baziInfo.tgLookupMap
    val dzLookupMap: Map<Int, DiZhi> = baziInfo.dzLookupMap

    //calculate Year Tiangan & Dizhi
    var yearBase =  BaziUtil().getCyclicalYearBase(year, month, day, hour)
    baziModel.setYearBase(yearBase)
    yearTG = yearBase % 10
    tg = tgLookupMap[yearTG]!!
    baziModel.setYearTiangan(tg)
    yearDZ = yearBase % 12
    dz = dzLookupMap[yearDZ]!!
    baziModel.setYearDiZhi(dz)

    //calculate Month Tiangan & Dizhi
    var monthBase = BaziUtil().getCyclicalMonthBase(year, month, day, hour)
    baziModel.setMonthBase(monthBase)
    monthTG = monthBase % 10
    tg = tgLookupMap[monthTG]!!
    baziModel.setMonthTiangan(tg)
    monthDZ = monthBase % 12
    dz = dzLookupMap.get(monthDZ)!!
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
    baziModel.setDayBase(dayBase)

    dayTG = dayBase % 10
    dayDZ = dayBase % 12
    tg = tgLookupMap[dayTG]!!
    dz = dzLookupMap.get(dayDZ)!!

    baziModel.setDayTiangan(tg)
    baziModel.setDayDiZhi(dz)

    //calculate Hour Tiangan & Dizhi
    dz = BaziUtil().getHourDZ(hour)
    hourDZ = BaziUtil().getHourDZInt(hour)

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

