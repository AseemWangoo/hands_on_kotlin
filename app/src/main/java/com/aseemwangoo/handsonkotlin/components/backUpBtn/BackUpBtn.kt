package com.aseemwangoo.handsonkotlin.components.backUpBtn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aseemwangoo.handsonkotlin.workers.OnDemandBackupViewModel

@Composable
fun BackUpButton(
    mBackUpViewModel: OnDemandBackupViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            mBackUpViewModel.beginBackup()
        }) {
            Text(text = "Backup Now")
        }
        Spacer(modifier = Modifier.padding(end = 4.dp))
        Button(onClick = {
            mBackUpViewModel.cancelBackup()
        }) {
            Text(text = "Cancel Backup")
        }
    }
}
