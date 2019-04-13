package com.study.scheduling.app.repository;

import com.study.scheduling.app.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {}
