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
import com.rick.bazi.data.FEMALE
import com.rick.bazi.data.MALE
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil

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
    println("Check BianyeTaohua")
    var set = setOf<DiZhi>(DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU, DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU)
    if (set.contains(baziInfo.yearDizhi) &&
        set.contains(baziInfo.monthDizhi) &&
        set.contains(baziInfo.dayDizhi) &&
        set.contains(
            baziInfo.hourDizhi
        )
    ) {
        println("Found BianyeTaohua")
        return true
    }
    return false
}

fun checkLuoxingTaohua(baziInfo: BaziInfo): Boolean {
    println("Check LuoxingTaohua")
    if (baziInfo.dayTiangan == TianGan.TIANGAN_JIA && baziInfo.dayDizhi == DiZhi.DIZHI_ZI) {
        if (baziInfo.monthTiangan == TianGan.TIANGAN_GENG && baziInfo.monthDizhi == DiZhi.DIZHI_WU) {
            return true
        }
        if (baziInfo.hourTiangan == TianGan.TIANGAN_GENG && baziInfo.hourDizhi == DiZhi.DIZHI_WU) {
            return true
        }
    }

    if (baziInfo.hourTiangan == TianGan.TIANGAN_GENG && baziInfo.hourDizhi == DiZhi.DIZHI_WU) {
        if (baziInfo.monthTiangan == TianGan.TIANGAN_JIA && baziInfo.monthDizhi == DiZhi.DIZHI_ZI) {
            return true
        }
        if (baziInfo.hourTiangan == TianGan.TIANGAN_JIA && baziInfo.hourDizhi == DiZhi.DIZHI_ZI) {
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
//            println("Add taohua MOU")
        }
    }
    if (dDz == DiZhi.DIZHI_YIN || dDz == DiZhi.DIZHI_WU || dDz == DiZhi.DIZHI_XU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_MOU)) {
            taohuaMap.put(DiZhi.DIZHI_MOU, DiZhi.DIZHI_MOU)
//            println("Add taohua MOU")
        }
    }

    //group two
    if (yDz == DiZhi.DIZHI_SHEN || yDz == DiZhi.DIZHI_ZI || yDz == DiZhi.DIZHI_CHEN) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_YOU)) {
            taohuaMap.put(DiZhi.DIZHI_YOU, DiZhi.DIZHI_YOU)
//            println("Add taohua YOU")
        }
    }
    if (dDz == DiZhi.DIZHI_SHEN || dDz == DiZhi.DIZHI_ZI || dDz == DiZhi.DIZHI_CHEN) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_YOU)) {
            taohuaMap.put(DiZhi.DIZHI_YOU, DiZhi.DIZHI_YOU)
//            println("Add taohua YOU")
        }
    }

    //group three
    if (yDz == DiZhi.DIZHI_HAI || yDz == DiZhi.DIZHI_MOU || yDz == DiZhi.DIZHI_WEI) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_ZI)) {
            taohuaMap.put(DiZhi.DIZHI_ZI, DiZhi.DIZHI_ZI)
//            println("Add taohua ZI")
        }
    }
    if (dDz == DiZhi.DIZHI_HAI || dDz == DiZhi.DIZHI_MOU || dDz == DiZhi.DIZHI_WEI) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_ZI)) {
            taohuaMap.put(DiZhi.DIZHI_ZI, DiZhi.DIZHI_ZI)
//            println("Add taohua ZI")
        }
    }

    //group four
    if (yDz == DiZhi.DIZHI_SI || yDz == DiZhi.DIZHI_YOU || yDz == DiZhi.DIZHI_CHOU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_WU)) {
            taohuaMap.put(DiZhi.DIZHI_WU, DiZhi.DIZHI_WU)
//            println("Add taohua WU")
        }
    }
    if (dDz == DiZhi.DIZHI_SI || dDz == DiZhi.DIZHI_YOU || dDz == DiZhi.DIZHI_CHOU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_WU)) {
            taohuaMap.put(DiZhi.DIZHI_WU, DiZhi.DIZHI_WU)
//            println("Add taohua WU")
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
    println("Bazi taohuaCount = $taohuaCount")

    content = getTaohuaCheckResult(baziInfo, taohuaMap, taohuaCount)

    var liunianTao = getLiunianTaohua(baziInfo, taohuaMap)

    var result = "$genderStr  $content $liunianTao"

    return result
}

