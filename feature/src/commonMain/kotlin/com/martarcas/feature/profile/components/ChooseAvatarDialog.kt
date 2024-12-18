package com.martarcas.feature.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.martarcas.feature.profile.ProfileViewModel
import com.martarcas.feature.profile.model.AvatarBottomSheetActions
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAvatarBottomSheet(profileViewModel: ProfileViewModel) {

    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    val modalSheetState = rememberModalBottomSheetState()

    if (uiState.showAvatarChooserDialog) {
        ModalBottomSheet(
            sheetState = modalSheetState,
            onDismissRequest = { profileViewModel.onAvatarBottomSheetAction(AvatarBottomSheetActions.OnDismissClick) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = "Choose avatar",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(8) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .clickable {
                                    profileViewModel.onAvatarBottomSheetAction(
                                        AvatarBottomSheetActions.OnSelectNewAvatar(
                                            "user_avatar$it"
                                        )
                                    )
                                }
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(profileViewModel.avatarNameDispenser(it)),
                                contentDescription = "image_avatar_chooser",
                                contentScale = ContentScale.Crop
                            )
                            if (uiState.avatarId == "user_avatar$it") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White.copy(alpha = 0.4f))
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .align(Alignment.Center),
                                        imageVector = Icons.Rounded.Check,
                                        tint = Color.White,
                                        contentDescription = "check_icon_avatar_selected"
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}