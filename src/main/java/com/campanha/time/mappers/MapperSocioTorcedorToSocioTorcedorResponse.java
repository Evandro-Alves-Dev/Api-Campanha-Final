package com.campanha.time.mappers;

import com.campanha.time.dto.response.SocioTorcedorResponse;
import com.campanha.time.entities.SocioTorcedor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperSocioTorcedorToSocioTorcedorResponse {
        SocioTorcedorResponse toDto(SocioTorcedor socioTorcedor);

}
