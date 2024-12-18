package com.martarcas.feature.presentation.ui_utils

import com.martarcas.domain.model.response.DataError
import usermanagerapp.feature.generated.resources.Res
import usermanagerapp.feature.generated.resources.conflict_request
import usermanagerapp.feature.generated.resources.error_disk_full
import usermanagerapp.feature.generated.resources.error_no_internet
import usermanagerapp.feature.generated.resources.error_request_timeout
import usermanagerapp.feature.generated.resources.error_serialization
import usermanagerapp.feature.generated.resources.error_too_many_requests
import usermanagerapp.feature.generated.resources.error_unknown

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER -> Res.string.error_unknown
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.CONFLICT -> Res.string.conflict_request
    }

    return UiText.StringResourceId(stringRes)
}