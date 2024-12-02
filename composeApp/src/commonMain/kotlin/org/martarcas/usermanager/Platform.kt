package org.martarcas.usermanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform