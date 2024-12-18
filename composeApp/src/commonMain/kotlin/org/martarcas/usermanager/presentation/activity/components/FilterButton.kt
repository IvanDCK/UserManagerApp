package org.martarcas.usermanager.presentation.activity.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.websocket.Frame
import org.martarcas.usermanager.presentation.activity.ActivityViewModel
import org.martarcas.usermanager.presentation.activity.model.ActivityActions
import org.martarcas.usermanager.presentation.activity.model.ActivityUiState
import org.martarcas.usermanager.presentation.ui_utils.ClosedNight

import org.martarcas.usermanager.presentation.ui_utils.Hazy

@Composable
fun FilterButton(uiState: ActivityUiState, activityViewModel: ActivityViewModel) {

    FilterChip(
        modifier = Modifier
            .width(120.dp)
            .height(50.dp),
        selected = uiState.filterByOwnLogs,
        onClick = {
            activityViewModel.onAction(ActivityActions.OnFilterByOwnLogsButtonClick)
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "My Logs",
                fontSize = 17.5.sp,
                textAlign = TextAlign.Center
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = ClosedNight,
            selectedContainerColor = Hazy,
            labelColor = Color.White,
            selectedLabelColor = Color.White,
        ),
        elevation = FilterChipDefaults.elevatedFilterChipElevation(2.dp),
        border = BorderStroke(0.dp, Color.Transparent)
    )
}