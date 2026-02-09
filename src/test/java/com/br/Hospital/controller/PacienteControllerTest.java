package com.br.Hospital.controller;

import com.br.Hospital.dto.PacienteDTO;
import com.br.Hospital.entity.Paciente;
import com.br.Hospital.exception.GlobalExceptionHandler;
import com.br.Hospital.mapper.PacienteMapper;
import com.br.Hospital.mapper.PacienteMapperImpl;
import com.br.Hospital.repository.PacienteRepository;
import com.br.Hospital.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PacienteControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PacienteControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PacienteRepository pacienteRepository;

    @BeforeEach
    void setup() {
        log.info("Limpando Banco H2");
        pacienteRepository.deleteAll();
    }

    @Test
    void deveCriarPaciente() throws Exception {
        log.info("TESTE: deveCriarPaciente");
        PacienteDTO dto = new PacienteDTO();
        dto.setNome("Carlos");
        dto.setEmail("carlos@email.com");
        dto.setSenha("123456");
        dto.setCpf("12345678901");
        dto.setDataNascimento(LocalDate.of(1992, 8, 22));

        mockMvc.perform(post("/api/v1/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(jsonPath("$.email").value("carlos@email.com"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void deveBuscarPacientePorId() throws Exception {
        log.info("TESTE: deveBuscarPacientePorId");
        Paciente p = new Paciente();
        p.setNome("Ana");
        p.setEmail("ana@email.com");
        p.setSenha("123456");
        p.setCpf("11122233344");
        p.setDataNascimento(LocalDate.of(1990, 1, 1));

        pacienteRepository.save(p);

        mockMvc.perform(get("/api/v1/paciente/{id}", p.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@email.com"));
    }

    @Test
    void deveAtualizarPacienteComPut() throws Exception {
        log.info("TESTE: deveAtualizarPacienteComPut");
        Paciente p = new Paciente();
        p.setNome("Lucas");
        p.setEmail("lucas@email.com");
        p.setSenha("123456");
        p.setCpf("99988877766");
        p.setDataNascimento(LocalDate.of(1995, 5, 5));
        pacienteRepository.save(p);

        PacienteDTO dto = new PacienteDTO();
        dto.setNome("Lucas Atualizado");
        dto.setEmail("lucas.novo@email.com");
        dto.setSenha("123456");
        dto.setCpf("99988877766");

        mockMvc.perform(put("/api/v1/paciente/{id}", p.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Lucas Atualizado"))
                .andExpect(jsonPath("$.email").value("lucas.novo@email.com"));
    }

    @Test
    void deveAtualizarPacienteParcialmenteComPatch() throws Exception {
        log.info("TESTE: deveAtualizarPacienteParcialmenteComPatch");
        Paciente p = new Paciente();
        p.setNome("Maria");
        p.setEmail("maria@email.com");
        p.setSenha("123456");
        p.setCpf("11122233355");
        p.setDataNascimento(LocalDate.of(1988, 8, 8));
        pacienteRepository.save(p);

        mockMvc.perform(patch("/api/v1/paciente/{id}", p.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email":"maria.atualizado@email.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.email").value("maria.atualizado@email.com"));
    }

    @Test
    void deveDeletarPaciente() throws Exception {
        log.info("TESTE: deveDeletarPaciente");
        Paciente p = new Paciente();
        p.setNome("Pedro");
        p.setEmail("pedro@email.com");
        p.setSenha("123456");
        p.setCpf("55544433322");
        p.setDataNascimento(LocalDate.of(1993, 3, 3));
        pacienteRepository.save(p);

        mockMvc.perform(delete("/api/v1/paciente/{id}", p.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/paciente/{id}", p.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400QuandoDadosInvalidos() throws Exception {
        log.info("TESTE: deveRetornar400QuandoDadosInvalidos");
        mockMvc.perform(post("/api/v1/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "",
                                  "email": "email-invalido",
                                  "senha": "123",
                                  "cpf": "123"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void deveRetornar404QuandoPacienteNaoExiste() throws Exception {
        log.info("TESTE: deveRetornar404QuandoPacienteNaoExiste");
        mockMvc.perform(get("/api/v1/paciente/{id}", 9999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void deveRetornar500QuandoErroInterno() throws Exception {
        log.info("TESTE: deveRetornar500QuandoErroInterno");
        PacienteMapper mapper = new PacienteMapperImpl();


        PacienteService serviceQueLancaErro = new PacienteService(pacienteRepository, mapper) {
            @Override
            public Paciente findById(Long id) {
                throw new RuntimeException("Erro inesperado");
            }
        };


        PacienteController controllerDeTeste = new PacienteController(serviceQueLancaErro, mapper);

        MockMvc mockMvcTeste = MockMvcBuilders.standaloneSetup(controllerDeTeste)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();


        mockMvcTeste.perform(get("/api/v1/paciente/{id}", 1L))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Erro interno no servidor"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
