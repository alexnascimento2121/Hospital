package com.br.Hospital.mapper;

import com.br.Hospital.dto.PacienteDTO;
import com.br.Hospital.entity.Paciente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    PacienteDTO enviaDTO(Paciente entity);

    Paciente enviaEntity(PacienteDTO dto);


    //patch
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizaEntityPorDTO(PacienteDTO dto, @MappingTarget Paciente entity);
}
