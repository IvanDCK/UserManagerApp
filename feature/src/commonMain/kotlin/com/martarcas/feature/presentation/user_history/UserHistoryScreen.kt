package com.martarcas.feature.presentation.user_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.martarcas.feature.presentation.components.ActivityItem
import com.martarcas.feature.presentation.user_history.model.UserHistoryUiState
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.back_icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHistoryScreen (navigateBack: () -> Unit) {
    val userHistoryViewModel: UserHistoryViewModel = koinViewModel()
    val uiState by userHistoryViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = navigateBack
                        ) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.back_icon),
                                contentDescription = "Return to profile icon"
                            )
                        }
                        Text("${uiState.loggedUser?.name}'s History")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserHistoryScreenContent(
                uiState = uiState
            )
        }
    }
}

@Composable
fun UserHistoryScreenContent(uiState: UserHistoryUiState) {
    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }

        uiState.activityList.isNotEmpty() -> {
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Member since: ${uiState.memberSince}", fontWeight = FontWeight.Bold)
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding()
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