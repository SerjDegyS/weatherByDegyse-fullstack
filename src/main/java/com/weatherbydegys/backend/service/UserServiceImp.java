package com.weatherbydegys.backend.service;

import com.weatherbydegys.backend.model.Role;
import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.repos.UserRepo;
import com.weatherbydegys.backend.util.UserEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional()
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserEmailValidator userEmailValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> getByName(String name) {
        return userRepo.findByName(name);
    }

    @Override
    public User getOneByEmail(String email) {
        return userRepo.findAllByEmail(email);
    }

    @Override
    public User add(User newUser) {

//        User User = userEmailValidator.userDataValidate(newUser);


        newUser.setActive(true);
        newUser.setLastVisit(LocalDateTime.now());
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (newUser.getPhotoURL() == null) { newUser.setPhotoURL(""); }

        return userRepo.save(newUser);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    @Transactional()
    public User update(User newUser) {
        User oldUser = userRepo.findById(newUser.getId());


        return userRepo.save(oldUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepo.findUserByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
