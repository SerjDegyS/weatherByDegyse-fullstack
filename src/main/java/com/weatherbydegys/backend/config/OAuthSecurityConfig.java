package com.weatherbydegys.backend.config;

import com.weatherbydegys.backend.model.Role;
import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableWebSecurity
//@Order(-100)

@EnableOAuth2Client
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("oauth2ClientContext")
    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                .logout()
                    .logoutSuccessUrl("/").permitAll()
                .and()
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)

                .cors().and();

    }


    private Filter ssoFilter() {

        CompositeFilter filter = new CompositeFilter();
        List<OAuth2ClientAuthenticationProcessingFilter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter ("/api/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);
        filters.add(facebookFilter);

        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter ("/api/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);
        filters.add(googleFilter);

        filter.setFilters(filters);
        return filter;
    }

//  Реегистрация клиентов
    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }
    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }


//  Конечные точки информации о пользователях
    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }
    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

//  Поключаем фильтр и регистрируем его с низким поряком ля перенаправления на Facebook
    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter){
        FilterRegistrationBean registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepo userRepo) {
        return map -> {
            String uid = (String) map.get("sub");

            User user = userRepo.findByUid(uid).orElseGet(() -> {
                User newUser = new User();

                newUser.setUid(uid);
                newUser.setName((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setPhotoURL((String) map.get("picture"));
                newUser.setActive(true);
                newUser.setLastVisit(LocalDateTime.now());
                newUser.setRoles(new HashSet<>(Collections.singleton(Role.USER)));

                return newUser;
            });

            return userRepo.save(user);
        };
    }
}
