package com.putriwidianingsih607062330057.konversisuhu.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material3.Button
import com.putriwidianingsih607062330057.konversisuhu.R
import com.putriwidianingsih607062330057.konversisuhu.navigation.Screen
import com.putriwidianingsih607062330057.konversisuhu.ui.theme.KonversiSuhuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ){ innerPadding ->
        ScreenContent(Modifier.padding((innerPadding)))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var inputSuhu by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }

    var awal by rememberSaveable { mutableStateOf("") }
    var tujuan by rememberSaveable { mutableStateOf("") }

    var error by rememberSaveable { mutableStateOf(false) }

    val sameInputError = stringResource(id = R.string.same_input_error)
    val inputInvalidError = stringResource(id = R.string.input_angka)
    val inputKosong = stringResource(id = R.string.input_invalid)

    val units = listOf("Celsius", "Fahrenheit", "Kelvin")

    Column(
        modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = inputSuhu,
            onValueChange = {inputSuhu = it},
            label = { Text(stringResource(id = R.string.input_suhu)) }
        )
        DropdownMenuCustom(label = stringResource(R.string.awal), selectedOption = awal, options = units) { awal = it }
        DropdownMenuCustom(label = stringResource(R.string.tujuan), selectedOption = tujuan, options = units) { tujuan = it }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Button(onClick = {
                error = false
                var errorMessage: String? = null

                if (awal == tujuan){
                    errorMessage = sameInputError
                } else {
                    val value = inputSuhu.toDoubleOrNull()
                    if (value != null){
                        result = convertTemperature(value, awal, tujuan)
                        error = false
                    } else if (inputSuhu.isBlank()){
                        errorMessage = inputKosong
                    } else {
                        errorMessage = inputInvalidError
                    }
                }

                if (errorMessage != null) {
                    error = true
                    result = errorMessage
                }
            }) {
                Text(
                    text = stringResource(R.string.hitung),
                    modifier = Modifier.padding(8.dp),
                    color = Color.Black,
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center

        ){
            Spacer(modifier = Modifier.height(10.dp))
            if (error && result.isNotEmpty()) {
                Text(
                    text = result,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }else if (result.isNotEmpty()){
                Text(
                    text = result,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

        }
    }

}

@Composable
fun DropdownMenuCustom(label: String, selectedOption: String, options: List<String>, onOptionSelected: (String) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(
            onClick = {expanded = true}
        ) {
            Text("$label: $selectedOption")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}

private fun convertTemperature(value: Double, awal: String, tujuan: String): String {
    val  celsius = when (awal) {
        "Celsius" -> value
        "Fahrenheit" -> (value - 32) * 5 / 9
        "Kelvin" -> value - 273.15
        else -> return "Pilihan suhu awal tidak valid!"
    }

    val konversi = when (tujuan) {
        "Celsius" -> celsius
        "Fahrenheit" -> (celsius * 9.0 / 5.0) + 32.0
        "Kelvin" -> celsius + 273.15
        else -> return "Pilihan suhu tujuan tidak valid!"
    }
    return "$value $awal \n = \n ${"%.2f".format(konversi)} $tujuan"
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    KonversiSuhuTheme {
        MainScreen(rememberNavController())
    }
}