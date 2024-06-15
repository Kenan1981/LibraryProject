package com.tpe.security.config;


import com.tpe.security.jwt.AuthTokenFilter;
import com.tpe.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig
{
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITE_LIST).permitAll()
                .anyRequest().authenticated();

        //for Click Jacking attack
        http.headers().frameOptions().sameOrigin();

        //provider
        http.authenticationProvider(authenticationProvider());

        //filter
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationTokenFilter()
    {
        return new AuthTokenFilter();
    }


    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);

        authenticationProvider.setPasswordEncoder(passwordEncoder());


        return authenticationProvider;

    }

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
      return  new WebMvcConfigurer() {


          public void addCorsMapping(CorsRegistry registry)
          {
              registry.addMapping("/**")
                      .allowedOrigins("*")
                      .allowedHeaders("*")
                      .allowedMethods("*");
          }
      };


    }


    private static final String[] AUTH_WHITE_LIST =          {
            "/",
            "/v3/api-docs/**",
            "swagger-ui.html",
            "/swagger-ui/**",
            "index.html",
            "/images/**",
            "/css/**",
            "/js/**",
            "/signin",
            "/register",
            "/publishers",
            "/books",
            "/books/id",
            "/publishers/id",
            "/authors",
            "/authors/id",
            "/categories",
            "/categories/id"
    };





}























