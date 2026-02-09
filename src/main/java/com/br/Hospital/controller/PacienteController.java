package com.br.Hospital.controller;

import com.br.Hospital.dto.PacienteDTO;
import com.br.Hospital.mapper.PacienteMapper;
import com.br.Hospital.service.PacienteService;
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
@RequestMapping("/api/v1/paciente")
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);


    public PacienteController(PacienteService pacienteService, PacienteMapper pacienteMapper) {
        this.pacienteService = pacienteService;
        this.pacienteMapper = pacienteMapper;
    }

    @PostMapping
    public ResponseEntity<EntityModel<PacienteDTO>> create(@Valid @RequestBody PacienteDTO dto) {

        var salvo = pacienteService.save(pacienteMapper.enviaEntity(dto));
        var response = pacienteMapper.enviaDTO(salvo);

        log.info("POST /paciente");
        log.debug("Payload recebido: {}", dto);

        EntityModel<PacienteDTO> model = EntityModel.of(
                response,
                linkTo(methodOn(PacienteController.class).findById(salvo.getId())).withSelfRel(),
                linkTo(methodOn(PacienteController.class).findAll()).withRel("all"),
                linkTo(methodOn(PacienteController.class).create(null)).withRel("create"),
                linkTo(methodOn(PacienteController.class).update(salvo.getId(), null)).withRel("update"),
                linkTo(methodOn(PacienteController.class).patch(salvo.getId(), null)).withRel("patch"),
                linkTo(methodOn(PacienteController.class).delete(salvo.getId())).withRel("delete")
        );

        log.info("Paciente criado com id={}", salvo.getId());

        return ResponseEntity
                .created(linkTo(methodOn(PacienteController.class)
                        .findById(salvo.getId())).toUri())
                .body(model);
    }


    @GetMapping
    public List<EntityModel<PacienteDTO>> findAll() {

        log.info("GET /paciente");

        return pacienteService.findAll().stream()
                .map(p -> EntityModel.of(
                        pacienteMapper.enviaDTO(p),
                        linkTo(methodOn(PacienteController.class).findById(p.getId())).withSelfRel(),
                        linkTo(methodOn(PacienteController.class).update(p.getId(), null)).withRel("update"),
                        linkTo(methodOn(PacienteController.class).patch(p.getId(), null)).withRel("patch"),
                        linkTo(methodOn(PacienteController.class).delete(p.getId())).withRel("delete")
                ))
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public EntityModel<PacienteDTO> findById(@PathVariable Long id) {

        log.info("GET /paciente/{}", id);

        var p = pacienteService.findById(id);

        return EntityModel.of(
                pacienteMapper.enviaDTO(p),
                linkTo(methodOn(PacienteController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(PacienteController.class).findAll()).withRel("all"),
                linkTo(methodOn(PacienteController.class).create(null)).withRel("create"),
                linkTo(methodOn(PacienteController.class).update(id, null)).withRel("update"),
                linkTo(methodOn(PacienteController.class).patch(id, null)).withRel("patch"),
                linkTo(methodOn(PacienteController.class).delete(id)).withRel("delete")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<PacienteDTO> update(
            @PathVariable Long id,
            @RequestBody PacienteDTO dto) {

        log.info("PUT /paciente/{}", id);
        log.debug("Payload: {}", dto);

        var atualizado = pacienteService.update(id, dto);

        log.info("Paciente atualizado com id={}", atualizado.getId());

        return EntityModel.of(
                pacienteMapper.enviaDTO(atualizado),
                linkTo(methodOn(PacienteController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(PacienteController.class).findAll()).withRel("all"),
                linkTo(methodOn(PacienteController.class).patch(id, null)).withRel("patch"),
                linkTo(methodOn(PacienteController.class).delete(id)).withRel("delete")
        );
    }

    @PatchMapping("/{id}")
    public EntityModel<PacienteDTO> patch(
            @PathVariable Long id,
            @RequestBody PacienteDTO dto) {

        log.info("PATCH /paciente/{}", id);
        log.debug("Payload recebido: {}", dto);

        var atualizado = pacienteService.patch(id, dto);

        return EntityModel.of(
                pacienteMapper.enviaDTO(atualizado),
                linkTo(methodOn(PacienteController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(PacienteController.class).findAll()).withRel("all"),
                linkTo(methodOn(PacienteController.class).update(id, null)).withRel("update"),
                linkTo(methodOn(PacienteController.class).delete(id)).withRel("delete")
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /paciente/{}", id);
        pacienteService.delete(id);

        return ResponseEntity
                .noContent()
                .location(linkTo(methodOn(PacienteController.class).findAll()).toUri())
                .build();
    }
}
