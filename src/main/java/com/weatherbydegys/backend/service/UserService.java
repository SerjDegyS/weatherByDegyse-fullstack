package com.weatherbydegys.backend.service;

import com.weatherbydegys.backend.dao.UserDAO;
import com.weatherbydegys.backend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDAO, UserDetailsService {

    User getOneByEmail(String email);

}
