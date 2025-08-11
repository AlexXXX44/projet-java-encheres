package fr.eni.projetencheres.config.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfiguration {

//   @Bean
//   InMemoryUserDetailsManager userDetailsManager(){
//       PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        String moiChiffre = encoder.encode("moi");
//       System.out.println("moi : " + moiChiffre);
//     UserDetails a = User.builder().username("admin").password(moiChiffre).roles("ADMIN").build();
//       UserDetails b = User.builder().username("utilisateur").password(moiChiffre).roles("UTILISATEUR").build();
//
//       return new InMemoryUserDetailsManager(a, b);
//   }


    @Bean
    UserDetailsManager userDetailsManager(DataSource datasource){
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("moi"));
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
        manager.setUsersByUsernameQuery("select pseudo, mot_de_passe, 1 from UTILISATEURS where pseudo = ?");
        manager.setAuthoritiesByUsernameQuery("select pseudo, role from ROLES where pseudo = ?");
        return manager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{

        MvcRequestMatcher.Builder mvcBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(
                auth -> {
                    auth
                            .requestMatchers(mvcBuilder.pattern("/")).permitAll()

                            // .requestMatchers(mvcBuilder.pattern("/")).hasRole("USER")
                            .requestMatchers(mvcBuilder.pattern("/*")).permitAll()
                            .requestMatchers(mvcBuilder.pattern("/js/*")).permitAll()

                            .requestMatchers(mvcBuilder.pattern("/css/*")).permitAll()
                            .requestMatchers(mvcBuilder.pattern("/img/*")).permitAll()
                            .anyRequest().denyAll();

                }
        );
        // http.formLogin(Customizer.withDefaults());

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/accueil_connecte");
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
