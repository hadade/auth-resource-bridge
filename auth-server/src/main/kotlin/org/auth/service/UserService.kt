package org.auth.service

import org.auth.model.User
import org.auth.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    private val inMemoryUser = org.springframework.security.core.userdetails.User
            .withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService {
        return UserDetailsService { username ->
            // First try in-memory user
            if (username == inMemoryUser.username) {
                return@UserDetailsService inMemoryUser
            }

            // Otherwise try DB
            val user = userRepository.findByUsername(username)
                    ?: throw UsernameNotFoundException("User '$username' not found")

            org.springframework.security.core.userdetails.User
                    .withUsername(user.username)
                    .password(user.password)
                    .roles("USER")
                    .build()
        }
    }

    fun saveUser(user: User): User {
        require(!userRepository.existsByUsername(user.username)) { "Username already exists" }

        val encodedPassword = passwordEncoder.encode(user.password)
        val savedUser = user.copy(password = encodedPassword)
        return userRepository.save(savedUser)
    }

}