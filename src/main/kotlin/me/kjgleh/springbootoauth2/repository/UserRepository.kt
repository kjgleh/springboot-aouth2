package me.kjgleh.springbootoauth2.repository

import me.kjgleh.springbootoauth2.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>
}
