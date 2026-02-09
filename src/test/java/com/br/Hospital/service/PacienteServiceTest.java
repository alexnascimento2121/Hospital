package com.br.Hospital.service;

import com.br.Hospital.entity.Paciente;
import com.br.Hospital.exception.RecursoNaoEncontradoException;
import com.br.Hospital.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PacienteServiceTest.class);

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void deveSalvarPaciente() {
        log.info("TESTE: deveSalvarPaciente");
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        when(pacienteRepository.save(any())).thenReturn(paciente);

        Paciente salvo = pacienteService.save(paciente);
        log.debug("Paciente retornado no teste: {}", salvo);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getId()).isEqualTo(1L);

        verify(pacienteRepository).save(paciente);
    }

    @Test
    void deveBuscarPorId() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        when(pacienteRepository.findById(1L))
                .thenReturn(Optional.of(paciente));

        Paciente resultado = pacienteService.findById(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrar() {
        log.info("TESTE: deveLancarExcecaoQuandoNaoEncontrar");
        when(pacienteRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> pacienteService.findById(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Paciente");
        log.info("Exceção lançada corretamente");
    }
}
