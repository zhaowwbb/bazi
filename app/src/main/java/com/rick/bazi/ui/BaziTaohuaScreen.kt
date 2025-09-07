package com.rick.bazi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.FEMALE
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val birthDateStr = BaziUtil().getBirthDateLabel(baziInfo)
        val baziTaohuaLable = stringResource(R.string.app_taohua_bazi)
        val dayunTaohuaLable = stringResource(R.string.app_taohua_dayun)
        val liunianTaohuaLable = stringResource(R.string.app_taohua_liunian)

        var taohuaMap : Map<DiZhi, DiZhi> = getTaohuaMap(baziInfo)
        var taohuaCount = getTaohuaCount(taohuaMap, baziInfo)

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
                    text = getTaohuaIntroduction(),
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
                    text = "$baziTaohuaLable",
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
                    text = getBaziTaohuaResult(baziInfo, taohuaMap, taohuaCount),
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
                    text = "$dayunTaohuaLable",
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
                    text = BaziUtil().getDayunTaohuaResult(baziInfo, taohuaMap),
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
                    text = "$liunianTaohuaLable",
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
                    text = getLiunianTaohua(baziInfo, taohuaMap),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineSmall
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
                HorizontalDivider(thickness = 2.dp)
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

fun getTaohuaMap(baziInfo: BaziInfo) : Map<DiZhi, DiZhi>{
    var taohuaMap = mutableMapOf<DiZhi, DiZhi>()
    var yDz = baziInfo.yearDizhi
    var dDz = baziInfo.dayDizhi

    //group one
    if (yDz == DiZhi.DIZHI_YIN || yDz == DiZhi.DIZHI_WU || yDz == DiZhi.DIZHI_XU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_MOU)) {
            taohuaMap.put(DiZhi.DIZHI_MOU, DiZhi.DIZHI_MOU)
        }
    }
    if (dDz == DiZhi.DIZHI_YIN || dDz == DiZhi.DIZHI_WU || dDz == DiZhi.DIZHI_XU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_MOU)) {
            taohuaMap.put(DiZhi.DIZHI_MOU, DiZhi.DIZHI_MOU)
        }
    }

    //group two
    if (yDz == DiZhi.DIZHI_SHEN || yDz == DiZhi.DIZHI_ZI || yDz == DiZhi.DIZHI_CHEN) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_YOU)) {
            taohuaMap.put(DiZhi.DIZHI_YOU, DiZhi.DIZHI_YOU)
        }
    }
    if (dDz == DiZhi.DIZHI_SHEN || dDz == DiZhi.DIZHI_ZI || dDz == DiZhi.DIZHI_CHEN) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_YOU)) {
            taohuaMap.put(DiZhi.DIZHI_YOU, DiZhi.DIZHI_YOU)
        }
    }

    //group three
    if (yDz == DiZhi.DIZHI_HAI || yDz == DiZhi.DIZHI_MOU || yDz == DiZhi.DIZHI_WEI) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_ZI)) {
            taohuaMap.put(DiZhi.DIZHI_ZI, DiZhi.DIZHI_ZI)
        }
    }
    if (dDz == DiZhi.DIZHI_HAI || dDz == DiZhi.DIZHI_MOU || dDz == DiZhi.DIZHI_WEI) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_ZI)) {
            taohuaMap.put(DiZhi.DIZHI_ZI, DiZhi.DIZHI_ZI)
        }
    }

    //group four
    if (yDz == DiZhi.DIZHI_SI || yDz == DiZhi.DIZHI_YOU || yDz == DiZhi.DIZHI_CHOU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_WU)) {
            taohuaMap.put(DiZhi.DIZHI_WU, DiZhi.DIZHI_WU)
        }
    }
    if (dDz == DiZhi.DIZHI_SI || dDz == DiZhi.DIZHI_YOU || dDz == DiZhi.DIZHI_CHOU) {
        if (!taohuaMap.containsKey(DiZhi.DIZHI_WU)) {
            taohuaMap.put(DiZhi.DIZHI_WU, DiZhi.DIZHI_WU)
        }
    }
    return taohuaMap
}

