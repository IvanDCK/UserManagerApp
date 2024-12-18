package com.martarcas.feature.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.martarcas.domain.model.user.Role
import com.martarcas.domain.model.user.User
import com.martarcas.domain.model.user.UserPublic
import com.martarcas.feature.mappers.toDrawableProfileImage
import com.martarcas.feature.presentation.ui_utils.Sunny
import org.jetbrains.compose.resources.painterResource
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.arrow_down
import usermanagerapp.feature.generated.resources.arrow_up
import usermanagerapp.feature.generated.resources.delete

@Composable
fun UserListItem(
    loggedUser: User?,
    user: UserPublic,
    isDropdownOpen: Boolean,
    onChangeRoleClick: () -> Unit,
    onChangeRoleApply: (Int, Role) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(user.avatarId.toDrawableProfileImage()),
                contentDescription = "avatar",
                modifier = Modifier.padding(end = 8.dp).size(65.dp).clip(CircleShape),
            )
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${user.name} ${user.surname}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user.role.name.replace("_", " "),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.secondary
            ).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (loggedUser?.role == Role.CEO || loggedUser?.role == Role.PROJECT_MANAGER) {
                MultipurposeButton(
                    text = "Change role",
                    onClick = onChangeRoleClick,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    endIcon = if (!isDropdownOpen) painterResource(Res.drawable.arrow_down) else painterResource(
                        Res.drawable.arrow_up
                    )
                )
                DropdownMenu(
                    expanded = isDropdownOpen,
                    onDismissRequest = onChangeRoleClick
                ) {
                    if (loggedUser.role == Role.PROJECT_MANAGER) {
                        val rolesToExclude =
                            listOf(
                                Role.CEO,
                                Role.PROJECT_MANAGER,
                                Role.HUMAN_RESOURCES,
                                Role.NEW_USER
                            )
                        Role.entries.filter {
                            rolesToExclude.contains(it).not()
                        }.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role.name.replace("_", " ")) },
                                onClick = {
                                    onChangeRoleApply(user.id, role)
                                })
                        }
                    } else {
                        Role.entries.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role.name.replace("_", " ")) },
                                onClick = {
                                    onChangeRoleApply(user.id, role)
                                })
                        }
                    }
                }
            }

            if (loggedUser?.role == Role.CEO || loggedUser?.role == Role.HUMAN_RESOURCES) {
                MultipurposeButton(
                    text = "Delete",
                    iconTint = Color.Black,
                    textColor = Color.Black,
                    buttonColor = Sunny,
                    startIcon = painterResource(Res.drawable.delete),
                    onClick = onDeleteClick,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

        }
    }
}