package com.br.Hospital.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicoDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String crm;
    private String conselhoRegional;
}
