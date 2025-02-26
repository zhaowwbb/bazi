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
import androidx.compose.ui.res.dimensionResource
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
fun BaziVersionScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    data class Version(val versionId: Int, val releaseDateId: Int, val releaseDetails: List<Int>)

    val versions = listOf(
        Version(
            R.string.bz_version_1_0,
            R.string.bz_version_1_0_time,
            listOf(R.string.bz_version_1_0_item1, R.string.bz_version_1_0_item2)
        ),
        Version(
            R.string.bz_version_1_2,
            R.string.bz_version_1_2_time,
            listOf(
                R.string.bz_version_1_2_item1,
                R.string.bz_version_1_2_item2,
                R.string.bz_version_1_2_item3,
                R.string.bz_version_1_2_item4,
                R.string.bz_version_1_2_item5
            )
        ),
        Version(
            R.string.bz_version_1_3,
            R.string.bz_version_1_3_time,
            listOf(
                R.string.bz_version_1_3_item1,
                R.string.bz_version_1_3_item2,
                R.string.bz_version_1_3_item3,
                R.string.bz_version_1_3_item4,
                R.string.bz_version_1_3_item5
            )
        )
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            versions.forEach { version ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(thickness = 2.dp)
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        textAlign = TextAlign.Left,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineMedium,
                        text = stringResource(version.versionId)
                    )
                    Text(
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight(500),
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(version.releaseDateId)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(thickness = 2.dp)
                }
                version.releaseDetails.forEach { detail ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            textAlign = TextAlign.Left,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight(500),
                            style = MaterialTheme.typography.headlineSmall,
                            text = stringResource(detail)
                        )
                    }
                }
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
fun BaziVersionPreview() {
    BaziTheme {
        BaziVersionScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}