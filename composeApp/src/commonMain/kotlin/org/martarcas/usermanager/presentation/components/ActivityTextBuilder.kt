package org.martarcas.usermanager.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.martarcas.usermanager.domain.model.user.toFormattedRole
import org.martarcas.usermanager.presentation.activity.model.ActivityUiModel
import org.martarcas.usermanager.presentation.ui_utils.Hazy

@Composable
fun ActivityTextBuilder(activityItem: ActivityUiModel): AnnotatedString {
    val onPrimaryContainer = MaterialTheme.colorScheme.onPrimaryContainer

    fun AnnotatedString.Builder.appendStyledText(text: String, style: SpanStyle) {
        withStyle(style = style) { append(text) }
    }

    val boldStyle = SpanStyle(fontWeight = FontWeight.Bold, color = onPrimaryContainer)
    val lightStyle = SpanStyle(fontWeight = FontWeight.Light, color = onPrimaryContainer)
    val italicBoldStyle = SpanStyle(color = Hazy, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)

    return buildAnnotatedString {
        when (activityItem.activityType) {
            "ChangeRole" -> {
                appendStyledText(activityItem.userName, boldStyle)
                appendStyledText(" assigned the role of ", lightStyle)
                appendStyledText(activityItem.activityRole.toFormattedRole(), italicBoldStyle)
                appendStyledText(" to ", lightStyle)
                appendStyledText(activityItem.targetUserName, boldStyle)
            }

            "DeleteUser" -> {
                appendStyledText(activityItem.targetUserName, boldStyle)
                appendStyledText(" has been removed from the system by ", lightStyle)
                appendStyledText(activityItem.userName, italicBoldStyle)
            }

            "CreatedUser" -> {
                appendStyledText(activityItem.userName, boldStyle)
                appendStyledText(" has joined this organization ", lightStyle)
            }

            else -> {
                appendStyledText(activityItem.userName, italicBoldStyle)
                appendStyledText(" updated their personal information ", lightStyle)
            }
        }
    }
}