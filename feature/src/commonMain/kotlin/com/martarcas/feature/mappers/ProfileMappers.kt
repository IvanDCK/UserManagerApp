package com.martarcas.feature.mappers

import org.jetbrains.compose.resources.DrawableResource
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.user_avatar0
import usermanagerapp.feature.generated.resources.user_avatar1
import usermanagerapp.feature.generated.resources.user_avatar2
import usermanagerapp.feature.generated.resources.user_avatar3
import usermanagerapp.feature.generated.resources.user_avatar4
import usermanagerapp.feature.generated.resources.user_avatar5
import usermanagerapp.feature.generated.resources.user_avatar6
import usermanagerapp.feature.generated.resources.user_avatar7


fun String.toDrawableProfileImage(): DrawableResource {
    return when (this) {
        "user_avatar0" -> return Res.drawable.user_avatar0
        "user_avatar1" -> return Res.drawable.user_avatar1
        "user_avatar2" -> return Res.drawable.user_avatar2
        "user_avatar3" -> return Res.drawable.user_avatar3
        "user_avatar4" -> return Res.drawable.user_avatar4
        "user_avatar5" -> return Res.drawable.user_avatar5
        "user_avatar6" -> return Res.drawable.user_avatar6
        "user_avatar7" -> return Res.drawable.user_avatar7
        else -> return Res.drawable.user_avatar0
    }
}