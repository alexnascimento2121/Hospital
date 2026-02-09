package com.br.Hospital.mapper;

import com.br.Hospital.dto.MedicoDTO;
import com.br.Hospital.entity.Medico;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    MedicoDTO enviaDTO(Medico entity);

    Medico enviaEntity(MedicoDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "crm", ignore = true)
    @Mapping(target = "conselhoRegional", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizaEntityPorDTO(MedicoDTO dto, @MappingTarget Medico entity);

}
