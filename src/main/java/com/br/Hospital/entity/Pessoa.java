package com.br.Hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome requerido")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email requerido")
    @Email(message = "Email invalido")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 6)
    @NotBlank(message = "Senha requerido")
    @Column(nullable = false)
    private String senha;


    @Column
    public LocalDate dataNascimento;

}