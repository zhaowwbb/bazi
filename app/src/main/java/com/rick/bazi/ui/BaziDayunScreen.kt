package com.rick.bazi.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziMeasureUtil
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.ConstUtil
import com.rick.bazi.util.ConstUtil.Companion
import com.rick.bazi.util.DaYunUtil
import com.rick.bazi.util.GeJuUtil
import com.rick.bazi.util.WuXingUtil
import com.rick.bazi.util.YongShenUtil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziDayunScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    baziModel: BaziViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
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
                    text = "${birthDateStr}",
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
//                val SPACE = "    "
                val analysisResult = buildAnnotatedString {

                    //1.
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
//                        append(stringResource(R.string.app_bazi_analyze_label1) + "\n")
//                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append("" + stringResource(R.string.app_bazi_str) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + BaziUtil().createBaziStringOneLine(baziInfo) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_wuxing_fenbu) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + WuXingUtil().getOwnerWuXingString(baziInfo, baziModel) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_wuxing_weight_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + YongShenUtil().getWuXingWeightString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_dayun_start_time) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + DaYunUtil().getDaYunStartTimeString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dayun_intro_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + stringResource(R.string.app_bazi_dayun_intro) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_yong_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + GeJuUtil().getYongShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_xi_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + GeJuUtil().getXiShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_ji_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + GeJuUtil().getJiShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_tiaohou_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(ConstUtil.SPACE + YongShenUtil().getTiaohouString(baziInfo.baziData) + "\n")
                    }
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
//                        append(stringResource(R.string.app_bazi_yongshen_tongguan_label) + ":\n")
//                    }
//                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
//                        append(SPACE + YongShenUtil().getTongGuanYongshenString(baziInfo.baziData) + "\n")
//                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_final_yong_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
//                        append(SPACE + BaziMeasureUtil().getBaziYongshenSummary(baziInfo.baziData) + "\n")
                        append(YongShenUtil().getYongshenString(baziInfo.baziData) )
                    }

                    //5
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_analyze_label5)+ "\n")
                    }
//                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
//                        append(stringResource(R.string.app_bazi_dayun) + ":")
//                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DaYunUtil().getDaYunSummaryString(baziInfo.baziData) + "\n")
                    }
                }

                Text(
                    text = analysisResult,
                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
//                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BaziDayunScreenPreview() {
    BaziTheme {
        BaziDayunScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            baziModel = viewModel(),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

