package com.study.scheduling.app.repository;

import com.study.scheduling.app.model.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<StudySchedule, Long> {

    @Query(value="SELECT study_schedules.id, patients.name AS patient_name, rooms.name AS room_name, doctors.name AS doctor_name, studies.status FROM study_schedules LEFT OUTER JOIN studies ON studies.id=study_schedules.study_id LEFT OUTER JOIN patients ON patients.id=studies.patient_id LEFT OUTER JOIN doctors ON doctors.id=study_schedules.doctor_id LEFT OUTER JOIN rooms ON rooms.id=study_schedules.room_id", nativeQuery=true)
    List<String> getAllSchedules();

}