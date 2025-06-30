package org.auth.api

import org.auth.model.User
import org.auth.service.JwtService
import org.auth.service.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@Controller
class AuthController(
        private val authManager: AuthenticationManager,
        private val jwtService: JwtService,
        private val userService: UserService,
) {

    @MutationMapping
    fun generateToken(@Argument request: AuthRequest): AuthResponse {
        val auth = UsernamePasswordAuthenticationToken(request.username, request.password)
        authManager.authenticate(auth)
        val token = jwtService.generateToken(request.username)
        return AuthResponse(token)
    }

    @MutationMapping
    fun validateToken(@Argument request: TokenValidationRequest): TokenValidationResponse {
        return try {
            val username = jwtService.extractUsername(request.token)
            val isExpired = jwtService.isTokenExpired(request.token)

            if (!isExpired) {
                TokenValidationResponse(valid = true, username = username)
            } else {
                TokenValidationResponse(valid = false, username = null)
            }

        } catch (e: Exception) {
            TokenValidationResponse(valid = false, username = null)
        }
    }

    @MutationMapping
    fun register(@Argument request: RegisterRequest): AuthResponse {
        val user = User(
                username = request.username,
                password = request.password,
                email = request.email
        )

        userService.saveUser(user)

        val token = jwtService.generateToken(user.username)

        return AuthResponse(token)
    }

    @QueryMapping
    fun hello(): String = "Hello world"
}

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)

data class TokenValidationRequest(val token: String)
data class TokenValidationResponse(val valid: Boolean, val username: String?)
data class RegisterRequest(val username: String, val password: String, val email: String)