package com.rick.bazi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.BaziUtil

@Composable
fun BaziSampleScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {

    data class BaziSample(val nameId: Int, val baziDateStrId: Int)

    val samples = listOf(
        BaziSample(R.string.sample_bianyetaohua_label, R.string.sample_bianyetaohua_date),
        BaziSample(R.string.sample_0taohua_label, R.string.sample_0taohua_date),
        BaziSample(R.string.sample_1taohua_label, R.string.sample_1taohua_date),
        BaziSample(R.string.sample_2taohua_label, R.string.sample_2taohua_date),
        BaziSample(R.string.sample_luoxingtaohua_label, R.string.sample_luoxingtaohua_date),
        BaziSample(R.string.sample_taohuajie_label, R.string.sample_taohuajie_date),
        BaziSample(R.string.sample_taohuasha_label, R.string.sample_taohuasha_date),

        BaziSample(R.string.sample_zhengguantaohua_label, R.string.sample_zhengguantaohua_date),
        BaziSample(R.string.sample_qiangwaitaohua_label, R.string.sample_qiangwaitaohua_date),
        BaziSample(R.string.sample_qiangneitaohua_label, R.string.sample_qiangneitaohua_date),
        BaziSample(R.string.sample_taohuaren_label, R.string.sample_taohuaren_date),
        BaziSample(R.string.sample_taohuagunlang_label, R.string.sample_taohuagunlang_date),
        BaziSample(R.string.sample_taohuarizhu_label, R.string.sample_taohuarizhu_date),
        BaziSample(R.string.sample_jueyanhai1_label, R.string.sample_jueyanhai1_date),
        BaziSample(R.string.sample_jueyanhai2_label, R.string.sample_jueyanhai2_date),
        BaziSample(R.string.sample_jueyanhai3_label, R.string.sample_jueyanhai3_date),
        BaziSample(R.string.sample_jueyanhai4_label, R.string.sample_jueyanhai4_date),

        BaziSample(R.string.sample_taiyiduoyinsi1_label, R.string.sample_taiyiduoyinsi1_date),
        BaziSample(R.string.sample_taiyiduoyinsi2_label, R.string.sample_taiyiduoyinsi2_date),
        BaziSample(R.string.sample_taiyiduoyinsi3_label, R.string.sample_taiyiduoyinsi3_date),
        BaziSample(R.string.sample_taiyiduoyinsi4_label, R.string.sample_taiyiduoyinsi4_date),
        BaziSample(R.string.sample_dmjy_3hai1si_label, R.string.sample_dmjy_3hai1si_date)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Row(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    textAlign = TextAlign.Left,
//                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
//                    style = MaterialTheme.typography.headlineMedium,
//                    text = stringResource(R.string.bazi_sample_introduction)
//                )
//            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight(500),
                    style = MaterialTheme.typography.headlineLarge,
                    text = stringResource(R.string.app_bazi_sample)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(thickness = 2.dp)
            }

            samples.forEach { sample ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Left,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(sample.nameId)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(sample.baziDateStrId)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(thickness = 2.dp)
                }
            }


//            Row(
//                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    textAlign = TextAlign.Left,
//                    modifier = Modifier.weight(1f),
//                    fontWeight = FontWeight(500),
//                    style = MaterialTheme.typography.headlineSmall,
//                    text = stringResource(R.string.taohua_sample_list)
//                )
//            }
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
    }
}

@Preview
@Composable
fun BaziSamplePreview() {
    BaziTheme {
        BaziSampleScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}