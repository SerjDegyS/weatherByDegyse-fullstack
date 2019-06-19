package com.weatherbydegys.backend.api;

import com.weatherbydegys.backend.dto.MapperUserDTO;
import com.weatherbydegys.backend.dto.UserDTO;
import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MapperUserDTO mapperUserDTO;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable("id") String id) {
//        Iterable<User> allUsers = userService.getAll();

//        List<UserDTO> result = ((List<User>) allUsers).stream()
//                .map(user -> mapperUserDTO.convertToDTO(user))
//                .collect(Collectors.toList());
        User user = userService.getById(Long.parseLong(id));

        LOGGER.info(user);

        return mapperUserDTO.convertToDTO(user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User newUser = mapperUserDTO.convertToEntity(userDTO);

        userService.add(newUser);
        LOGGER.info("CREATE new User: " + newUser.toString());

        return mapperUserDTO.convertToDTO(newUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public UserDTO update(@RequestBody UserDTO newUserDTO) {
        User newUser = mapperUserDTO.convertToEntity(newUserDTO);
        userService.update(newUser);
        LOGGER.info("UPDATE User: " + newUser.toString());
        return mapperUserDTO.convertToDTO(newUser);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage());
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
