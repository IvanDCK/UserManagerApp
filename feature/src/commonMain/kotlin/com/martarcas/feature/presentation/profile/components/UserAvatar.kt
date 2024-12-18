package com.martarcas.feature.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.martarcas.feature.mappers.toDrawableProfileImage
import com.martarcas.feature.presentation.profile.ProfileViewModel
import com.martarcas.feature.presentation.profile.model.ProfileActions
import com.martarcas.feature.presentation.ui_utils.ClosedNight
import org.jetbrains.compose.resources.painterResource

@Composable
fun UserAvatar(avatarId: String, profileViewModel: ProfileViewModel) {
    Box(
        modifier = Modifier
            .size(100.dp)
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            painter = painterResource(avatarId.toDrawableProfileImage()),
            contentDescription = "avatar_image"
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .background(color = ClosedNight),
        ) {
            IconButton(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    profileViewModel.onProfileAction(ProfileActions.OnEditAvatarButtonClick)
                }
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(25.dp),
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "edit_avatar_icon",
                    tint = Color.White,
                )
            }
        }
    }

}