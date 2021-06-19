package com.ktor.sample

import com.ktor.sample.route.bookRoute
import com.ktor.sample.route.greetingRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.jackson.jackson
import java.nio.file.attribute.UserPrincipal


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Locations)
    install(ContentNegotiation){
        jackson{
            // シリアライズ、デシリアライズの処理のカスタマイズを記述
        }
    }
    install(Authentication){
        basic{
            validate{ credentials ->
                if(credentials.name == "user" && credentials.password == "password"){
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
    routing{
        get("/") {
            call.respondText("Hello Ktor")
        }

        get("/path/{name}"){
            val name = call.parameters["name"]
            call.respondText("Hello $name")
        }

        get("/hello"){
            val name = call.parameters["name"]
            call.respondText("Hello $name")
        }
        greetingRoute()
        bookRoute()
        authenticate {
            get("/authenticated"){
                val user = call.authentication.principal<UserIdPrincipal>()
                call.respondText("authenticated id=${user?.name}")
            }
        }
    }
}

