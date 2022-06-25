package ua.co.k.spring.google.auth.example.utils;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.web.servlet.View;

import java.util.Locale;

/**
 * <a href="https://github.com/jknack/handlebars.java/issues/979"> related issue</a> 
 */
public class ReloadableHandlebarsViewResolver extends HandlebarsViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return createView(viewName, locale);
    }
}
