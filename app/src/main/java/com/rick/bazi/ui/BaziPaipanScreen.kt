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
import androidx.compose.runtime.remember
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
import com.rick.bazi.util.BaziMeasureUtil
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.DateUtils
import com.rick.bazi.util.DiZhiUtil
import com.rick.bazi.util.ShiShenUtil


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
//    val preProcessBazi = mutableStateOf(false)

    val preProcessBazi = remember { mutableStateOf(false) }

//    preProcessBazi.value

//    var preProcessBazi by rememberSaveable { mutableStateOf(false) }
    if(preProcessBazi.value) {
        BaziMeasureUtil().analyzeBaziAndSaveStat(baziInfo, baziModel)
    }

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
        val yearStarLabel = ShiShenUtil().getShiShenText(baziInfo.yearTiangan, baziInfo.dayTiangan)
        val monthStarLabel = ShiShenUtil().getShiShenText(baziInfo.monthTiangan, baziInfo.dayTiangan)
        val hourStarLabel = ShiShenUtil().getShiShenText(baziInfo.hourTiangan, baziInfo.dayTiangan)

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
                BaziUtil().getCangganLabel(DiZhiUtil().getCanggan(baziInfo.yearDizhi))
            val monthCangganLabel =
                BaziUtil().getCangganLabel(DiZhiUtil().getCanggan(baziInfo.monthDizhi))
            val dayCangganLabel =
                BaziUtil().getCangganLabel(DiZhiUtil().getCanggan(baziInfo.dayDizhi))
            val hourCangganLabel =
                BaziUtil().getCangganLabel(DiZhiUtil().getCanggan(baziInfo.hourDizhi))
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
            val yearCangganStarLabel = ShiShenUtil().getCangganStarLabel(
                DiZhiUtil().getCanggan(baziInfo.yearDizhi),
                baziInfo.dayTiangan
            )
            val monthCangganStarLabel = ShiShenUtil().getCangganStarLabel(
                DiZhiUtil().getCanggan(baziInfo.monthDizhi),
                baziInfo.dayTiangan
            )
            val dayCangganStarLabel = ShiShenUtil().getCangganStarLabel(
                DiZhiUtil().getCanggan(baziInfo.dayDizhi),
                baziInfo.dayTiangan
            )
            val hourCangganStarLabel = ShiShenUtil().getCangganStarLabel(
                DiZhiUtil().getCanggan(baziInfo.hourDizhi),
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
                    preProcessBazi.value = true
                    navController.navigate(BaziScreen.Analysis.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_analysis),
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
                    navController.navigate(BaziScreen.DaYun.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_dayun),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.YiMa.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_yima),
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
                    navController.navigate(BaziScreen.PrivacyPolicy.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_privacy_policy),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                    )
                }
                OutlinedButton(modifier = Modifier.weight(1f), onClick = {
                    navController.navigate(BaziScreen.UserAgreement.name)
                }) {
                    Text(
                        text = stringResource(R.string.app_bazi_user_agreement),
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