fun getTaohuaCount(taohuaMap : Map<DiZhi, DiZhi>, baziInfo: BaziInfo) : Int{
    var taohuaCount = 0

    if(taohuaMap.containsKey(baziInfo.yearDizhi)){
        taohuaCount += 1
    }

    if(taohuaMap.containsKey(baziInfo.monthDizhi)){
        taohuaCount += 1
    }

    if(taohuaMap.containsKey(baziInfo.dayDizhi)){
        taohuaCount += 1
    }

    if(taohuaMap.containsKey(baziInfo.hourDizhi)){
        taohuaCount += 1
    }
    return taohuaCount
}

fun getDizhiSiCount(baziInfo: BaziInfo) : Int{
    var count = 0
    if(baziInfo.yearDizhi == DiZhi.DIZHI_SI){
        count = count + 1
    }

    if(baziInfo.monthDizhi == DiZhi.DIZHI_SI){
        count = count + 1
    }

    if(baziInfo.dayDizhi == DiZhi.DIZHI_SI){
        count = count + 1
    }

    if(baziInfo.hourDizhi == DiZhi.DIZHI_SI){
        count = count + 1
    }
    return count
}

fun getDizhiHaiCount(baziInfo: BaziInfo) : Int{
    var count = 0
    if(baziInfo.yearDizhi == DiZhi.DIZHI_HAI){
        count = count + 1
    }

    if(baziInfo.monthDizhi == DiZhi.DIZHI_HAI){
        count = count + 1
    }

    if(baziInfo.dayDizhi == DiZhi.DIZHI_HAI){
        count = count + 1
    }

    if(baziInfo.hourDizhi == DiZhi.DIZHI_HAI){
        count = count + 1
    }
    return count
}