@Composable
fun getTaohuaCheckResult(
    baziInfo: BaziInfo,
    taohuaMap: Map<DiZhi, DiZhi>,
    taohuaCount: Int
): String {
    if (checkBianyeTaohua(baziInfo)) {
        return stringResource(R.string.msg_taohua_bianye)
    } else if (checkLuoxingTaohua(baziInfo)) {
        return stringResource(R.string.msg_taohua_luoxing)
    } else if (checkGunlangTaohua(baziInfo)) {
        return stringResource(R.string.msg_taohua_gunlang)
    } else if (checkTaohuaRen(baziInfo, taohuaMap)) {
        return stringResource(R.string.msg_taohua_ren)
    } else if (checkTaohuaJie(baziInfo, taohuaMap)) {
        return stringResource(R.string.msg_taohua_jie)
    } else if (checkTaohuaSha(baziInfo, taohuaMap)) {
        return stringResource(R.string.msg_taohua_sha)
    } else if (checkTaohuaZhengguan(baziInfo, taohuaMap)) {
        return stringResource(R.string.msg_taohua_zhengguan)
    } else if (checkTaohuaQiangwai(baziInfo, taohuaMap)) {
        return stringResource(R.string.msg_taohua_qiangwai)
    } else if (checkTaohuaQiangnei(baziInfo, taohuaMap)) {
        return stringResource(R.string.msg_taohua_qiangnei)
    } else if (taohuaCount == 0) {
        return stringResource(R.string.msg_taohua_0)
    } else if (taohuaCount == 1) {
        return stringResource(R.string.msg_taohua_1)
    } else if (taohuaCount == 2) {
        return stringResource(R.string.msg_taohua_2)
    } else if (taohuaCount == 3) {
        return stringResource(R.string.msg_taohua_3)
    } else if (taohuaCount == 4) {
        return stringResource(R.string.msg_taohua_4)
    } else {
        return stringResource(R.string.msg_taohua_0)
    }
}

@Composable
fun getLiunianTaohua(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): String {
    val builder = StringBuilder()
    builder.append(stringResource(R.string.msg_taohua_liunian))
    builder.append("   ")

    var thisYearTg = baziInfo.yearTiangan
    var thisYearDz = baziInfo.yearDizhi

    var yearBase = BaziUtil().getCyclicalYearBase(
        baziInfo.birthDateYear,
        baziInfo.birthDateMonth,
        baziInfo.birthDateDay,
        baziInfo.birthHour
    )
    var yearBaseTmp = yearBase
    var year = baziInfo.birthDateYear
    for (i in 0..100) {
        yearBaseTmp = yearBase + i
        thisYearTg = BaziUtil().getTianGan(yearBaseTmp)
        thisYearDz = BaziUtil().getDiZhi(yearBaseTmp)
        if (taohuaMap.containsKey(thisYearDz)) {
            //Found Taohua liunian
            builder.append(year + i)
            builder.append("(")
            builder.append(BaziUtil().getTianGanLabel(thisYearTg))
            builder.append(BaziUtil().getDizhiLabel(thisYearDz))
            builder.append(")")
            builder.append("  ")
        }
    }
    return builder.toString()
}

fun checkTaohuaJie(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): Boolean {
    println("Check TaohuaJie")
    var owner = baziInfo.dayTiangan
    println("owner $owner")

    var tg = baziInfo.yearTiangan
    var dz = baziInfo.yearDizhi

    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getBijian(owner) || tg == BaziUtil().getJiecai(owner)) {
            //found taohua sha
            println("Found TaohuaJie on year Tiangan")
            return true
        }
    }

    tg = baziInfo.monthTiangan
    dz = baziInfo.monthDizhi
    println("owner $owner  tg=$tg  dz=$dz ")
    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getBijian(owner) || tg == BaziUtil().getJiecai(owner)) {
            println("Found TaohuaJie on month Tiangan")
            return true
        }
    }

    tg = baziInfo.dayTiangan
    dz = baziInfo.dayDizhi
    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getBijian(owner) || tg == BaziUtil().getJiecai(owner)) {
            println("Found TaohuaJie on day Tiangan")
            return true
        }
    }

    tg = baziInfo.hourTiangan
    dz = baziInfo.hourDizhi
    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getBijian(owner) || tg == BaziUtil().getJiecai(owner)) {
            println("Found TaohuaJie on hour Tiangan")
            return true
        }
    }

    return false
}

