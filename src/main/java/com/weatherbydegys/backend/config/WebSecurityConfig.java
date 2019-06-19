package com.weatherbydegys.backend.config;

import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.repos.UserRepo;
import com.weatherbydegys.backend.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
//@Order(3)
//@EnableOAuth2Sso
//@ComponentScan("com.weatherbydegys.backend.security")
/* For ADMIN roles */
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProviderImpl authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/img/**", "/static/**").permitAll()
                .antMatchers("/", "/login", "/login/process", "/registration").anonymous()
                .antMatchers( "/main/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .usernameParameter("email")
                .passwordParameter("password")
//                .defaultSuccessUrl("/main")
                .failureUrl("/login?message=Bad credential!!!")
                .permitAll()
                .and()
             .rememberMe()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/main")
                .and()
            .logout()
            .logoutSuccessUrl("/");

//        http.antMatcher("/api/user/**").oauth2Login().clientRegistrationRepository(new ClientRegistrationRepository() {
//            @Override
//            public ClientRegistration findByRegistrationId(String s) {
//
//                return null;
//            }
//        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);



//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select email, password, name  from users where email=?")
//                .authoritiesByUsernameQuery("select u.email, ur.roles from users u inner join user_roles ur on u.id = ur.user_id where u.email=?");
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepo userRepo) {
        return map -> {
            return new User();
        };
    }

}
