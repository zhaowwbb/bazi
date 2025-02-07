package com.rick.bazi

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.TianGan

@Composable
fun xxxxx(
    baziInfo: BaziInfo,
    tiangan : TianGan
): String{
    val tianganStrMap : Map<TianGan, Int> = baziInfo.tianganStrMap
    var tianganStrKey = tianganStrMap.getValue(tiangan)
    val str = stringResource(tianganStrKey)
//        val str = baziInfo.tianganStrMap.get(baziInfo.monthTiangan)
//            ?.let { stringResource(it) }
    return str
//    return ""
}
