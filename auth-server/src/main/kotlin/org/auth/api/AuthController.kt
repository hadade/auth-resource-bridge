package org.auth.api

import org.auth.service.JwtService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@Controller
class AuthGraphQLController(
        private val authManager: AuthenticationManager,
        private val jwtService: JwtService
) {

    @MutationMapping
    fun login(@Argument request: AuthRequest): AuthResponse {
        val auth = UsernamePasswordAuthenticationToken(request.username, request.password)
        authManager.authenticate(auth)
        val token = jwtService.generateToken(request.username)
        return AuthResponse(token)
    }

    @QueryMapping
    fun hello(): String = "Hello world"
}

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)