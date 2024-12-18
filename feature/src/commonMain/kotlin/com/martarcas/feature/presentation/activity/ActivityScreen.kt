package com.martarcas.feature.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.martarcas.feature.presentation.activity.components.FilterButton
import com.martarcas.feature.presentation.activity.model.ActivityUiState
import com.martarcas.feature.presentation.components.ActivityItem
import com.martarcas.feature.presentation.components.ProgressLoading
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ActivityScreen() {

    val activityViewModel: ActivityViewModel = koinViewModel()
    val uiState by activityViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FilterButton(uiState, activityViewModel)
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActivityScreenContent(
                uiState = uiState
            )
        }
    }
}

@Composable
fun ActivityScreenContent(uiState: ActivityUiState) {

    when {
        uiState.isLoading -> {
            ProgressLoading()
        }

        uiState.activityList.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding()
                    .padding(vertical = 4.dp)
            ) {
                items(uiState.activityList.reversed()) { activityLog ->
                    ActivityItem(activityLog)
                }
            }
        }

        uiState.activityList.isEmpty() -> {
            Text("No activity logs found")
        }
    }
}
