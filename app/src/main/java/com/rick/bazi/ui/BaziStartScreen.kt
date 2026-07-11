package com.rick.bazi.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.rick.bazi.data.FEMALE
import com.rick.bazi.data.MALE
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.DateUtils
import java.util.Calendar

// 配色常量
private val GradientStart = Color(0xFF667eea)
private val GradientEnd = Color(0xFF764ba2)
private val CardBg = Color(0xFFFFFFFF)
private val AccentGold = Color(0xFFFFD700)
private val TextPrimary = Color(0xFF2C3E50)
private val TextSecondary = Color(0xFF95A5A6)
private val InputBg = Color(0xFFF0F0F5)
private val MaleColor = Color(0xFF3498DB)
private val FemaleColor = Color(0xFFE91E63)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziStartScreen(
    onNextButtonClicked: (Int) -> Unit = {},
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
        is24Hour = true
    )

    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: "请选择日期"
    var formatDateStr by remember { mutableStateOf(dateToString) }
    var formatTimeStr by remember {
        mutableStateOf(String.format("%02d:%02d", timePickerState.hour, timePickerState.minute))
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // 更新时间显示
    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        formatTimeStr = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
    }

    LaunchedEffect(dateToString) {
        formatDateStr = dateToString
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // 顶部标题区域
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 图标
                    Surface(
                        shape = CircleShape,
                        color = AccentGold.copy(alpha = 0.2f),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = AccentGold,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.app_bazi_science),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "请输入您的出生信息",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 输入区域卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // 出生日期选择
                    InputFieldCard(
                        icon = Icons.Default.CalendarMonth,
                        label = "出生日期",
                        value = formatDateStr,
                        onClick = { showDatePicker = true }
                    )

                    // 出生时间选择
                    InputFieldCard(
                        icon = Icons.Default.Schedule,
                        label = "出生时间",
                        value = formatTimeStr,
                        onClick = { showTimePicker = true }
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )

                    // 性别选择
                    Text(
                        text = "性别",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        GenderOption(
                            text = stringResource(R.string.app_bazi_male),
                            icon = Icons.Default.Man,
                            isSelected = selectedValue == MALE,
                            color = MaleColor,
                            onClick = {
                                selectedValue = MALE
                                baziModel.setGender(MALE)
                                onSelectionChanged(MALE)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        GenderOption(
                            text = stringResource(R.string.app_bazi_female),
                            icon = Icons.Default.Woman,
                            isSelected = selectedValue == FEMALE,
                            color = FemaleColor,
                            onClick = {
                                selectedValue = FEMALE
                                baziModel.setGender(FEMALE)
                                onSelectionChanged(FEMALE)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 排盘按钮
                    Button(
                        onClick = {
                            val millisToLocalDate = dateState.selectedDateMillis?.let {
                                DateUtils().convertMillisToLocalDate(it)
                            }
                            millisToLocalDate?.let { baziModel.setBirthDateYear(it.year) }
                            millisToLocalDate?.let { baziModel.setBirthDateMonth(it.monthValue) }
                            millisToLocalDate?.let { baziModel.setBirthDateDay(it.dayOfMonth) }
                            baziModel.setBirthHour(timePickerState.hour)
                            baziModel.setBirthMinute(timePickerState.minute)
                            baziModel.setGender(selectedValue)
                            navController.navigate(BaziScreen.Pan.name)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GradientEnd
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.app_bazi_paipan),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 日期选择弹窗
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("确定", color = GradientEnd)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("取消", color = TextSecondary)
                    }
                }
            ) {
                DatePicker(state = dateState)
            }
        }

        // 时间选择弹窗
        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text("确定", color = GradientEnd)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text("取消", color = TextSecondary)
                    }
                },
                title = { Text("选择时间") },
                text = {
                    TimeInput(state = timePickerState)
                }
            )
        }
    }
}

@Composable
private fun InputFieldCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = LocalIndication.current
//                ) { onClick() },
            onClick = { onClick() },
            shape = RoundedCornerShape(12.dp),
            color = InputBg
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = GradientEnd,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (value.contains("请选择") || value.contains("YYYY"))
                            TextSecondary else TextPrimary
                    )
                }
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "编辑",
                    tint = TextSecondary.copy(alpha = 0.5f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun GenderOption(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color.copy(alpha = 0.1f) else InputBg
        ),
        border = if (isSelected)
            BorderStroke(2.dp, color)
        else null,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) color else TextSecondary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) color else TextSecondary
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BaziStartScreenPreview() {
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