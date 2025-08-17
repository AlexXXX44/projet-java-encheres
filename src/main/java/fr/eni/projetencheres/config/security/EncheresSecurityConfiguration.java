package fr.eni.projetencheres.config.security;

import fr.eni.projetencheres.bll.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    public EncheresSecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers(mvcBuilder.pattern("/")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/login")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/ajout")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/inscription")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/creer_compte")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/css/**")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/js/**")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/img/**")).permitAll()
                    .requestMatchers(mvcBuilder.pattern("/article/**")).authenticated()
                    .anyRequest().authenticated();
        });

        http.formLogin(form -> form.loginPage("/login").permitAll());
        http.logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}
