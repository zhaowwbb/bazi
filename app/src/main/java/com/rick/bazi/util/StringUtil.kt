package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan

@Composable
fun getTianganStr(
    baziInfo: BaziInfo,
    tiangan: TianGan
): String {
    val tianganStrMap: Map<TianGan, Int> = baziInfo.tianganStrMap
    var tianganStrKey = tianganStrMap.getValue(tiangan)
    val str = stringResource(tianganStrKey)
    return str
}

@Composable
fun getDizhiStr(
    baziInfo: BaziInfo,
    diZhi: DiZhi
): String {
    val dizhiStrMap: Map<DiZhi, Int> = baziInfo.dizhiStrMap
    var dizhiStrKey = dizhiStrMap.getValue(diZhi)
    val str = stringResource(dizhiStrKey)
    return str
}
