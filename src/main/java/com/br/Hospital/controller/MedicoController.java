package com.br.Hospital.controller;

import com.br.Hospital.dto.MedicoDTO;
import com.br.Hospital.mapper.MedicoMapper;
import com.br.Hospital.service.MedicoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping("/api/v1/medico")
public class MedicoController {
    private static final Logger log = LoggerFactory.getLogger(MedicoController.class);

    private final MedicoService medicoService;

    private final MedicoMapper medicoMapper;

    public MedicoController(MedicoService medicoService, MedicoMapper medicoMapper) {
        this.medicoService = medicoService;
        this.medicoMapper = medicoMapper;
    }

    @PostMapping
    public ResponseEntity<EntityModel<MedicoDTO>> create(@Valid @RequestBody MedicoDTO dto) {
        var salvo = medicoService.save(medicoMapper.enviaEntity(dto));
        var response = medicoMapper.enviaDTO(salvo);

        log.info("POST /medico");
        log.debug("Payload recebido para CRIAR: {}", dto);

        EntityModel<MedicoDTO> model = EntityModel.of(
                response,
                linkTo(methodOn(MedicoController.class).findById(salvo.getId())).withSelfRel(),
                linkTo(methodOn(MedicoController.class).findAll()).withRel("all"),
                linkTo(methodOn(MedicoController.class).create(null)).withRel("create"),
                linkTo(methodOn(MedicoController.class).update(salvo.getId(), null)).withRel("update"),
                linkTo(methodOn(MedicoController.class).patch(salvo.getId(), null)).withRel("patch"),
                linkTo(methodOn(MedicoController.class).delete(salvo.getId())).withRel("delete")
        );

        log.info("Medico criado com id={}", salvo.getId());

        return ResponseEntity
                .created(linkTo(methodOn(MedicoController.class)
                        .findById(salvo.getId())).toUri())
                .body(model);
    }


    @GetMapping
    public List<EntityModel<MedicoDTO>> findAll() {
        log.info("GET /medico");

        return medicoService.findAll().stream()
                .map(m -> EntityModel.of(
                        medicoMapper.enviaDTO(m),
                        linkTo(methodOn(MedicoController.class).findById(m.getId())).withSelfRel(),
                        linkTo(methodOn(MedicoController.class).update(m.getId(), null)).withRel("update"),
                        linkTo(methodOn(MedicoController.class).patch(m.getId(), null)).withRel("patch"),
                        linkTo(methodOn(MedicoController.class).delete(m.getId())).withRel("delete")
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EntityModel<MedicoDTO> findById(@PathVariable Long id) {
        log.info("GET /medico/{}", id);

        var m = medicoService.findById(id);

        return EntityModel.of(
                medicoMapper.enviaDTO(m),
                linkTo(methodOn(MedicoController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(MedicoController.class).findAll()).withRel("all"),
                linkTo(methodOn(MedicoController.class).create(null)).withRel("create"),
                linkTo(methodOn(MedicoController.class).update(id, null)).withRel("update"),
                linkTo(methodOn(MedicoController.class).patch(id, null)).withRel("patch"),
                linkTo(methodOn(MedicoController.class).delete(id)).withRel("delete")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<MedicoDTO> update(@PathVariable Long id,@RequestBody MedicoDTO dto) {
        log.info("PUT /medico/{}", id);
        log.debug("Payload recebido para UPDATE: {}", dto);

        var atualizado = medicoService.update(id, dto);

        log.info("Objeto Medico atualizado com id={}", atualizado.getId());

        return EntityModel.of(
                medicoMapper.enviaDTO(atualizado),
                linkTo(methodOn(MedicoController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(MedicoController.class).findAll()).withRel("all"),
                linkTo(methodOn(MedicoController.class).patch(id, null)).withRel("patch"),
                linkTo(methodOn(MedicoController.class).delete(id)).withRel("delete")
        );
    }

    @PatchMapping("/{id}")
    public EntityModel<MedicoDTO> patch(@PathVariable Long id,@RequestBody MedicoDTO dto) {
        log.info("PATCH /medico/{}", id);
        log.debug("Payload recebido para PATCH: {}", dto);

        var atualizado = medicoService.patch(id, dto);

        log.info("Atributo Medico atualizado com id={}", atualizado.getId());

        return EntityModel.of(
                medicoMapper.enviaDTO(atualizado),
                linkTo(methodOn(MedicoController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(MedicoController.class).findAll()).withRel("all"),
                linkTo(methodOn(MedicoController.class).update(id, null)).withRel("update"),
                linkTo(methodOn(MedicoController.class).delete(id)).withRel("delete")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /medico/{}", id);
        log.debug("LONG recebido para DELETE: {}", id);

        medicoService.delete(id);

        return ResponseEntity
                .noContent()
                .location(linkTo(methodOn(MedicoController.class).findAll()).toUri())
                .build();
    }
}
