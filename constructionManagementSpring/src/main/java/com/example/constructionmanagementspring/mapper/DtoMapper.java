package com.example.constructionmanagementspring.mapper;

public interface DtoMapper<T, E> {
    T toEntity(E dto);

    E toDto(T entity);

}
