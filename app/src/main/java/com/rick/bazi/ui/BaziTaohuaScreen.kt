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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
import com.rick.bazi.util.ConstUtil
import com.rick.bazi.util.ShiShenUtil
import com.rick.bazi.util.TaoHuaUtil

@Composable
fun BaziTaoHuaScreen(
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
        val taohuaMap : Map<DiZhi, DiZhi> = TaoHuaUtil().getTaohuaMap(baziInfo)
        val taohuaCount = TaoHuaUtil().getTaohuaCount(taohuaMap, baziInfo)

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
                    fontWeight = FontWeight.Medium,
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
                val analysisResult = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append("" + stringResource(R.string.app_bazi_str) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + BaziUtil().createBaziStringOneLine(baziInfo) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.taohua_intro_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + TaoHuaUtil().getTaohuaIntroduction() + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_taohua_bazi) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + TaoHuaUtil().getBaziTaohuaResult(baziInfo, taohuaMap, taohuaCount) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_taohua_dayun) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(TaoHuaUtil().getDayunTaohuaResult(baziInfo, taohuaMap))
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_taohua_liunian) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(TaoHuaUtil().getLiunianTaohua(baziInfo, taohuaMap))
                    }

                }
                    Text(
                        modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
                        text = analysisResult
                    )
            }
        }
    }
}

@Preview
@Composable
fun BaziTaoHuaPreview() {
    BaziTheme {
        BaziTaoHuaScreen(
//            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}
