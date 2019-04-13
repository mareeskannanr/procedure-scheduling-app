package com.study.scheduling.app.utils;

import com.study.scheduling.app.model.*;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static MockHttpServletResponse postObject(MockMvc mockMvc, String data,  String url) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(data)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        return result.getResponse();
    }

    public static JSONObject createScheduleJSON() throws Exception {
        JSONObject schedule = new JSONObject();
        schedule.put("patientId", 1);
        schedule.put("description", "Treatment");
        schedule.put("plannedStartTime", LocalDateTime.now());
        schedule.put("estimatedEndTime", LocalDateTime.now().plusHours(10));
        schedule.put("doctorId", 1);
        schedule.put("roomId", 1);

        return schedule;
    }

    public static Patient createPatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("A.Cook");
        patient.setSex(Sex.MALE);
        patient.setDob(LocalDate.of(1990, 10, 21));
        return patient;
    }

    public static List<Room> createRoomList() throws Exception {
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(1L, "Room No.1"));
        roomList.add(new Room(2L, "Room No.2"));
        roomList.add(new Room(3L, "Room No.3"));
        return roomList;
    }

    public static List<Doctor> createDoctorList() throws Exception {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(new Doctor(1L, "Dr. Simmons"));
        doctorList.add(new Doctor(2L, "Dr. Helena"));
        doctorList.add(new Doctor(3L, "Dr. Robby"));
        return doctorList;
    }

    public static List<Patient> createPatientList() throws Exception {
        return Arrays.asList(createPatient());
    }

    public static List<StudySchedule> createScheduleList() throws Exception {
        return Arrays.asList(createStudySchedule());
    }

    public static StudySchedule createStudySchedule() throws Exception {
        Study study = new Study();
        study.setPatient(createPatient());
        study.setDescription("Treatment");
        study.setId(1L);
        study.setPlannedStartTime(LocalDateTime.now());
        study.setEstimatedEndTime(LocalDateTime.now().plusHours(10));

        study.setStatus(Status.PLANNED);

        StudySchedule studySchedule = new StudySchedule();
        studySchedule.setStudy(study);
        studySchedule.setRoom(createRoomList().get(0));
        studySchedule.setDoctor(createDoctorList().get(0));
        studySchedule.setId(1L);

        return studySchedule;
    }

}
