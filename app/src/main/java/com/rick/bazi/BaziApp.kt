package com.rick.bazi

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rick.bazi.data.DataSource
import com.rick.bazi.ui.BaziAppIntroductionScreen
import com.rick.bazi.ui.BaziConfirmPrivacyScreen
import com.rick.bazi.ui.BaziPaipanScreen
import com.rick.bazi.ui.BaziSampleScreen
import com.rick.bazi.ui.BaziStartScreen
import com.rick.bazi.ui.BaziSummaryScreen
import com.rick.bazi.ui.BaziTianKeDiChongScreen
import com.rick.bazi.ui.BaziVersionScreen
import com.rick.bazi.ui.BaziViewModel
import com.rick.bazi.ui.BaziDayunScreen
import com.rick.bazi.ui.BaziPrivacyPolicyScreen
import com.rick.bazi.ui.BaziUserAgreementScreen
import com.rick.bazi.ui.BaziYimaScreen

enum class BaziScreen(@StringRes val title: Int) {
    ConfirmPrivacy(title = R.string.app_bazi_confirm_privacy),
    UserAgreement(title = R.string.app_bazi_user_agreement),
    PrivacyPolicy(title = R.string.app_bazi_privacy_policy),
    Start(title = R.string.app_bazi_title),
    Paipan(title = R.string.app_bazi_paipan),
    Sample(title = R.string.app_bazi_sample),
    Version(title = R.string.app_bazi_version),
    Introduction(title = R.string.app_introduction),
    TianKeDiChong(title = R.string.bazi_tkdc),
    DaYun(title = R.string.app_bazi_dayun),
    YiMa(title = R.string.app_bazi_yima),
    Summary(title = R.string.app_bazi_result)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaziAppBar(
    currentScreen: BaziScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BaziApp(
    baziModel: BaziViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = BaziScreen.valueOf(
        backStackEntry?.destination?.route ?: BaziScreen.Start.name
    )

    Scaffold(
        topBar = {
            BaziAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val baziUIInfo by baziModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = BaziScreen.ConfirmPrivacy.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {


            composable(route = BaziScreen.ConfirmPrivacy.name) {
                BaziConfirmPrivacyScreen(
                    onNextButtonClicked = {
                        navController.navigate(BaziScreen.Paipan.name)
                    },
                    navController = navController,
                    baziInfo = baziUIInfo,
                    baziModel = baziModel,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = BaziScreen.UserAgreement.name) {
                BaziUserAgreementScreen(
                    onNextButtonClicked = {
                        navController.navigate(BaziScreen.Paipan.name)
                    },
                    navController = navController,
                    baziInfo = baziUIInfo,
                    baziModel = baziModel,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }


            composable(route = BaziScreen.PrivacyPolicy.name) {
                BaziPrivacyPolicyScreen(
                    onNextButtonClicked = {
                        navController.navigate(BaziScreen.Paipan.name)
                    },
                    navController = navController,
                    baziInfo = baziUIInfo,
                    baziModel = baziModel,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }



            composable(route = BaziScreen.Start.name) {
                BaziStartScreen(
                    onNextButtonClicked = {
                        navController.navigate(BaziScreen.Paipan.name)
                    },
                    navController = navController,
                    baziInfo = baziUIInfo,
                    baziModel = baziModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = BaziScreen.Paipan.name) {
                BaziPaipanScreen(
                    subtotal = "13.0",
                    onNextButtonClicked = { navController.navigate(BaziScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    options = DataSource.strList,
                    onSelectionChanged = { //viewModel.setDate(it)
                        },
                    navController = navController,
                    baziModel = baziModel,
                    baziInfo = baziUIInfo,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.Summary.name) {
                val context = LocalContext.current
                BaziSummaryScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.Sample.name) {
                val context = LocalContext.current
                BaziSampleScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.Version.name) {
                val context = LocalContext.current
                BaziVersionScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.Introduction.name) {
                BaziAppIntroductionScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.TianKeDiChong.name) {
                BaziTianKeDiChongScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.DaYun.name) {
                BaziDayunScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    baziModel = baziModel,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = BaziScreen.YiMa.name) {
                BaziYimaScreen(
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(baziModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String -> {}

                    },
                    baziInfo = baziUIInfo,
                    baziModel = baziModel,
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: BaziViewModel,
    navController: NavHostController
) {
//    viewModel.resetOrder()
    navController.popBackStack(BaziScreen.Start.name, inclusive = false)
}