package ua.co.k.spring.google.auth.example;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
    
    @RequestMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User principal, CsrfToken sessionToken) {
        model.addAttribute("foo", "Hello Handlebars!");
        if (principal == null) {
            model.addAttribute("logged", false);
            model.addAttribute("loginUrl", "oauth2/authorization/google");
        } else {
            model.addAttribute("logged", true);
            model.addAttribute("user", principal.getAttribute("name"));
            if (sessionToken != null) {
                model.addAttribute("_csrf", sessionToken.getToken());
            }
        }
        return "index";
    }
}
