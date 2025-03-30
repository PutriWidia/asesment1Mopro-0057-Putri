package com.putriwidianingsih607062330057.konversisuhu.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.putriwidianingsih607062330057.konversisuhu.R
import com.putriwidianingsih607062330057.konversisuhu.model.Termometer
import com.putriwidianingsih607062330057.konversisuhu.ui.theme.KonversiSuhuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ){ innerPadding ->
        AboutContent(termometer = Termometer(R.drawable.termo), Modifier.padding(innerPadding))
    }
}

@Composable
fun AboutContent(termometer: Termometer, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = termometer.imageResId),
            contentDescription = "",
            modifier = Modifier.size(350.dp)
        );
        Text(text = stringResource(R.string.tentang_aplikasi))
    }
}

//fun AboutImage(termometer: Termometer, modifier : Modifier = Modifier) {
//    Column(
//        modifier.fillMaxSize().padding(16.dp)
//    ) {
//        Text(text = stringResource(R.string.tentang_aplikasi)
//        )
//    }
//}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AboutScreenPreview(){
    KonversiSuhuTheme {
        AboutScreen(rememberNavController())
    }
}