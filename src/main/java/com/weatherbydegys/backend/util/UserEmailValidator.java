package com.weatherbydegys.backend.util;

import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEmailValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        /* If user.email is empty */
        if (user.getEmail().length() == 0) {
            errors.rejectValue("email", "", "Email cannot be empty");
        }else {
            /* If user exist */
            if (userService.getOneByEmail(user.getEmail()) != null) {
                errors.rejectValue(
                        "email",
                        "",
                        "This email or login is already in use"
                );
            }
        }
    }

    public User userDataValidate(Object target) {
        User newUser = (User) target;
        User oldUser = userService.getById(newUser.getId());

        /* Overwrite old user*/
        if (newUser.getName() != null && newUser.getName().length() >= 1) {
            oldUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null && newUser.getEmail().length() >= 4) {
            oldUser.setEmail(newUser.getEmail());
        }
        if (newUser.getPassword() != null && newUser.getPassword().length() >= 1) {
            oldUser.setPassword(newUser.getPassword());
        }
        if (newUser.getPhotoURL() != null) {
            oldUser.setPhotoURL(newUser.getPhotoURL());
        }
        if (newUser.getRoles() != null) {
            oldUser.setRoles(newUser.getRoles());
        }

        return oldUser;
    }
}
