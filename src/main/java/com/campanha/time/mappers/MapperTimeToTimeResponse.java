package com.campanha.time.mappers;

import org.mapstruct.Mapper;

import com.campanha.time.dto.response.TimeResponse;
import com.campanha.time.entities.Time;

@Mapper(componentModel = "spring")
public interface MapperTimeToTimeResponse {
	TimeResponse toDto(Time time);
}