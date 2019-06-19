package com.weatherbydegys.backend.service;

import com.weatherbydegys.backend.dao.UserFavCityDAO;
import com.weatherbydegys.backend.model.FavCity;
import com.weatherbydegys.backend.model.User;
import com.weatherbydegys.backend.repos.FavCityRepo;
import com.weatherbydegys.backend.repos.UserRepo;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserFavCityService implements UserFavCityDAO {

    private static final Logger LOGGER = Logger.getLogger(UserFavCityService.class);

    @Autowired
    UserRepo userRepo;
    @Autowired
    FavCityRepo favCityRepo;

    @Override
    public List<FavCity> addFavCityToUser(long userId, FavCity favCity) {

        User user = userRepo.findById(userId);
        if(user == null){
            throw new IllegalArgumentException("User with ID: " + userId + " is not available!!!");
        }

        FavCity newFavCity = favCityRepo.findById(favCity.getId());

        /*If city doesn't exist in BD*/
        if(newFavCity == null){
            LOGGER.info("City with ID: " + favCity.getId() + "doesn't exist in BD!");
            throw new IllegalArgumentException("City with ID: " + favCity.getId() + " doesn't exist in BD!");
        }

        /*If user don't has new Favorite City*/
        if(!user.getFavCities().contains(newFavCity)){
            newFavCity.addUser(user);
            user.addFavCity(newFavCity);
            LOGGER.info("ADD to User ID: " + user.getId() + "FavCity: " + newFavCity.toString());

            userRepo.save(user);
            return new ArrayList<>(user.getFavCities());
        }else {
            LOGGER.warn("User ID: " + user.getId() + " already contains favorite city " + newFavCity.toString());
            return new ArrayList<>(user.getFavCities());
        }
    }

    @Override
    public List<FavCity> getByUserID(long userId) {
        User user = userRepo.findById(userId);
        if(user == null){
            throw new IllegalArgumentException("The userID: " + userId + " is not available!!!");
        }
        return new ArrayList<>(user.getFavCities());
    }

    @Override
    public List<FavCity> updateCitiesByUserID(long userId, List<FavCity> favCities) {
        if (favCities != null){
            User user = userRepo.findById(userId);
            if(user == null){
                throw new IllegalArgumentException("The userID: " + userId + " is not available!!!");
            }
            user.setFavCities(new HashSet<>(favCities));
            LOGGER.info("UPDATE User ID: " + userId + " by FavCitiesList: " + favCities.toString());
            userRepo.save(user);
        }
        return favCities;
    }

    @Override
    public FavCity findOne(long id) {
        return favCityRepo.findById(id);
    }

    @Override
    public List<FavCity> list() {
        return favCityRepo.findAll();
    }
}
