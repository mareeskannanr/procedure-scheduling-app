package com.study.scheduling.app.utils;

import com.study.scheduling.app.exception.AppException;
import com.study.scheduling.app.model.Doctor;
import com.study.scheduling.app.model.Patient;
import com.study.scheduling.app.model.Room;
import com.study.scheduling.app.model.dto.ScheduleDTO;
import com.study.scheduling.app.repository.DoctorRepository;
import com.study.scheduling.app.repository.PatientRepository;
import com.study.scheduling.app.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CommonUtils {

    private final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static void setupRooms(RoomRepository roomRepository) {
        roomRepository.save(new Room(1L, "Room No.1"));
        roomRepository.save(new Room(2L, "Room No.2"));
        roomRepository.save(new Room(3L, "Room No.3"));
        roomRepository.save(new Room(4L, "Room No.4"));
        roomRepository.save(new Room(5L, "Room No.5"));
        logger.info("Rooms Data Setup Done!");
    }

    public static void setupDoctors(DoctorRepository doctorRepository) {
        doctorRepository.save(new Doctor(1L, "Dr.Marry"));
        doctorRepository.save(new Doctor(2L, "Dr.Anna"));
        doctorRepository.save(new Doctor(3L, "Dr.Mathews"));
        doctorRepository.save(new Doctor(4L, "Dr.John"));
        doctorRepository.save(new Doctor(5L, "Dr.Paul"));
        logger.info("Doctors Data Setup Done!");
    }

    /**
     * Check for Patient details existence in repository
     *
     * @param schedule
     * @param patientRepository
     * @return
     */
    public static Patient getPatient(ScheduleDTO schedule, PatientRepository patientRepository) {
        Optional<Patient> patientOptional = patientRepository.findById(schedule.getPatientId());

        if (!patientOptional.isPresent()) {
            throw new AppException(AppConstants.PATIENT_NOT_EXISTS);
        }

        return patientOptional.get();
    }

    /**
     * Check for Room details existence in repository
     *
     * @param schedule
     * @param roomRepository
     * @return
     */
    public static Room getRoom(ScheduleDTO schedule, RoomRepository roomRepository) {
        Optional<Room> roomOptional = roomRepository.findById(schedule.getRoomId());
        if (!roomOptional.isPresent()) {
            throw new AppException(AppConstants.ROOM_NOT_EXISTS);
        }

        return roomOptional.get();
    }

    /**
     * Check for Doctor details existence in repository
     *
     * @param schedule
     * @param doctorRepository
     * @return
     */
    public static Doctor getDoctor(ScheduleDTO schedule, DoctorRepository doctorRepository) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(schedule.getDoctorId());
        if (!doctorOptional.isPresent()) {
            throw new AppException(AppConstants.DOCTOR_NOT_EXISTS);
        }

        return doctorOptional.get();
    }

}
