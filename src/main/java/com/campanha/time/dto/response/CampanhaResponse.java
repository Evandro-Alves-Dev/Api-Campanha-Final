package com.campanha.time.dto.response;

import com.campanha.time.entities.Time;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
public class CampanhaResponse {

	private Long id;
	
	private String nome;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataInicioVigencia;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataFimVigencia;

	@ManyToOne
	private Time time;
	
	private String message;
}
