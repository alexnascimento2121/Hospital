package com.br.Hospital.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteDTO {
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    private LocalDate dataNascimento;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
}
