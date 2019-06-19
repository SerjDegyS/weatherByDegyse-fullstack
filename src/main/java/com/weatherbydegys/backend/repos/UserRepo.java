package com.weatherbydegys.backend.repos;

import com.weatherbydegys.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    User findById(long id);
    Optional <User> findByUid(String uid);
    User findAllByEmail(String email);
    UserDetails findUserByName(String userName);

}
