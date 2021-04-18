package me.kjgleh.springbootoauth2.config.dto

import me.kjgleh.springbootoauth2.domain.User
import me.kjgleh.springbootoauth2.domain.enums.Role

data class OAuthAttributes(
    val attributes: Map<String, Any>,
    val nameAttributeKey: String,
    val name: String,
    val email: String,
    val picture: String
) {
    fun toEntity(): User {
        return User(
            name = name,
            email = email,
            picture = picture,
            role = Role.GUEST
        )
    }

    companion object {
        fun of(
            registrationId: String,
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return OAuthAttributes(
                attributes = attributes,
                nameAttributeKey = userNameAttributeName,
                name = attributes["name"].toString(),
                email = attributes["email"].toString(),
                picture = attributes["picture"].toString()
            )
        }
    }
}
