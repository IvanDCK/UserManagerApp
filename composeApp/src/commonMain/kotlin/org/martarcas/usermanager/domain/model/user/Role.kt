package org.martarcas.usermanager.domain.model.user

enum class Role(val importance: Int) {
    CEO(1),
    HUMAN_RESOURCES(2),
    PROJECT_MANAGER(3),
    MOBILE_DEVELOPER(4),
    BACKEND_DEVELOPER(4),
    FRONTEND_DEVELOPER(4),
    FULLSTACK_DEVELOPER(4),
    UX_UI_DESIGNER(4),
    DATA_SCIENTIST(4),
    DATA_ANALYST(4),
    NEW_USER(5);

    val formattedName: String
        get() = when (this) {
            CEO -> "CEO"
            HUMAN_RESOURCES -> "Human Resources"
            PROJECT_MANAGER -> "Project Manager"
            MOBILE_DEVELOPER -> "Mobile Developer"
            BACKEND_DEVELOPER -> "Backend Developer"
            FRONTEND_DEVELOPER -> "Frontend Developer"
            FULLSTACK_DEVELOPER -> "Fullstack Developer"
            UX_UI_DESIGNER -> "UX/UI Designer"
            DATA_SCIENTIST -> "Data Scientist"
            DATA_ANALYST -> "Data Analyst"
            NEW_USER -> "New User"
        }
}

fun String.toFormattedRole(): String {
    return Role.valueOf(this).formattedName
}
