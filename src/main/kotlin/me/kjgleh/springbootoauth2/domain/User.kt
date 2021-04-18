package me.kjgleh.springbootoauth2.domain

import me.kjgleh.springbootoauth2.domain.enums.Role
import javax.persistence.*

@Entity(name = "ms_users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String,
    val email: String,
    var picture: String,
    @Enumerated(EnumType.STRING)
    val role: Role
) {

    fun update(name: String, picture: String) {
        this.name = name
        this.picture = picture
    }
}
