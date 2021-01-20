package com.campanha.time.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TB_CAMPANHA")
public class Campanha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataInicioVigencia;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataFimVigencia;

	@ManyToOne
	private Time time;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_campanha_sociotorcedor",
			joinColumns = {
			@JoinColumn(name = "id_campanha", referencedColumnName = "id", nullable = false, updatable = false)
			},
			inverseJoinColumns = {
			@JoinColumn(name = "id_sociotorcedor", referencedColumnName = "id", nullable = false, updatable = false)
			}
	)
	private List<SocioTorcedor> socioTorcedor = new ArrayList<>();
}
