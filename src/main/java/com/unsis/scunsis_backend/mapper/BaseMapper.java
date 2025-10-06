package com.unsis.scunsis_backend.mapper;

import java.util.List;

public interface BaseMapper<D, R, E> {
    // D = Dto response, R = Dto Request, E = Entity
    E toEntity(R dto);
    D toDto(E entity);
    List<D> toDtos(List<E> entities);
    void updateEntity(R request, E entity);
}
