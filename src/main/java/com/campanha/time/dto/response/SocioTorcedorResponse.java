package com.campanha.time.dto.response;

import com.campanha.time.entities.Time;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SocioTorcedorResponse {

    private Long id;

    private String nome;

    private String email;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    private Time time;

//    @ManyToOne
//    private Campanha campanha;

    private String message;
}

