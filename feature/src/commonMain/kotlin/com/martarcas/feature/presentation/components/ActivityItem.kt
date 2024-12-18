package com.martarcas.feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.martarcas.feature.presentation.activity.model.ActivityUiModel
import com.martarcas.feature.presentation.ui_utils.formatActivityType
import com.martarcas.feature.presentation.ui_utils.formatTimestampDifference

@Composable
fun ActivityItem(activityItem: ActivityUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp, start = 10.dp, end = 10.dp)
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatActivityType(activityItem.activityType),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = formatTimestampDifference(activityItem.actionTimestamp.toLong()),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            text = ActivityTextBuilder(activityItem)
        )
    }
}