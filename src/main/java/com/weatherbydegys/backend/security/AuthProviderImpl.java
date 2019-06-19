package com.weatherbydegys.backend.security;

import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserServiceImp userService;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        User user = userService.getOneByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        String password = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Bad password!!!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(user.getRoles());
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
