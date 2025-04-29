package com.mercadona.eanservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.mercadona.eanservice.dto.EanResponseDTO;
import com.mercadona.eanservice.entity.EanEntity;

@Mapper(componentModel = "spring")
public interface EanMapper {

    EanMapper INSTANCE = Mappers.getMapper(EanMapper.class);

    EanResponseDTO toDTO(EanEntity entity);

    EanEntity toEntity(EanResponseDTO dto);
}
