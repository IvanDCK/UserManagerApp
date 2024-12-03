package org.martarcas.usermanager.manager.domain.model

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
    NEW_USER(5)
}