package com.study.scheduling.app;

import com.study.scheduling.app.model.StudySchedule;
import com.study.scheduling.app.repository.*;
import com.study.scheduling.app.utils.TestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UIApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private StudyRepository studyRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @Test
    public void getAllRooms() throws Exception {
        doReturn(TestUtils.createRoomList()).when(roomRepository).findAll();
        MvcResult result = mockMvc.perform(get("/api/rooms")).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        JSONArray resultArray = new JSONArray(result.getResponse().getContentAsString());
        assertEquals(3, resultArray.length());
    }

    @Test
    public void getAllDoctors() throws Exception {
        doReturn(TestUtils.createDoctorList()).when(doctorRepository).findAll();
        MvcResult result = mockMvc.perform(get("/api/doctors")).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        JSONArray resultArray = new JSONArray(result.getResponse().getContentAsString());
        assertEquals(3, resultArray.length());
    }

    @Test
    public void getAllPatients() throws Exception {
        doReturn(TestUtils.createPatientList()).when(patientRepository).findAll();
        MvcResult result = mockMvc.perform(get("/api/patients")).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        JSONArray resultArray = new JSONArray(result.getResponse().getContentAsString());
        assertEquals(1, resultArray.length());
    }

    @Test
    public void getAllSchedules() throws Exception {
        doReturn(TestUtils.createScheduleList()).when(scheduleRepository).getAllSchedules();
        MvcResult result = mockMvc.perform(get("/api/schedules")).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        JSONArray resultArray = new JSONArray(result.getResponse().getContentAsString());
        assertEquals(1, resultArray.length());
    }

    @Test
    public void getScheduleByIdWithNullResult() throws Exception {
        doReturn(Optional.empty()).when(scheduleRepository).findById(any(Long.class));
        MvcResult result = mockMvc.perform(get("/api/schedule/1")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void getScheduleByIdWithExpectedResult() throws Exception {
        StudySchedule studySchedule = TestUtils.createStudySchedule();
        doReturn(Optional.of(studySchedule)).when(scheduleRepository).findById(any(Long.class));
        MvcResult result = mockMvc.perform(get("/api/schedule/1")).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        JSONObject schedule = new JSONObject(result.getResponse().getContentAsString());
        assertEquals(schedule.getLong("id"), studySchedule.getId().longValue());
        assertTrue(schedule.has("study"));
        assertTrue(schedule.has("doctor"));
        assertTrue(schedule.has("room"));
    }

}
