package com.br.Hospital.service;

import com.br.Hospital.dto.MedicoDTO;
import com.br.Hospital.entity.Medico;
import com.br.Hospital.exception.RecursoNaoEncontradoException;
import com.br.Hospital.mapper.MedicoMapper;
import com.br.Hospital.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    private static final Logger log = LoggerFactory.getLogger(MedicoService.class);

    private final MedicoRepository repository;
    private final MedicoMapper mapper;

    public MedicoService (MedicoRepository repository, MedicoMapper mapper){
        this.repository =repository;
        this.mapper = mapper;
    }
    public Medico save(Medico m) {
        log.info("Salvando Medico");
        log.debug("Dados do Medico: {}", m);
        return repository.save(m);
    }
    public List<Medico> findAll() {
        return repository.findAll();
    }
    public Medico findById(Long id) {
        log.info("Buscando Medico por id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Medico não encontrado id={}", id);
                    return new RecursoNaoEncontradoException("Medico não encontrado");
                });
    }

    @Transactional
    public Medico update(Long id, MedicoDTO dto) {
        Medico entity = findById(id);
        log.info("Atualizando objeto Medico id={}", id);

        Medico novo = mapper.enviaEntity(dto);
        novo.setId(entity.getId());

        return repository.save(novo);
    }

    // PATCH
    @Transactional
    public Medico patch(Long id, MedicoDTO dto) {
        Medico entity = findById(id);
        log.info("Atualizando dado especifico Medico id={}", id);
        mapper.atualizaEntityPorDTO(dto, entity);
        return entity;
    }

    public void delete(Long id) {
        log.info("Removendo Medico id={}", id);
        repository.deleteById(id);
        log.info("Medico removido com sucesso id={}", id);
    }
}
