package fr.marc.patientInfo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.marc.patientInfo.model.Patient;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PatientInfoControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(PatientInfoControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	// GET /PatientInfo/list
	@Nested
	class GetPatients {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/list"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("TestBorderline")))
				.andExpect(jsonPath("$[1].family", is("TestEarlyOnset")))
				.andExpect(jsonPath("$[2].family", is("TestInDanger")))
				.andExpect(jsonPath("$[3].family", is("TestNone")));
		}
	}
	
	// GET /PatientInfo/byId
	@Nested
	class GetPatientById {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/byId?id=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family", is("TestNone")));
		}
		
		@Test
		public void no_patient () throws Exception {
			mockMvc.perform(get("/PatientInfo/byId?id=999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family").doesNotExist());
		}
	}
	
	// GET /PatientInfo/byName
	@Nested
	class GetPatientByName {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=testnone&&given=test"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].family", is("TestNone")));
		}
		
		@Test
		public void no_patient () throws Exception {
			mockMvc.perform(get("/PatientInfo/byName?family=nemo&&given=test"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
		}
	}
	
	// POST /PatientInfo/update
	@Nested
	class UpdatePatient {
		@Test
		public void address_and_phone_updated () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient updatedPatient = Patient.builder()
					.family("TestNone")
					.given("test")
					.dob("1966-12-31")
					.sex("F")
					.address("Address change")
					.phone("Phone change")
					.build();
			mockMvc.perform(post("/PatientInfo/update?id=1")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is("Address change")))
				.andExpect(jsonPath("$.phone", is("Phone change")));
		}
		
		@Test
		public void no_patient_with_this_id_should_return_nothing () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient updatedPatient = Patient.builder()
					.family("last")
					.given("first")
					.dob("2000-01-01")
					.sex("F")
					.address("Address")
					.phone("Phone")
					.build();
			MvcResult result = mockMvc.perform(post("/PatientInfo/update?id=15")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist())
				.andReturn();
			assertThat(result.getResponse().getContentAsString())
				.isEqualTo("");
		}
		
		@Test
		public void patient_already_exist_should_return_new_patient () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient updatedPatient = Patient.builder()
					.family("TestNone")
					.given("test")
					.dob("1966-12-31")
					.sex("F")
					.address("Address change")
					.phone("Phone change")
					.build();
			mockMvc.perform(post("/PatientInfo/update?id=3")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", nullValue()));
		}
	}
	
	
	// POST /PatientInfo/add
	@Nested
	class CreatePatient {
		@Test
		public void success () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Patient createdPatient = Patient.builder()
					.family("ZewPatient")
					.given("test")
					.dob("1966-12-31")
					.sex("F")
					.address("Address")
					.phone("Phone")
					.build();
			mockMvc.perform(post("/PatientInfo/add")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(createdPatient)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family", is("ZewPatient")))
				.andExpect(jsonPath("$.address", is("Address")))
				.andExpect(jsonPath("$.phone", is("Phone")));
		}
	}

}
