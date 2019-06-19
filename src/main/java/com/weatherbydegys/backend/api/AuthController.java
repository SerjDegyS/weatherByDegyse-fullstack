package com.weatherbydegys.backend.api;

import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.service.UserService;
import com.weatherbydegys.backend.util.UserEmailValidator;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller()
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserEmailValidator userEmailValidator;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user, BindingResult bindingResult,
            Model model){

        System.out.println(user);

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        boolean isConfirm = user.getPassword() != null && user.getPassword().equals(passwordConfirm);
        /* If password and confirm password are different */
        if (!isConfirm) {
            model.addAttribute("passwordError", "Password are different!");
        }

        userEmailValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors() || isConfirmEmpty || !isConfirm) {
            model.addAttribute("message", "Fill in the correct fields");
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            System.out.println(model.toString());
            System.out.println("ERROR!!!");
            return "/registration";
        }

        userService.add(user);

        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login (@RequestParam(name = "error", required = false) Boolean error,
                         Model model){
        if(Boolean.TRUE.equals(error)){
            model.addAttribute("error", true);
        }
        return "login";
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler (Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
