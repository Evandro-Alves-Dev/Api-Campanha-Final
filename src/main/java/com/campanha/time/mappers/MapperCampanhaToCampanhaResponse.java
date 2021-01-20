package com.campanha.time.mappers;

import org.mapstruct.Mapper;

import com.campanha.time.dto.response.CampanhaResponse;
import com.campanha.time.entities.Campanha;

@Mapper(componentModel = "spring", uses = {MapperTimeToTimeResponse.class})
public interface MapperCampanhaToCampanhaResponse {
	 CampanhaResponse toDto(Campanha campanha);
}