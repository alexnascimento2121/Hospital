package com.br.Hospital.service;

import com.br.Hospital.dto.PacienteDTO;
import com.br.Hospital.entity.Paciente;
import com.br.Hospital.exception.RecursoNaoEncontradoException;
import com.br.Hospital.mapper.PacienteMapper;
import com.br.Hospital.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository repository;
    private final PacienteMapper mapper;

    public PacienteService(PacienteRepository repository, PacienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Paciente save(Paciente p) {
        log.info("Salvando paciente");
        log.debug("Dados do paciente: {}", p);
        return repository.save(p);
    }
    public List<Paciente> findAll() {
        return repository.findAll();
    }
    public Paciente findById(Long id) {
        log.info("Buscando paciente por id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado id={}", id);
                    return new RecursoNaoEncontradoException("Paciente não encontrado");
                });
    }

    @Transactional
    public Paciente update(Long id, PacienteDTO dto) {
        Paciente entity = findById(id);

        log.info("Atualizando objeto paciente id={}", id);

        Paciente novo = mapper.enviaEntity(dto);
        novo.setId(entity.getId());

        return repository.save(novo);
    }
    @Transactional
    public Paciente patch(Long id, PacienteDTO dto) {
        Paciente entity = findById(id);
        log.info("Atualizando atributo paciente id={}", id);
        mapper.atualizaEntityPorDTO(dto, entity);
        return entity;
    }

    public void delete(Long id) {
        log.info("Removendo paciente id={}", id);
        repository.deleteById(id);
        log.info("Paciente removido com sucesso id={}", id);
    }
}
