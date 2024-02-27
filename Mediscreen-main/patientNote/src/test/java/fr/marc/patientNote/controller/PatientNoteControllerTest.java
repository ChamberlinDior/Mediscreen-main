package fr.marc.patientNote.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

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

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PatientNoteControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(PatientNoteControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PatientNoteRepository patientNoteRepository;
	
	// GET /PatientNote/byPatient
	@Nested
	class GetNotesByPatientId {
		@Test
		public void success () throws Exception {
			mockMvc.perform(get("/PatientNote/byPatient?patId=1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].body", is("Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level")));
		}
		
		@Test
		public void no_note () throws Exception {
			mockMvc.perform(get("/PatientNote/byPatient?patId=15"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
		}
	}
	
	// GET /PatientNote/byId
	@Nested
	class GetNoteById {
		@Test
		public void success () throws Exception {
			List<Note> notes = patientNoteRepository.findByPatIdOrderByDateDesc(1);
			mockMvc.perform(get("/PatientNote/byId?id=" + notes.get(0).getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.body", is("Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level")));
		}
		
		@Test
		public void no_note () throws Exception {
			mockMvc.perform(get("/PatientNote/byId?id=1"))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
		}
	}
	
	// POST /PatientNote/update
	@Nested
	class UpdateNote {
		@Test
		public void body_updated() throws Exception{
			ObjectMapper mapper = new ObjectMapper();
			List<Note> notes = patientNoteRepository.findByPatIdOrderByDateDesc(3);
			Note updatedNote = Note.builder()
					.body("Body updated")
					.build();
			mockMvc.perform(post("/PatientNote/update?id=" + notes.get(0).getId())
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedNote)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.body", is("Body updated")));
		}
		
		@Test
		public void no_note_with_this_id_should_return_nothing() throws Exception{
			ObjectMapper mapper = new ObjectMapper();
			Note updatedNote = Note.builder()
					.body("Body updated")
					.build();
			MvcResult result = mockMvc.perform(post("/PatientNote/update?id=15")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedNote)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist())
				.andReturn();
			assertThat(result.getResponse().getContentAsString())
				.isEqualTo("");
		}
	}
	
	// POST /PatientInfo/add
	@Nested
	class CreateNote {
		@Test
		public void success () throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			Note createdNote = Note.builder()
					.body("New body")
					.build();
			mockMvc.perform(post("/PatientNote/create?patId=4")
		       		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(createdNote)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patId", is(4)))
				.andExpect(jsonPath("$.body", is("New body")));
		}
	}

}
