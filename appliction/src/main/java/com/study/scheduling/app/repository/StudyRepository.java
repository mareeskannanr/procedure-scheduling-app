package com.study.scheduling.app.repository;

import com.study.scheduling.app.model.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {}
