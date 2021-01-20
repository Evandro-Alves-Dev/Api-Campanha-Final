package com.campanha.time.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CampanhaRequest {		
	
	private String nome;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataInicioVigencia;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataFimVigencia;
	
	private String time;
}
