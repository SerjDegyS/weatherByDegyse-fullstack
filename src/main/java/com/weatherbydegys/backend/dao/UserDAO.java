package com.weatherbydegys.backend.dao;

//import com.weatherbydegys.backend.model.FavCity;
import com.weatherbydegys.backend.model.User;

import java.util.List;

public interface UserDAO {
    User add(User user);
    User getById(long id);
    List<User> getByName(String name);
    void delete(User user);
    List<User> getAll();
    User update(User user);
}
