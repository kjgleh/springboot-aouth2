package me.kjgleh.springbootoauth2.config

import me.kjgleh.springbootoauth2.config.dto.OAuthAttributes
import me.kjgleh.springbootoauth2.domain.User
import me.kjgleh.springbootoauth2.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId;
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails
                .userInfoEndpoint.userNameAttributeName

        val attributes = OAuthAttributes.of(
            registrationId,
            userNameAttributeName,
            oAuth2User.attributes
        )

        val user = saveOrUpdate(attributes)

        return DefaultOAuth2User(
            setOf(SimpleGrantedAuthority(user.role.key)),
            attributes.attributes,
            attributes.nameAttributeKey
        )
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): User {
        val user = userRepository.findByEmail(attributes.email).map {
            it.update(
                name = attributes.name,
                picture = attributes.picture
            )
            it
        }.orElse(attributes.toEntity())
        return userRepository.save(user)
    }
}