fun checkTaohuaSha(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): Boolean {
    println("Check TaohuaSha")
    var owner = baziInfo.dayTiangan
    println("owner $owner")

    var tg = baziInfo.yearTiangan
    var dz = baziInfo.yearDizhi

    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getQisha(owner)) {
            println("Found taohua sha on year Tiangan")
            return true
        }
    }

    tg = baziInfo.monthTiangan
    dz = baziInfo.monthDizhi
    println("owner $owner  tg=$tg  dz=$dz ")
    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getQisha(owner)) {
            println("Found taohua sha on month Tiangan")
            return true
        }
    }

    tg = baziInfo.dayTiangan
    dz = baziInfo.dayDizhi
    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getQisha(owner)) {
            println("Found taohua sha on day Tiangan")
            return true
        }
    }

    tg = baziInfo.hourTiangan
    dz = baziInfo.hourDizhi
    if (taohuaMap.containsKey(dz)) {
        if (tg == BaziUtil().getQisha(owner)) {
            println("Found taohua sha on hour Tiangan")
            return true
        }
    }

    return false
}

fun checkTaohuaZhengguan(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): Boolean {
    //only for female
    if (baziInfo.gender == FEMALE) {
        var owner = baziInfo.dayTiangan

        var tg = baziInfo.yearTiangan
        var dz = baziInfo.yearDizhi

        if (taohuaMap.containsKey(dz)) {
            if (tg == BaziUtil().getZhengguan(owner)) {
                return true
            }
        }
        tg = baziInfo.monthTiangan
        dz = baziInfo.monthDizhi
        if (taohuaMap.containsKey(dz)) {
            if (tg == BaziUtil().getZhengguan(owner)) {
                return true
            }
        }

        tg = baziInfo.hourTiangan
        dz = baziInfo.hourDizhi
        if (taohuaMap.containsKey(dz)) {
            if (tg == BaziUtil().getZhengguan(owner)) {
                return true
            }
        }
    }
    return false
}

fun checkTaohuaQiangwai(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): Boolean {
    var dz = baziInfo.dayDizhi

    //taohua on day
    if (taohuaMap.containsKey(dz)) {
        return true
    }

    dz = baziInfo.hourDizhi
    //taohua on hour
    if (taohuaMap.containsKey(dz)) {
        return true
    }
    return false
}

fun checkTaohuaQiangnei(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): Boolean {
    var dz = baziInfo.yearDizhi

    //taohua on year
    if (taohuaMap.containsKey(dz)) {
        return true
    }

    dz = baziInfo.monthDizhi
    //taohua on month
    if (taohuaMap.containsKey(dz)) {
        return true
    }
    return false
}

fun checkTaohuaRen(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): Boolean {
    var owner = baziInfo.dayTiangan
    var dz = baziInfo.dayDizhi

    val yangren = BaziUtil().getYangren(owner)
    //taohua on day
    if (taohuaMap.containsKey(dz)) {
        if (dz == yangren) {
            return true
        }
    }
    dz = baziInfo.hourDizhi
    if (taohuaMap.containsKey(dz)) {
        if (dz == yangren) {
            return true
        }
    }
    return false
}

fun checkGunlangTaohua(baziInfo: BaziInfo): Boolean {
    if (baziInfo.dayTiangan == TianGan.TIANGAN_BING && baziInfo.dayDizhi == DiZhi.DIZHI_ZI) {
        if (baziInfo.monthTiangan == TianGan.TIANGAN_XIN && baziInfo.monthDizhi == DiZhi.DIZHI_MOU) {
            return true
        }
        if (baziInfo.hourTiangan == TianGan.TIANGAN_XIN && baziInfo.hourDizhi == DiZhi.DIZHI_MOU) {
            return true
        }
    }

    if (baziInfo.dayTiangan == TianGan.TIANGAN_XIN && baziInfo.dayDizhi == DiZhi.DIZHI_MOU) {
        if (baziInfo.monthTiangan == TianGan.TIANGAN_BING && baziInfo.monthDizhi == DiZhi.DIZHI_ZI) {
            return true
        }
    }

    return false
}
