package com.rick.bazi.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rick.bazi.R
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.ui.theme.BaziTheme

// 行业常用配色
private val PrimaryBlue = Color(0xFF1976D2)
private val LightBlue = Color(0xFFE3F2FD)
private val DarkBlue = Color(0xFF1565C0)
private val SuccessGreen = Color(0xFF4CAF50)
private val WarningOrange = Color(0xFFFF9800)
private val BackgroundGray = Color(0xFFF5F5F5)
private val CardWhite = Color(0xFFFFFFFF)
private val TextPrimary = Color(0xFF212121)
private val TextSecondary = Color(0xFF757575)

@Composable
fun BaziPrivacyPolicyScreen(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    onNextButtonClicked: () -> Unit = {},
    navController: NavHostController,
    baziModel: BaziViewModel,
    baziInfo: BaziInfo,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        // 顶部标题栏
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = PrimaryBlue,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PrivacyTip,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.app_bazi_privacy_policy),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // 主要内容区域 - 可滚动
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 安全承诺卡片
            PrivacyFeatureCard(
                icon = Icons.Default.Security,
                title = "数据安全保障",
                description = "您的个人信息将采用行业标准加密技术进行传输和存储"
            )

            // 声明内容卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 声明标题
                    Text(
                        text = "隐私政策声明",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryBlue
                    )

                    HorizontalDivider(color = LightBlue, thickness = 1.dp)

                    // 声明内容
                    Text(
                        text = stringResource(R.string.app_bazi_privacy_policy_content),
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = TextPrimary,
                        textAlign = TextAlign.Start
                    )
                }
            }

            // 承诺要点列表
            CommitmentSection()

            // 底部提示
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightBlue.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "继续使用即表示您同意以上隐私条款，我们将严格遵守相关法律法规保护您的权益。",
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // 底部操作按钮
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = CardWhite
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 取消按钮
                val activity = (LocalContext.current as? Activity)
                OutlinedButton(
                    onClick = { activity?.finish() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextSecondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "拒绝",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // 同意按钮
                Button(
                    onClick = { onNextButtonClicked() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "同意并继续",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun PrivacyFeatureCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = LightBlue,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun CommitmentSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "我们的承诺",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue
            )

            HorizontalDivider(color = LightBlue, thickness = 1.dp)

            CommitmentItem(
                number = "01",
                text = "收集信息仅用于八字排盘和运势分析"
            )
            CommitmentItem(
                number = "02",
                text = "不会将您的个人信息分享给第三方"
            )
            CommitmentItem(
                number = "03",
                text = "您可以随时申请删除您的账户和数据"
            )
            CommitmentItem(
                number = "04",
                text = "采用加密传输，保障数据传输安全"
            )
        }
    }
}

@Composable
private fun CommitmentItem(
    number: String,
    text: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = PrimaryBlue.copy(alpha = 0.1f),
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun BaziPrivacyPolicyScreenPreview() {
    BaziTheme {
        BaziPrivacyPolicyScreen(
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            onNextButtonClicked = {},
            navController = rememberNavController(),
            baziModel = viewModel(),
            baziInfo = BaziInfo(name = ""),
            modifier = Modifier.fillMaxHeight()
        )
    }
}