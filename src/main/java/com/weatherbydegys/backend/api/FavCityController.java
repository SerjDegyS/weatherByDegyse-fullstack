package com.weatherbydegys.backend.api;

import com.weatherbydegys.backend.dto.FavCityDTO;
import com.weatherbydegys.backend.dto.MapperFavCityDTO;
import com.weatherbydegys.backend.model.FavCity;
import com.weatherbydegys.backend.repos.FavCityRepo;
import com.weatherbydegys.backend.service.UserFavCityService;
import com.weatherbydegys.backend.service.UserService;
import javassist.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.weatherbydegys.backend.Specification.FavCitySpecification.favCityNameStartBy;

@RestController
@RequestMapping("/api/city")
public class FavCityController {
    private static final Logger LOGGER = Logger.getLogger(FavCityController.class);
    @Autowired
    MapperFavCityDTO mapperFavCityDTO;

    @Autowired
    UserFavCityService userFavCityService;
    @Autowired
    UserService userService;
    @Autowired
    /* For filter */
    FavCityRepo favCityRepo;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public List<FavCityDTO> addFavCityToUser(@RequestParam("user_id") Long userId,
                                             @RequestBody @Valid FavCity newFavCity,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            System.out.println(errorsMap);
            throw new IllegalArgumentException(errorsMap.toString());
        }else {
                userFavCityService.addFavCityToUser(userId, newFavCity);
        }

        return userFavCityService.getByUserID(userId).stream()
                .map(city -> mapperFavCityDTO.convertToDTO(city)).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    public List<FavCityDTO> getByUserID(@RequestParam("user_id") String userID) {
        List<FavCity> result = userFavCityService.getByUserID(Long.parseLong(userID));
        return result.stream()
                .map(favCity -> mapperFavCityDTO.convertToDTO(favCity)).collect(Collectors.toList());
    }

//    @DeleteMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public void delete (@RequestParam String userID, @RequestParam String favCityID) {
//        User user = userService.getById(Integer.parseInt(userID));
//        if(user == null){
//            throw new IllegalArgumentException("User with ID: " + userID + " not found!!!");
//        }
//        FavCity favCity = favCityRepo.findById(Integer.parseInt(favCityID));
//        if(favCity == null) {
//            throw new IllegalArgumentException("FavCity with ID: " + favCityID + " not found!!!");
//        }
//        user.deleteFavCity(favCity);
//        userService.update(user);
//        LOGGER.info("DELETE FavCity with ID: " + favCityID + " from User ID: " + userID);
//    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateCitiesByUserID")
    public void updateCitiesByUserID(@RequestParam String userID, @RequestBody List<FavCityDTO> favCityDTOList) {
        List<FavCity> favCityList = favCityDTOList.stream()
                .map(favCityDTO -> mapperFavCityDTO.convertToEntity(favCityDTO))
                .collect(Collectors.toList());
        userFavCityService.updateCitiesByUserID(Long.parseLong(userID), favCityList);
    }

    @GetMapping("/filter")
    public List<FavCity> filter(@RequestParam String filter) throws NotFoundException {
        if (filter == null || filter.length() < 3 ){
            throw new NotFoundException("Bad filter! Min 3 symbols!");
        }

        return favCityRepo.findAll(favCityNameStartBy(filter));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public FieldErrorDTO errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage());
        return new FieldErrorDTO(exc.toString(), exc.getMessage());
    }


    public class FieldErrorDTO implements Serializable {
        public LocalDateTime timestap;
        private String field;
        private String message;

        public FieldErrorDTO(String field, String message) {
            this.timestap = LocalDateTime.now();
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
