package com.martarcas.feature.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileButton(
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(55.dp),
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(text = label, color = Color.White, fontSize = 19.sp, maxLines = 1)
    }
}
