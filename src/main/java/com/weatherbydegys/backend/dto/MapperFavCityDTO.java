package com.weatherbydegys.backend.dto;

import com.weatherbydegys.backend.model.FavCity;
import org.springframework.stereotype.Component;

@Component
public class MapperFavCityDTO implements Mapper<FavCityDTO, FavCity> {
    @Override
    public FavCityDTO convertToDTO(FavCity entity) {
        FavCityDTO favCityDTO = new FavCityDTO();
        favCityDTO.setId(String.valueOf(entity.getId()));
        favCityDTO.setName(entity.getName());
        favCityDTO.setCountry(entity.getCountry());
        return favCityDTO;
    }

    @Override
    public FavCity convertToEntity(FavCityDTO dto) {
        FavCity favCity = new FavCity();
        favCity.setId(Long.parseLong(dto.getId()));
        favCity.setName(dto.getName());
        favCity.setCountry(dto.getCountry());
        return favCity;
    }
}
