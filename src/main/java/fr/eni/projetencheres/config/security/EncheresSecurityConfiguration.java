package fr.eni.projetencheres.config.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfiguration {

    @Bean
    UserDetailsManager userDetailsManager(DataSource datasource) {
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("moi"));
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
        manager.setUsersByUsernameQuery("select pseudo, mot_de_passe, 1 from UTILISATEURS where pseudo = ?");
        manager.setAuthoritiesByUsernameQuery("select pseudo, role from ROLES where pseudo = ?");
        return manager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers(mvcBuilder.pattern("/")).permitAll()         // page d'accueil
                    .requestMatchers(mvcBuilder.pattern("/login")).permitAll()   // page de login
                    .requestMatchers(mvcBuilder.pattern("/js/**")).permitAll()   // ressources JS
                    .requestMatchers(mvcBuilder.pattern("/css/**")).permitAll()  // ressources CSS
                    .requestMatchers(mvcBuilder.pattern("/img/**")).permitAll()  // images
                    .requestMatchers(mvcBuilder.pattern("/article/**")).authenticated() // accès protégé
                    .anyRequest().authenticated(); // tout le reste nécessite authentification
        });

        http.formLogin(form -> {
            form.loginPage("/login").permitAll(); // page de login accessible à tous
            form.successHandler(new SavedRequestAwareAuthenticationSuccessHandler()); // redirige vers la page demandée
        });

        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
        );

        return http.build();
    }
}
