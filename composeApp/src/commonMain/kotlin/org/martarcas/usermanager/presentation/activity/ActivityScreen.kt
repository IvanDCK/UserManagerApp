package org.martarcas.usermanager.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.presentation.activity.components.ActivityItem
import org.martarcas.usermanager.presentation.activity.model.ActivityUiState

@Composable
fun ActivityScreen() {

    val activityViewModel: ActivityViewModel = koinViewModel()
    val uiState by activityViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActivityScreenContent(
            activityViewModel = activityViewModel,
            uiState = uiState
        )
    }
}

@Composable
fun ActivityScreenContent(uiState: ActivityUiState, activityViewModel: ActivityViewModel) {

    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }

        uiState.activityList.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding()
            ) {
                items(uiState.activityList.reversed()) { activityLog ->
                    ActivityItem(activityLog, activityViewModel)
                }
            }
        }

        uiState.activityList.isEmpty() -> {
            Text("No activity logs found")
        }
    }
}
