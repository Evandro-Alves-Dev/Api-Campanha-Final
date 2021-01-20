package com.campanha.time.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TB_SOCIOTORCEDOR")
public class SocioTorcedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private String email;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataNascimento;

	@NotNull
	@ManyToOne
	private Time time;

//	@ManyToOne
//	private Campanha campanha;
}
