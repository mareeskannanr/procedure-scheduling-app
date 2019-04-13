package com.study.scheduling.app;

import com.study.scheduling.app.model.Patient;
import com.study.scheduling.app.model.Study;
import com.study.scheduling.app.model.StudySchedule;
import com.study.scheduling.app.repository.*;
import com.study.scheduling.app.utils.AppConstants;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

    private static final String PATIENT_URL = "/api/patient";
    private static final String SCHEDULE_URL = "/api/schedule";

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
    public void saveEmptyPatient() throws Exception {
        MockHttpServletResponse response = TestUtils.postObject(mockMvc, "{}", PATIENT_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(1, resultArray.length());
        assertEquals(AppConstants.NAME_REQUIRED, resultArray.getString(0));
    }

    @Test
    public void savePatientWithEmptyName() throws Exception {
        String patient = "{\"name\": \"    \"}";
        MockHttpServletResponse response = TestUtils.postObject(mockMvc, patient, PATIENT_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(1, resultArray.length());
        assertEquals(AppConstants.NAME_REQUIRED, resultArray.getString(0));
    }

    @Test
    public void savePatientWithWrongValues() throws Exception {
        String patient = "{\"name\": \"Sam\", \"sex\": \"bbb\", \"dob\": \"aaa\"}";
        MockHttpServletResponse response = TestUtils.postObject(mockMvc, patient, PATIENT_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(1, resultArray.length());
        assertEquals(AppConstants.INVALID_POST_MSG, resultArray.getString(0));
    }

    @Test
    public void savePatientWithCorrectValues() throws Exception {
        JSONObject patient = new JSONObject("{\"name\": \"A.Cook\", \"sex\": \"Male\", \"dob\": \"1990-10-21\"}");
        doReturn(TestUtils.createPatient()).when(patientRepository).save(any(Patient.class));
        MockHttpServletResponse response = TestUtils.postObject(mockMvc, patient.toString(), PATIENT_URL);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        JSONObject result = new JSONObject(response.getContentAsString());
        assertEquals(patient.get("name"), result.get("name"));
        assertEquals(patient.get("sex"), result.get("sex"));
        assertEquals(patient.get("dob"), result.get("dob"));
    }

    @Test
    public void emptyScheduleObject() throws Exception {
        MockHttpServletResponse response = TestUtils.postObject(mockMvc, "{}", SCHEDULE_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray result = new JSONArray(response.getContentAsString());
        List<String> errorList = Arrays.asList(new String[]{
                AppConstants.ROOM_REQUIRED, AppConstants.DOCTOR_REQUIRED, AppConstants.PATIENT_ID_REQUIRED,
                AppConstants.PLANNED_START_TIME, AppConstants.DESCRIPTION_REQUIRED
        });

        for (int i = 0, length = result.length(); i < length; i++) {
            assertTrue(errorList.contains(result.get(i)));
        }
    }

    @Test
    public void nonExistPatientCheck() throws Exception {
        JSONObject schedule = TestUtils.createScheduleJSON();
        MockHttpServletResponse response = TestUtils.postObject(mockMvc, schedule.toString(), SCHEDULE_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(AppConstants.PATIENT_NOT_EXISTS, resultArray.get(0));
    }

    @Test
    public void invalidTimeCheck() throws Exception {
        JSONObject schedule = TestUtils.createScheduleJSON();
        schedule.put("estimatedEndTime", LocalDateTime.of(2019,01, 01, 1,50).toString());

        MockHttpServletResponse response = TestUtils.postObject(mockMvc, schedule.toString(), SCHEDULE_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(AppConstants.TIME_DIFF_ERROR, resultArray.get(0));
    }

    @Test
    public void nonExistDoctorCheck() throws Exception {
        JSONObject schedule = TestUtils.createScheduleJSON();
        doReturn(Optional.of(TestUtils.createPatient())).when(patientRepository).findById(any(Long.class));

        MockHttpServletResponse response = TestUtils.postObject(mockMvc, schedule.toString(), SCHEDULE_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(AppConstants.DOCTOR_NOT_EXISTS, resultArray.get(0));
    }

    @Test
    public void nonExistRoomCheck() throws Exception {
        JSONObject schedule = TestUtils.createScheduleJSON();
        System.out.println(schedule);

        doReturn(Optional.of(TestUtils.createPatient())).when(patientRepository).findById(any(Long.class));
        doReturn(Optional.of(TestUtils.createDoctorList().get(0))).when(doctorRepository).findById(any(Long.class));


        MockHttpServletResponse response = TestUtils.postObject(mockMvc, schedule.toString(), SCHEDULE_URL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        JSONArray resultArray = new JSONArray(response.getContentAsString());
        assertEquals(AppConstants.ROOM_NOT_EXISTS, resultArray.get(0));
    }

    @Test
    public void correctScheduleCheck() throws Exception {
        JSONObject schedule = TestUtils.createScheduleJSON();
        StudySchedule studySchedule = TestUtils.createStudySchedule();
        doReturn(Optional.of(TestUtils.createPatient())).when(patientRepository).findById(any(Long.class));
        doReturn(Optional.of(TestUtils.createDoctorList().get(0))).when(doctorRepository).findById(any(Long.class));
        doReturn(Optional.of(TestUtils.createRoomList().get(0))).when(roomRepository).findById(any(Long.class));
        doReturn(studySchedule.getStudy()).when(studyRepository).save(any(Study.class));
        doReturn(studySchedule).when(scheduleRepository).save(any(StudySchedule.class));

        MockHttpServletResponse response = TestUtils.postObject(mockMvc, schedule.toString(), SCHEDULE_URL);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertNotNull(response.getContentAsString());

        JSONObject studyScheduleObject = new JSONObject(response.getContentAsString());
        assertEquals(schedule.getString("description"), studyScheduleObject.getJSONObject("study").getString("description"));
    }

}
