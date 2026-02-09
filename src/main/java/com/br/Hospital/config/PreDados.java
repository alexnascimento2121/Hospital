package com.br.Hospital.config;

import com.br.Hospital.entity.Medico;
import com.br.Hospital.entity.Paciente;
import com.br.Hospital.repository.MedicoRepository;
import com.br.Hospital.repository.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class PreDados implements CommandLineRunner {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public PreDados(PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void run(String... args) {
        Paciente p1 = new Paciente();
        p1.setNome("João Silva");
        p1.setEmail("joao@mail.com");
        p1.setCpf("01234567890");
        p1.setSenha("123456");
        p1.setDataNascimento(LocalDate.of(1990, 5, 10));

        Paciente p2 = new Paciente();
        p2.setNome("Maria Souza");
        p2.setEmail("maria@mail.com");
        p2.setCpf("98765432109");
        p2.setSenha("321654");
        p2.setDataNascimento(LocalDate.of(1985, 8, 22));

        pacienteRepository.save(p1);
        pacienteRepository.save(p2);

        Medico m1 = new Medico();
        m1.setNome("Alex Silva");
        m1.setEmail("alex@mail.com");
        m1.setCrm("123456");
        m1.setSenha("321654");
        m1.setConselhoRegional("sp");
        m1.setDataNascimento(LocalDate.of(1992, 8, 22));

        medicoRepository.save(m1);
    }
}
