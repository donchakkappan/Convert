package com.allutils.feature_scanner.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.allutils.app_style_guide.animations.LottieAssetLoader
import com.allutils.feature_scanner.R

@Composable
internal fun ScannerHome(scannerViewModel: ScannerViewModel) {

    val state = scannerViewModel.state.collectAsState()

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        ) {
            LottieAssetLoader(R.raw.scan)
            Button(
                onClick = {
                    scannerViewModel.startScanning()
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.scan_now),
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                )
            }
            Text(
                text = state.value.scannedData ,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
