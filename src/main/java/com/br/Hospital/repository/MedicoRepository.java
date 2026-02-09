package com.br.Hospital.repository;

import com.br.Hospital.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