@Composable
fun getBaziTaohuaResult(
    baziInfo: BaziInfo,
    taohuaMap: Map<DiZhi, DiZhi>,
    taohuaCount: Int
): String {
    val builder = StringBuilder()
    var isTaohua: Boolean = false

    if(BaziUtil().isLady(baziInfo)){
        var haiCount = getDizhiHaiCount(baziInfo)
        if(haiCount == 1){
            builder.append(stringResource(R.string.msg_lady_looking1))
            builder.append("\n")
        }
        if(haiCount == 2){
            builder.append(stringResource(R.string.msg_lady_looking2))
            builder.append("\n")
        }
        if(haiCount == 3){
            builder.append(stringResource(R.string.msg_lady_looking3))
            builder.append("\n")
        }
        if(haiCount == 4){
            builder.append(stringResource(R.string.msg_lady_looking4))
            builder.append("\n")
        }
        var siCount = getDizhiSiCount(baziInfo)
        if(siCount == 1){
            builder.append(stringResource(R.string.msg_lady_se1))
            builder.append("\n")
        }
        if(siCount == 2){
            builder.append(stringResource(R.string.msg_lady_se2))
            builder.append("\n")
        }
        if(siCount == 3){
            builder.append(stringResource(R.string.msg_lady_se3))
            builder.append("\n")
        }
        if(siCount == 4){
            builder.append(stringResource(R.string.msg_lady_se4))
            builder.append("\n")
        }
    }

    if (checkBianyeTaohua(baziInfo)) {
        builder.append(stringResource(R.string.msg_taohua_bianye))
        builder.append("\n")
        isTaohua = true
    }
    if (checkLuoxingTaohua(baziInfo)) {
        builder.append(stringResource(R.string.msg_taohua_luoxing))
        builder.append("\n")
        isTaohua = true
    }
    if (checkGunlangTaohua(baziInfo)) {
        builder.append(stringResource(R.string.msg_taohua_gunlang))
        builder.append("\n")
        isTaohua = true
    }
    if (checkTaohuaRen(baziInfo, taohuaMap)) {
        builder.append(stringResource(R.string.msg_taohua_ren))
        builder.append("\n")
        isTaohua = true
    }
    if (checkTaohuaJie(baziInfo, taohuaMap)) {
        builder.append(stringResource(R.string.msg_taohua_jie))
        builder.append("\n")
        isTaohua = true
    }
    if (checkTaohuaSha(baziInfo, taohuaMap)) {
        builder.append(stringResource(R.string.msg_taohua_sha))
        builder.append("\n")
        isTaohua = true
    }
    if (checkTaohuaZhengguan(baziInfo, taohuaMap)) {
        builder.append(stringResource(R.string.msg_taohua_zhengguan))
        builder.append("\n")
        isTaohua = true
    }
    if (checkTaohuaQiangwai(baziInfo, taohuaMap)) {
        builder.append(stringResource(R.string.msg_taohua_qiangwai))
        builder.append("\n")
        isTaohua = true
    }
    if (checkTaohuaQiangnei(baziInfo, taohuaMap)) {
        builder.append(stringResource(R.string.msg_taohua_qiangnei))
        builder.append("\n")
        isTaohua = true
    }
    if (checkRizhuTaohua(baziInfo)) {
        builder.append(stringResource(R.string.msg_taohua_rizhu))
        builder.append("\n")
        isTaohua = true
    }
    println("taohuaMap=$taohuaMap  taohuaCount=$taohuaCount")

    if (taohuaCount == 0) {
        if(!isTaohua) {
            builder.append(stringResource(R.string.msg_taohua_0))
        }
    }
    if (taohuaCount == 1) {
        builder.append(stringResource(R.string.msg_taohua_1))
    }
    if (taohuaCount == 2) {
        builder.append(stringResource(R.string.msg_taohua_2))
    }
    if (taohuaCount == 3) {
        builder.append(stringResource(R.string.msg_taohua_3))
    }
    if (taohuaCount == 4) {
        builder.append(stringResource(R.string.msg_taohua_4))
    }
    return builder.toString()
}

@Composable
fun getLiunianTaohua(baziInfo: BaziInfo, taohuaMap: Map<DiZhi, DiZhi>): String {
    val builder = StringBuilder()

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
    var yearUnit = stringResource(R.string.age_unit)
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
            builder.append(" ")
            builder.append(i + 1)
            builder.append(yearUnit)
            builder.append("\n")
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
//    println("Check TaohuaSha")
    var owner = baziInfo.dayTiangan
//    println("owner $owner")

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

@Composable
fun getTaohuaIntroduction(): String {
//    return stringResource(R.string.msg_taohua_intro)
    return stringResource(R.string.msg_taohua_brief)
}

fun checkRizhuTaohua(baziInfo: BaziInfo): Boolean {
    var isRizhuTaohua = false
    val tg = baziInfo.dayTiangan
    val dz = baziInfo.dayDizhi

    if(tg == TianGan.TIANGAN_JIA && dz == DiZhi.DIZHI_ZI){
        return true
    }
    if(tg == TianGan.TIANGAN_YI && dz == DiZhi.DIZHI_SI){
        return true
    }
    if(tg == TianGan.TIANGAN_GENG && dz == DiZhi.DIZHI_WU){
        return true
    }
    if(tg == TianGan.TIANGAN_XIN && dz == DiZhi.DIZHI_HAI){
        return true
    }
    if(tg == TianGan.TIANGAN_JIA && dz == DiZhi.DIZHI_WU){
        return true
    }
    if(tg == TianGan.TIANGAN_GENG && dz == DiZhi.DIZHI_ZI){
        return true
    }
    if(tg == TianGan.TIANGAN_GUI && dz == DiZhi.DIZHI_HAI){
        return true
    }
    return isRizhuTaohua
}

