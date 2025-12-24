package com.rick.bazi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziMeasureUtil
import com.rick.bazi.util.BaziUtil
import com.rick.bazi.util.DiZhiUtil
import com.rick.bazi.util.GeJuUtil
import com.rick.bazi.util.TianGanUtil
import com.rick.bazi.util.WuXingUtil
import com.rick.bazi.util.YongShenUtil

@Composable
fun BaziAppAnalysisScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
//                val bullet = "\u2022" // Unicode bullet character
                val SPACE = "    "
                val analysisResult = buildAnnotatedString {

                    //1.
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        append(stringResource(R.string.app_bazi_analyze_label1)+ "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append("" + stringResource(R.string.app_bazi_str) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + BaziUtil().createBaziStringOneLine(baziInfo) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.bazi_owner) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + WuXingUtil().getOwnerString(baziInfo.baziData.dayTiangan) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_wuxing_fenbu) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + WuXingUtil().getOwnerWuXingString(baziInfo, baziModel) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_wuxing_weight_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + YongShenUtil().getWuXingWeightString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.ten_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + baziInfo.shishenYearStr + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + baziInfo.shishenMonthStr + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + baziInfo.shishenDayStr + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + baziInfo.shishenHourStr + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_tiangan_5he) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(TianGanUtil().getTianGanHeString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dizhi_3he_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DiZhiUtil().getDiZhi3HeString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dizhi_3hui_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DiZhiUtil().getDiZhi3HuiString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dizhi_6he_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DiZhiUtil().getDiZhi6HeString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dizhi_6chong_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DiZhiUtil().getDiZhi6ChongString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dizhi_6hai_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DiZhiUtil().getDiZhi6HaiString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dizhi_xing_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(DiZhiUtil().getDiZhiXingString(baziInfo.baziData) + "\n")
                    }


                    //2
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        append(stringResource(R.string.app_bazi_analyze_label2)+ "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_deling_label) + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + WuXingUtil().getDangLingStr(baziInfo, baziModel) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dedi_label) + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + WuXingUtil().getDeDiDescription(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_dezhu_label) + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(BaziMeasureUtil().getHelpElementString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_kexiehao_label) + " ")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(BaziMeasureUtil().getKeXieHaoString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_strength_label) + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + BaziMeasureUtil().getBaziStrengthSummary(baziInfo, baziModel) + "\n")
                    }

                    //3
                    var gj = GeJuUtil().getGJ(baziInfo.baziData)
                    //calculate GeJu xiji stat
//                    GeJuUtil().getGeJuXiJi(gj, baziInfo.baziData)
//                    YongShenUtil().calculateTiaoHouYongShen(baziInfo.baziData)
//                    YongShenUtil().calculateTongGuanShen(baziInfo.baziData)

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        append(stringResource(R.string.app_bazi_analyze_label3)+ "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getGJText(gj) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_analysis) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getGeJuAnalysis(gj, baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_personality) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getGJSummary(gj) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_job) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getJobDescription(gj) + "\n")
                    }

                    //4
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                        append(stringResource(R.string.app_bazi_analyze_label4)+ "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_yong_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getYongShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_xi_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getXiShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_gj_ji_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + GeJuUtil().getJiShenString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_tiaohou_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + YongShenUtil().getTiaohouString(baziInfo.baziData) + "\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_yongshen_tongguan_label) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
                        append(SPACE + YongShenUtil().getTongGuanYongshenString(baziInfo.baziData) + "\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                        append(stringResource(R.string.app_bazi_final_yong_shen) + ":\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
//                        append(SPACE + BaziMeasureUtil().getBaziYongshenSummary(baziInfo.baziData) + "\n")
                        append(YongShenUtil().getYongshenString(baziInfo.baziData) )
                    }


//                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
//                        append(stringResource(R.string.app_bazi_str) + ":")
//                    }

//                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
//                        append(BaziMeasureUtil().getXiJiSummary(baziInfo, baziModel) + "\n")
//                    }

//                    //5
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
//                        append(stringResource(R.string.app_bazi_analyze_label5)+ "\n")
//                    }
////                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
////                        append(stringResource(R.string.app_bazi_dayun) + ":")
////                    }
//                    withStyle(style = SpanStyle(fontSize = 18.sp)) {
//                        append(BaziMeasureUtil().getBaziDayunSummary(baziInfo, baziModel) + "\n")
//                    }
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
fun BaziAppAnalysisScreenPreview() {
    BaziTheme {
        BaziVersionScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}