package org.martarcas.usermanager.manager.presentation.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MultipurposeButton(
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    iconTint: Color = Color.White,
    iconSize: Int = 24,
    text: String? = null,
    textColor: Color = Color.White,
    onClick: () -> Unit,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonWithIconContentPadding,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = buttonColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            startIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize.dp)
                )
            }
            if (startIcon != null && text != null) {
                Spacer(modifier = Modifier.size(4.dp))
            }
            text?.let {
                Text(
                    text = text,
                    color = textColor,
                )
            }
            if (endIcon != null && text != null) {
                Spacer(modifier = Modifier.size(4.dp))
            }
            endIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize.dp)
                )
            }
        }

    }

}