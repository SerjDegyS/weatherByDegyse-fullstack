package com.weatherbydegys.backend.repos;

import com.weatherbydegys.backend.model.FavCity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavCityRepo extends CrudRepository<FavCity, Integer>, JpaSpecificationExecutor<FavCity> {
    FavCity findById(long id);
    List<FavCity> findAll();
}
