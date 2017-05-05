package reco.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import reco.interceptor.InvalidSessionInterceptor;
import reco.interceptor.ValidSessionInterceptor;

/**
 * Created by nishantbhardwaj2002 on 3/9/17.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"reco"})
public class SpringConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {

        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(new InvalidSessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/signup", "/signin");

        registry.addInterceptor(new ValidSessionInterceptor())
                .addPathPatterns("/signup", "/signin");
    }
}
