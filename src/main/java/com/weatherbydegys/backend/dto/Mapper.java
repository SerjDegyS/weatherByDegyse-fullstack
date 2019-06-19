package com.weatherbydegys.backend.dto;

public interface Mapper<T, E> {

    public T convertToDTO(E entity);
    public E convertToEntity(T dto);

}
