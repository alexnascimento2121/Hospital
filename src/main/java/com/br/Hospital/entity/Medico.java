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
public class Medico extends Pessoa{

    @NotBlank(message = "CRM obrigatório")
    @Size(min = 6, max = 6, message = "CRM deve ter 6 dígitos")
    @Column(nullable = false, length = 6)
    private String crm;

    @NotBlank(message = "Conselho Regional obrigatório")
    @Size(min = 2, max = 2, message = "Conselho Regional deve ter uma sigla")
    @Column(nullable = false,length = 2)
    private String conselhoRegional;
}
