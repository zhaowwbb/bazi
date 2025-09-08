package com.rick.bazi.ui

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
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
import com.rick.bazi.data.FEMALE
import com.rick.bazi.data.MALE
import com.rick.bazi.util.DateUtils
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziStartScreen1(
    quantityOptions: List<Pair<Int, Int>>,
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
//    dateState.selectedDateMillis = currentTime.timeInMillis
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )


    var formatDateStr by remember { mutableStateOf("YYYY-MM-DD") }
    var formatTimeStr by remember { mutableStateOf("MM-SS") }

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

    Column(
        modifier = Modifier.padding(5.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Red)
//                .padding(5.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(stringResource(R.string.app_bazi_science))
        }
//        Spacer(modifier = Modifier.width(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Magenta)
//            modifier = Modifier
//                .padding(5.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
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
//    Spacer(modifier = Modifier.width(5.dp))

//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Yellow)
////        modifier = Modifier.padding(5.dp),
////        horizontalArrangement = Arrangement.Center
//    ) {
//
//
//    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().height(400.dp).background(Color.Blue)
//        modifier = Modifier.padding(5.dp),
//        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
//                    .fillMaxWidth()
                .padding(16.dp),
//                fontSize = 30.sp,
            text = stringResource(R.string.app_bazi_birth_time)
        )

        Button(
            onClick = {
                showTimePicker = !showTimePicker
            }) {
            Text(stringResource(R.string.app_bazi_birth_time))
        }
        Text(
            modifier = Modifier
                .padding(16.dp),
            fontSize = 30.sp,
            text = "[${formatTimeStr}]"
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Black)
//        modifier = Modifier.padding(5.dp),
//        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifier,
//                fontSize = 30.sp,
            text = stringResource(R.string.app_bazi_birth_date)
        )
        Divider(
            thickness = dimensionResource(R.dimen.thickness_divider),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        Button(
            onClick = {
                showDatePicker = !showDatePicker
            }
        ) {
            Text(stringResource(R.string.app_bazi_birth_date))
        }
        Text(
            modifier = modifier,
//                fontSize = 30.sp,
            text = stringResource(R.string.app_bazi_suanming)
        )
        Text(
            modifier = Modifier
//                    .fillMaxWidth()
                .padding(16.dp),
            fontSize = 30.sp,
            text = "[${formatDateStr}]"
        )
    }

    Row(
//        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().background(Color.Green)
//        modifier = Modifier.padding(5.dp),
//        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(selected = selectedValue == MALE, onClick = {
            selectedValue = MALE
            baziModel.setGender(MALE)
            onSelectionChanged(MALE)
        }
        )
        Text(
            text = stringResource(R.string.app_bazi_male),
            style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(start = 16.dp)
        )

        RadioButton(selected = selectedValue == FEMALE, onClick = {
            selectedValue = FEMALE
            baziModel.setGender(FEMALE)
            onSelectionChanged(FEMALE)
        }
        )
        Text(
            text = stringResource(R.string.app_bazi_female),
            style = MaterialTheme.typography.bodyLarge,
        )

        Button(onClick = {
            val millisToLocalDate = dateState.selectedDateMillis?.let {
                DateUtils().convertMillisToLocalDate(it)
            }

            millisToLocalDate?.let { baziModel.setBirthDateYear(it.year) }
            millisToLocalDate?.let { baziModel.setBirthDateMonth(it.monthValue) }
            millisToLocalDate?.let { baziModel.setBirthDateDay(it.dayOfMonth) }

            baziModel.setBirthHour(timePickerState.hour)
            baziModel.setBirthMinute(timePickerState.minute)
            baziModel.setGender(selectedValue)
//            paipan(baziModel, baziInfo)

            navController.navigate(BaziScreen.Paipan.name)
        }) {
            Text(text = stringResource(R.string.app_bazi_paipan))
        }

    }
}


fun paipan2(
    baziModel: BaziViewModel,
    baziInfo: BaziInfo
) {
    val year: Int = baziInfo.birthDateYear
    var n: Int = year - 3
    n = n.mod(10)
    baziModel.setName(
        n.toString()
    )
//    baziInfo.name = n.toString()
//    Log.d(TAG, "##### Rick ######…")
    println("##### Rick ######")
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BaziStart1Preview() {
    BaziTheme {
        BaziStartScreen(
//            quantityOptions = DataSource.quantityOptions,
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

