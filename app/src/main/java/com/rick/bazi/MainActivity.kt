package com.rick.bazi

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rick.bazi.ui.theme.BaziTheme
import com.rick.bazi.util.DateUtils
import java.util.Calendar
import com.rick.bazi.BaziApp
import com.rick.bazi.data.MALE

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaziTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BaziApp(
//                        name = "Rick",
//                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BaziTestApp(name: String, modifier: Modifier = Modifier) {



    var selectedValue by rememberSaveable { mutableStateOf(MALE) }
    val options: List<String> = listOf("AA", "BB")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    painter = painterResource(R.drawable.you_believe_science),
                    contentDescription = null
                )
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.background(Color.Gray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selectedValue == "Male",
                    onClick = {selectedValue = "Male"}
                )
                Text(text = stringResource(R.string.app_bazi_male))
                RadioButton(
                    selectedValue == "Female",
                    onClick = {selectedValue = "Female"}
                )
                Text(text = stringResource(R.string.app_bazi_female))
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.app_bazi_birth_date))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.pick))
                }

                Text(
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    text = "YYYY-MM-DD")
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.app_bazi_birth_time))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.pick))
                }

                Text(
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    text = "MM-SS")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { }
            ) {
                Text(stringResource(R.string.app_bazi_paipan))
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.baizisuanming),
                    contentDescription = null
                )
            }
        }
    }

}

