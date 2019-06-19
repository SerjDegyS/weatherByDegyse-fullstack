package com.weatherbydegys.backend.api;

import com.weatherbydegys.backend.dto.MapperUserDTO;
import com.weatherbydegys.backend.dto.UserDTO;
import com.weatherbydegys.backend.model.FavCity;
import com.weatherbydegys.backend.model.Role;
import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.repos.FavCityRepo;
import com.weatherbydegys.backend.service.UserService;
import com.weatherbydegys.backend.service.storage.StorageService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller()
public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    @Autowired
    private MapperUserDTO mapperUserDTO;

    @Autowired
    private UserService userService;

    @Autowired
    private FavCityRepo favCityRepo;

    @Autowired
    private StorageService storageService;

    @Value("${upload.path}")
    private String uploadPath;
//    @Value("${serve.path}")
//    private String servePath;

    @GetMapping("/")
    public String greeting(Model model, Principal user
                         ) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Map<String, Object> model){
        Iterable<User> users;

        /* Filtering users list*/
        if(filter != null && !filter.isEmpty()) {
            users = userService.getByName(filter);
        } else {
            users = userService.getAll();
        }

        Iterable<FavCity> favCities = favCityRepo.findAll();
        model.put("users", users);
        model.put("favCities", favCities);
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/main/userList")
    public String all(Model model) {
        Iterable<User> allUsers = userService.getAll();

        List<UserDTO> userList = ((List<User>) allUsers).stream()
                .map(user -> mapperUserDTO.convertToDTO(user))
                .collect(Collectors.toList());

        model.addAttribute("userList", userList);

        return "userList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/main/edit/{id}", method = RequestMethod.GET)
    public String editor(@PathVariable("id") User user, Model model){
        /* Spring will find the user himself*/
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @GetMapping("/img/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
//            @RequestParam String name,
//            @RequestParam String email,
//            @RequestParam String photoUser,
            @Valid User newUser,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user,
            Model model) {

//        @Valid User newUser = new User(name, email, photoURL);
        /* If binding result has error */
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Fill in the correct fields");
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            System.out.println(newUser);
            System.out.println(errors);

            return "userEdit";
        }

        /* Download file if exist*/
        if (!file.isEmpty()) {
            String resultFileName = storageService.store(file);

            user.setPhotoURL("/img/" + resultFileName);

        }
        else user.setPhotoURL(newUser.getPhotoURL());

        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
//        user.setPhotoURL(newUser.getPhotoURL());

        System.out.println(user);
        /*Get all roles*/
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        /*Clean roles of user*/
        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userService.update(user);
        return "redirect:/main/userList";
    }


//    @RequestMapping(method = RequestMethod.POST, value = "greater", consumes = MediaType.ALL_VALUE)
//    public String add(@RequestParam String uid,
//                      @RequestParam String name,
//                      @RequestParam String email,
//                      @RequestParam String photoURL,
//                      Map<String, Object> model) {
//        User user = new User(uid, name, email, photoURL);
//
//        userService.add(user);
//
//        Iterable<User> users = userService.getAll();
//        model.put("users", users);
//
//        return "main";
//    }

    @GetMapping("/json")
    @ResponseBody
    public Iterable<User> getJson() { return userService.getAll(); }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler (Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
