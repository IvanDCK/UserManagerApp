package org.martarcas.usermanager.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {

    @Serializable
    data object Login: Destinations()

    @Serializable
    data object SignUp: Destinations()

    @Serializable
    data object List: Destinations()

    @Serializable
    data object Activity: Destinations()

    @Serializable
    data object Profile: Destinations()

    @Serializable
    data object UserHistory: Destinations()
}
