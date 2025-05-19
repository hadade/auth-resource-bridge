package org.auth.service

import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Service

@Service
class UserService {
    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("user")
                .password("{noop}password") // {noop} = plain text encoder
                .roles("USER")
                .build()
        return InMemoryUserDetailsManager(user)
    }
}