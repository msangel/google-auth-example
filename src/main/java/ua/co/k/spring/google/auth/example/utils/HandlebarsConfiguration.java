package ua.co.k.spring.google.auth.example.utils;

import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.GuavaTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsProperties;

import javax.annotation.PostConstruct;

import static com.google.common.cache.CacheBuilder.newBuilder;

/**
 * <a href="https://github.com/jknack/handlebars.java/issues/979"> related issue</a> 
 */
@Configuration
@EnableConfigurationProperties(HandlebarsProperties.class)
public class HandlebarsConfiguration {

    @Autowired
    private HandlebarsProperties handlebars;

    @Bean
    public HandlebarsViewResolver handlebarsViewResolver() {
        HandlebarsViewResolver handlebarsViewResolver = new ReloadableHandlebarsViewResolver();
        handlebars.applyToMvcViewResolver(handlebarsViewResolver);
        return handlebarsViewResolver;
    }

    @Bean
    public TemplateCache templateCache() {
        GuavaTemplateCache templateCache = new GuavaTemplateCache(newBuilder().<TemplateSource, Template>build());
        templateCache.setReload(true);
        return templateCache;
    }
    
    @Configuration
    protected static class HandlebarsCachingStrategyConfiguration {
        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @Autowired
        private TemplateCache templateCacheInstance;

        @PostConstruct
        public void setCachingStrategy() {
            if (handlebarsViewResolver.isCache()) {
                handlebarsViewResolver.getHandlebars().with(templateCacheInstance);
            }
        }
    }


}