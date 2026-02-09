package com.br.Hospital.repository;

import com.br.Hospital.entity.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PacienteRepositoryTest {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    void deveSalvarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNome("Teste");
        paciente.setEmail("teste@email.com");
        paciente.setSenha("123456");
        paciente.setCpf("12345678901");
        paciente.setDataNascimento(LocalDate.of(1992, 8, 22));

        Paciente salvo = pacienteRepository.save(paciente);

        assertThat(salvo.getId()).isNotNull();
    }

    @Test
    void deveBuscarPorCpf() {
        Paciente paciente = new Paciente();
        paciente.setNome("Teste");
        paciente.setEmail("cpf@email.com");
        paciente.setSenha("123456");
        paciente.setCpf("99988877766");
        paciente.setDataNascimento(LocalDate.of(1992, 8, 22));

        pacienteRepository.save(paciente);

        Optional<Paciente> encontrado =
                pacienteRepository.findByCpf("99988877766");

        assertThat(encontrado).isPresent();
    }
}
