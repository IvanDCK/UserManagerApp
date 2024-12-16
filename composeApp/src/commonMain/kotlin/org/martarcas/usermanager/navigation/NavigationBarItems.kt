package org.martarcas.usermanager.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationBarItems(
    val label: String,
    val icon: ImageVector,
    val route: Destinations
) {
    LIST("List", Icons.Rounded.Menu, Destinations.List),
    ACTIVITY("Activity", Icons.Default.Done, Destinations.Activity),
    PROFILE("Profile", Icons.Default.Person, Destinations.Profile)
}
