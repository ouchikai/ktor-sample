package com.ktor.sample.route

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@Location("/user")
class UserLocation {
    @Location("/{id}")
    data class GetLocation(val id: Long)

    @Location("/detail/{id}")
    data class GetDetailLocation(val id: Long)

    @Location("authenticated")
    data class AuthenticatedLocation(val id: Long)
}

fun Routing.userRoute() {
    get<UserLocation.GetLocation> { param ->
        val id = param.id
        call.respondText("get id=$id")
    }

    get<UserLocation.GetDetailLocation> { param ->
        val id = param.id
        call.respondText("get Detail id=$id")
    }
}

fun Route.authenticatedUserRoute(){
    get("/authenticated"){
        val user = call.authentication.principal<UserIdPrincipal>()
        call.respondText("authenticated id=${user?.name}")
    }
}

data class MyUserPrincipal(val id: Long, val name: String, val profile: String): Principal