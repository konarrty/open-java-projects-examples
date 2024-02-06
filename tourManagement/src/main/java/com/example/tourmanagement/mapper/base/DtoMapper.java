package com.example.tourmanagement.mapper.base;

public interface DtoMapper<T, E> {

    E toDTO(T entity);

    T toEntity(E dto);


}
