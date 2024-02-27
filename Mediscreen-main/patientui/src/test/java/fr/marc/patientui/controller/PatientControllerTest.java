package fr.marc.patientui.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.beans.PatientAndNotesBean;
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.beans.PatientBeanDTO;
import fr.marc.patientui.beans.ReportBean;
import fr.marc.patientui.proxies.PatientInfoProxy;
import fr.marc.patientui.proxies.PatientNoteProxy;
import fr.marc.patientui.proxies.PatientReportProxy;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
	
	@Autowired
	private PatientController patientController;
	
	@Autowired
	private MockMvc mockMvc;
	   
	@MockBean
	private PatientInfoProxy patientInfoProxy;
	
	@MockBean
	private PatientNoteProxy patientNoteProxy;
	
	@MockBean
	private PatientReportProxy patientReportProxy;
	
	private static final Logger log = LoggerFactory.getLogger(PatientControllerTest.class); 
	private PatientBean patient1;
	private PatientBean patient2;
	
	@BeforeEach
	public void init() {
		patient1 = PatientBean.builder()
				.id(1)
				.family("last1")
				.given("first1")
				.dob("2001-01-01")
				.sex("F")
				.address("Address1")
				.phone("111111")
				.build();
		patient2 = PatientBean.builder()
				.id(2)
				.family("last2")
				.given("first2")
				.dob("2002-02-02")
				.sex("M")
				.address("Address2")
				.phone("222222")
				.build();
	}
	
	@Test
	void patientController_is_correctely_called()throws Exception {
	   assertThat(patientController).isNotNull();
	   }

	@Test
	public void displayHomePage() throws Exception {
		mockMvc.perform(get("/"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("Home"))
        	.andExpect(content().string(containsString("Welcome")));
	}
	
	@Test
	public void displayPatientListPage() throws Exception {
		when(patientInfoProxy.getPatients())
			.thenReturn(List.of(patient1, patient2));
		mockMvc.perform(get("/PatientList"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("PatientList"))
        	.andExpect(content().string(containsString("Patient list")))
        	.andExpect(content().string(containsString("last1")))
        	.andExpect(content().string(containsString("last2")));
	}
	
	@Nested
	class displayPatientSearchPage {
		@Test
		public void blanck_home_page() throws Exception {
			when(patientInfoProxy.getPatientsByFamilyAndGiven("last1","first1"))
				.thenReturn(List.of(patient1));
			mockMvc.perform(get("/PatientSearch"))
	        	.andExpect(status().isOk())
	        	.andExpect(view().name("PatientSearch"))
	        	.andExpect(content().string(containsString("Patient wanted")));
		}
		
		@Test
		public void display_patient1() throws Exception {
			when(patientInfoProxy.getPatientsByFamilyAndGiven("last1","first1"))
				.thenReturn(List.of(patient1));
			mockMvc.perform(get("/PatientSearch?family=last1&&given=first1"))
	        	.andExpect(status().isOk())
	        	.andExpect(view().name("PatientSearch"))
	        	.andExpect(content().string(containsString("Patient wanted")))
	        	.andExpect(content().string(containsString("last1")));
		}
		
		@Test
		public void patient_does_not_exist() throws Exception {
			when(patientInfoProxy.getPatientsByFamilyAndGiven("last1","first1"))
				.thenReturn(new ArrayList<PatientBean>());
			System.out.println();
			mockMvc.perform(get("/PatientSearch?family=last1&&given=first1"))
	        	.andExpect(status().isOk())
	        	.andExpect(view().name("PatientSearch"))
	        	.andExpect(content().string(containsString("Sorry, last1 first1 is not registered.")));
		}
	}
	
	@Nested
	class PatientSearchRequest {
		@Test
		public void find_patient1() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			PatientBeanDTO patient1DTO = new PatientBeanDTO();
			patient1DTO.setFamily("last1");
			patient1DTO.setGiven("first1");
	        mockMvc.perform(post("/PatientSearch")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(patient1DTO)))
	            .andExpect(status().isOk())
	            .andExpect(view().name("PatientSearch"));
		}
	}
	
	@Test
	public void displayPatientInfoPage() throws Exception {
		List<NoteBean> notes = new ArrayList<>();
		PatientAndNotesBean patientAndNotes = PatientAndNotesBean.builder()
				.patient(patient1)
				.notes(notes)
				.build();
		ReportBean report = ReportBean.builder()
				.age(33)
				.assessment("None")
				.build();
		when(patientInfoProxy.getPatientById(1))
			.thenReturn(patient1);
		when(patientNoteProxy.getNotesByPatientId(1))
			.thenReturn(notes);
		when(patientReportProxy.getReport(patientAndNotes))
			.thenReturn(report);
		mockMvc.perform(get("/PatientInfo?id=1"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("PatientInfo"))
        	.andExpect(content().string(containsString("Patient information")))
        	.andExpect(content().string(containsString("last1")))
        	.andExpect(content().string(containsString("first1")))
        	.andExpect(content().string(containsString("33")))
        	.andExpect(content().string(containsString("None")));
	}
	
	@Test
	public void displayPatientUpdatePage() throws Exception {
		when(patientInfoProxy.getPatientById(1))
			.thenReturn(patient1);
		mockMvc.perform(get("/PatientUpdate?id=1"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("PatientUpdate"))
        	.andExpect(content().string(containsString("Updating patient information")))
        	.andExpect(content().string(containsString("last1")))
        	.andExpect(content().string(containsString("first1")));
	}
	
	@Nested
	class PatientUpdateRequest {
		@Test
		public void update_patient1() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			when(patientInfoProxy.updatePatient(1, patient1))
				.thenReturn(patient1);
	        mockMvc.perform(post("/PatientUpdate?id=1")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(patient1))
	        		.flashAttr("patient", patient1))
	            .andExpect(status().is(302))
	            .andExpect(view().name("redirect:/PatientInfo?id=1"));
		}
		
		@Test
		public void no_patient_with_this_id() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			when(patientInfoProxy.updatePatient(15, patient1))
				.thenReturn(new PatientBean());
	        mockMvc.perform(post("/PatientUpdate?id=15")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(patient1))
	        		.flashAttr("patient", patient1))
	            .andExpect(status().isOk())
	            .andExpect(view().name("PatientUpdate"));
		}
		
		@Test
		public void this_patient_already_exist() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			when(patientInfoProxy.updatePatient(1, patient2))
				.thenReturn(new PatientBean());
	        mockMvc.perform(post("/PatientUpdate?id=1")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(patient2))
	        		.flashAttr("patient", patient2))
	            .andExpect(status().isOk())
	            .andExpect(view().name("PatientUpdate"));
		}
	}
	
	@Nested
	class PatientCreateRequest {
		@Test
		public void create_patient5() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			PatientBean patientToCreate = PatientBean.builder()
					.family("last5")
					.given("first5")
					.dob("2005-05-05")
					.sex("M")
					.build();
			PatientBean patientCreated = PatientBean.builder()
					.id(5)
					.family("last5")
					.given("first5")
					.dob("2005-05-05")
					.sex("M")
					.build();
			when(patientInfoProxy.createPatient(patientToCreate))
				.thenReturn(patientCreated);
	    mockMvc.perform(post("/PatientCreate")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(patientToCreate))
          .flashAttr("patient", patientToCreate))
        .andExpect(status().is(302))
        .andExpect(view().name("redirect:/PatientInfo?id=5"));
		}
	}
	
}
