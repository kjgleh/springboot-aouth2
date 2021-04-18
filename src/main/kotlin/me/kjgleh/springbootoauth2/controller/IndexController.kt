package me.kjgleh.springbootoauth2.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/")
    fun index(
        model: Model,
        @AuthenticationPrincipal principal: OAuth2User?
    ): String {
        val name = principal?.let { it.attributes["name"].toString() }
        model.addAttribute("userName", name)
        return "index"
    }
}
