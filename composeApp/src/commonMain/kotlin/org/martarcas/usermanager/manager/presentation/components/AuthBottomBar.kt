package org.martarcas.usermanager.manager.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthBottomBar(infoText: String, textClickable: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            infoText,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            modifier = Modifier.clickable { onClick() },
            text = textClickable,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 13.sp
        )
    }
}