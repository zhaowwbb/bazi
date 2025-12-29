package com.rick.bazi.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.ConstUtil
import com.rick.bazi.util.DaYunUtil
//import com.rick.bazi.util.getDizhiStr
import com.rick.bazi.util.DaYunUtil.DayunRecord
import com.rick.bazi.util.DiZhiUtil
import com.rick.bazi.util.TaoHuaUtil
import com.rick.bazi.util.YiMaUtil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziYimaScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    baziModel: BaziViewModel,
    modifier: Modifier = Modifier
) {

    var data = baziInfo.baziData

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
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = BaziUtil().getBirthDateLabel(baziInfo),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }

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
                        append(stringResource(R.string.yima_intro_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + stringResource(R.string.app_bazi_yima_intro) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_yima_bazi) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + YiMaUtil().getHorseString(baziInfo) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_yima_dayun) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(YiMaUtil().getDaYunYimaString(baziInfo))
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_yima_liunian) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(YiMaUtil().getLiuNianYimaString(baziInfo))
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BaziYimaScreenPreview() {
    BaziTheme {
        BaziYimaScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            modifier = Modifier.fillMaxHeight()
        )
    }
}