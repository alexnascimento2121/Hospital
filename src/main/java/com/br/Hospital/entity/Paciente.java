package com.br.Hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Paciente extends Pessoa{

    @NotBlank(message = "CPF obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
}
