package com.example.projetA.Config;

import com.example.projetA.Services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSecure  {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();

        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        PasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();

        //return new BCryptPasswordEncoder();
        return noOpPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/create_User").permitAll()

                        .requestMatchers("/gestion/list").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/accueil",true)
                        .failureUrl("/login-error")
                        .permitAll());


        return http.build();
    }

}
