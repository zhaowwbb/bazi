package com.rick.bazi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import com.rick.bazi.BaziScreen
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.MALE
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.theme.BaziTheme

@Composable
fun BaziSummaryScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

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
                HorizontalDivider(thickness = 10.dp)
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
//                    text = stringResource(R.string.msg_taohua_normal),
                    text = calculateTaohua(baziInfo),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
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
                HorizontalDivider(thickness = 10.dp)
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
                    text = stringResource(R.string.dts_d),
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
                    text = stringResource(R.string.dts_t),
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
                    text = stringResource(R.string.dts_s),
                    color = Color.Blue,
                    style = MaterialTheme.typography.headlineLarge
                )
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
                Divider(
                    thickness = dimensionResource(R.dimen.thickness_divider),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )

                Divider(
                    thickness = dimensionResource(R.dimen.thickness_divider),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

@Preview
@Composable
fun BaziSummaryPreview() {
    BaziTheme {
        BaziSummaryScreen(
//            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun checkBianyeTaohua(baziInfo: BaziInfo): Boolean {
    var set = setOf<DiZhi>(DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU, DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU)
    if (set.contains(baziInfo.yearDizhi) &&
        set.contains(baziInfo.monthDizhi) &&
        set.contains(baziInfo.dayDizhi) &&
        set.contains(
            baziInfo.hourDizhi
        )
    ) {
        return true
    }
    return false
}

fun checkLuoxingTaohua(baziInfo: BaziInfo): Boolean {
    if(baziInfo.dayTiangan == TianGan.TIANGAN_JIA && baziInfo.dayDizhi == DiZhi.DIZHI_ZI){
        if(baziInfo.monthTiangan == TianGan.TIANGAN_GENG && baziInfo.monthDizhi == DiZhi.DIZHI_WU){
            return true
        }
        if(baziInfo.hourTiangan == TianGan.TIANGAN_GENG && baziInfo.hourDizhi == DiZhi.DIZHI_WU){
            return true
        }
    }

    if(baziInfo.hourTiangan == TianGan.TIANGAN_GENG && baziInfo.hourDizhi == DiZhi.DIZHI_WU){
        if(baziInfo.monthTiangan == TianGan.TIANGAN_JIA && baziInfo.monthDizhi == DiZhi.DIZHI_ZI){
            return true
        }
        if(baziInfo.hourTiangan == TianGan.TIANGAN_JIA && baziInfo.hourDizhi == DiZhi.DIZHI_ZI){
            return true
        }
    }
    return false
}

@Composable
fun calculateTaohua(baziInfo: BaziInfo): String {
    var taohuaMap = mutableMapOf<DiZhi, DiZhi>()
    var yDz = baziInfo.yearDizhi
    var dDz = baziInfo.dayDizhi

    //group one
    if (yDz == DiZhi.DIZHI_YIN || yDz == DiZhi.DIZHI_WU || yDz == DiZhi.DIZHI_XU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_MOU)) {
            taohuaMap.put(DiZhi.DIZHI_MOU, DiZhi.DIZHI_MOU)
            println("Add taohua MOU")
        }
    }
    if (dDz == DiZhi.DIZHI_YIN || dDz == DiZhi.DIZHI_WU || dDz == DiZhi.DIZHI_XU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_MOU)) {
            taohuaMap.put(DiZhi.DIZHI_MOU, DiZhi.DIZHI_MOU)
            println("Add taohua MOU")
        }
    }

    //group two
    if (yDz == DiZhi.DIZHI_SHEN || yDz == DiZhi.DIZHI_ZI || yDz == DiZhi.DIZHI_CHEN) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_YOU)) {
            taohuaMap.put(DiZhi.DIZHI_YOU, DiZhi.DIZHI_YOU)
            println("Add taohua YOU")
        }
    }
    if (dDz == DiZhi.DIZHI_SHEN || dDz == DiZhi.DIZHI_ZI || dDz == DiZhi.DIZHI_CHEN) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_YOU)) {
            taohuaMap.put(DiZhi.DIZHI_YOU, DiZhi.DIZHI_YOU)
            println("Add taohua YOU")
        }
    }

    //group three
    if (yDz == DiZhi.DIZHI_HAI || yDz == DiZhi.DIZHI_MOU || yDz == DiZhi.DIZHI_WEI) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_ZI)) {
            taohuaMap.put(DiZhi.DIZHI_ZI, DiZhi.DIZHI_ZI)
            println("Add taohua ZI")
        }
    }
    if (dDz == DiZhi.DIZHI_HAI || dDz == DiZhi.DIZHI_MOU || dDz == DiZhi.DIZHI_WEI) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_ZI)) {
            taohuaMap.put(DiZhi.DIZHI_ZI, DiZhi.DIZHI_ZI)
            println("Add taohua ZI")
        }
    }

    //group four
    if (yDz == DiZhi.DIZHI_SI || yDz == DiZhi.DIZHI_YOU || yDz == DiZhi.DIZHI_CHOU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_WU)) {
            taohuaMap.put(DiZhi.DIZHI_WU, DiZhi.DIZHI_WU)
            println("Add taohua WU")
        }
    }
    if (dDz == DiZhi.DIZHI_SI || dDz == DiZhi.DIZHI_YOU || dDz == DiZhi.DIZHI_CHOU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_WU)) {
            taohuaMap.put(DiZhi.DIZHI_WU, DiZhi.DIZHI_WU)
            println("Add taohua WU")
        }
    }
    var genderStr = ""
    if (baziInfo.gender == MALE) {
        genderStr = stringResource(R.string.msg_male)
    } else {
        genderStr = stringResource(R.string.msg_female)
    }
    println("taohuaMap = $taohuaMap")
    var content = ""
    var taohuaCount = 0

    taohuaMap.forEach() {
        if (it.key == baziInfo.yearDizhi) {
            taohuaCount += 1
        } else if (it.key == baziInfo.monthDizhi) {
            taohuaCount += 1
        } else if (it.key == baziInfo.dayDizhi) {
            taohuaCount += 1
        } else if (it.key == baziInfo.hourDizhi) {
            taohuaCount += 1
        }
    }
    println("taohuaCount = $taohuaCount")

    if(checkBianyeTaohua(baziInfo)){
        content = stringResource(R.string.msg_taohua_bianye)
    }else if(checkLuoxingTaohua(baziInfo)){
        content = stringResource(R.string.msg_taohua_luoxing)
    } else if (taohuaCount == 0) {
        content = stringResource(R.string.msg_taohua_0)
    } else if (taohuaCount == 1) {
        content = stringResource(R.string.msg_taohua_1)
    } else if (taohuaCount == 2) {
        content = stringResource(R.string.msg_taohua_2)
    } else if (taohuaCount == 3) {
        content = stringResource(R.string.msg_taohua_3)
    } else if (taohuaCount == 4) {
        content = stringResource(R.string.msg_taohua_4)
    }

    var result = "$genderStr  $content "

    return result
}