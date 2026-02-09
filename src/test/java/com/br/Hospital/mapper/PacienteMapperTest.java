package com.br.Hospital.mapper;

import com.br.Hospital.dto.PacienteDTO;
import com.br.Hospital.entity.Paciente;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class PacienteMapperTest {
    private PacienteMapper mapper = Mappers.getMapper(PacienteMapper.class);

    @Test
    void deveConverterEntityParaDTO() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João1");
        paciente.setEmail("joao1@email.com");

        PacienteDTO dto = mapper.enviaDTO(paciente);

        assertThat(dto).isNotNull();
        assertThat(dto.getNome()).isEqualTo("João1");
        assertThat(dto.getEmail()).isEqualTo("joao1@email.com");
    }

    @Test
    void deveConverterDTOParaEntity() {
        PacienteDTO dto = new PacienteDTO();
        dto.setNome("Maria");
        dto.setEmail("maria@email.com");

        Paciente entity = mapper.enviaEntity(dto);

        assertThat(entity.getNome()).isEqualTo("Maria");
        assertThat(entity.getEmail()).isEqualTo("maria@email.com");
    }

    @Test
    void deveAtualizarSomenteCamposNaoNulos() {
        Paciente entity = new Paciente();
        entity.setNome("Antigo");
        entity.setEmail("old@email.com");

        PacienteDTO dto = new PacienteDTO();
        dto.setNome("Novo");

        mapper.atualizaEntityPorDTO(dto, entity);

        assertThat(entity.getNome()).isEqualTo("Novo");
        assertThat(entity.getEmail()).isEqualTo("old@email.com");
    }
}
