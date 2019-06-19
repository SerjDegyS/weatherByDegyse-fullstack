package com.weatherbydegys.backend.dto;

import com.weatherbydegys.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class MapperUserDTO implements Mapper<UserDTO, User> {

    public MapperUserDTO() {
    }

    @Override
    public UserDTO convertToDTO(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setUid(entity.getUid());
        userDTO.setName(entity.getName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setPhotoURL(entity.getPhotoURL());
        userDTO.setLastVisit(entity.getLastVisit());
        userDTO.setRoles(entity.getRoles());
        return userDTO;
    }

    @Override
    public User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUid(dto.getUid());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhotoURL(dto.getPhotoURL());
        return user;
    }
}
