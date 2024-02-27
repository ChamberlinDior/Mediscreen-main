package fr.marc.patientui.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
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
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.proxies.PatientInfoProxy;
import fr.marc.patientui.proxies.PatientNoteProxy;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PatientNoteProxy patientNoteProxy;
	
	@MockBean
	private PatientInfoProxy patientInfoProxy;
	
	private static final Logger log = LoggerFactory.getLogger(NoteControllerTest.class); 
	private NoteBean note1;
	private NoteBean note2;
	private PatientBean patient1;
	
	@BeforeEach
	public void init() {
		note1 = NoteBean.builder()
				.id("1")
				.patId(1)
				.date(LocalDateTime.of(2023, 4, 1, 0, 0, 0))
				.body("Body1")
				.build();
		note2 = NoteBean.builder()
				.id("2")
				.patId(1)
				.date(LocalDateTime.of(2023, 4, 2, 0, 0, 0))
				.body("Body2")
				.build();
		patient1 = PatientBean.builder()
				.id(1)
				.family("last1")
				.given("first1")
				.dob("2001-01-01")
				.sex("F")
				.address("Address1")
				.phone("111111")
				.build();
	}
	
	@Test
	public void displayNoteListPage() throws Exception {
		when(patientNoteProxy.getNotesByPatientId(1))
			.thenReturn(List.of(note1, note2));
		when(patientInfoProxy.getPatientById(1))
			.thenReturn(patient1);
		mockMvc.perform(get("/NoteList?patId=1"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("NoteList"))
        	.andExpect(content().string(containsString("last1")))
        	.andExpect(content().string(containsString("first1")))
        	.andExpect(content().string(containsString("notes")))
        	.andExpect(content().string(containsString("Body1")))
        	.andExpect(content().string(containsString("Body2")));
	}
	
	@Nested
	class NoteUpdateRequest {
		@Test
		public void update_note1() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			NoteBean updatedNote = NoteBean.builder()
					.body("Body updated")
					.build();
			when(patientNoteProxy.updateNote("1", updatedNote))
				.thenReturn(note1);
	        mockMvc.perform(post("/NoteUpdate?id=1")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(updatedNote))
	        		.flashAttr("note", updatedNote))
	            .andExpect(status().is(302))
	            .andExpect(view().name("redirect:/NoteList?patId=1"));
		}
	}
	
	@Nested
	class NoteCreateRequest {
		@Test
		public void create_note() throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			NoteBean newBody = NoteBean.builder()
					.body("New body")
					.build();
			NoteBean createdNote = NoteBean.builder()
					.id("3")
					.patId(1)
					.date(LocalDateTime.of(2023, 4, 3, 0, 0, 0))
					.body("New body")
					.build();
			when(patientNoteProxy.createNote(1, newBody))
				.thenReturn(createdNote);
	        mockMvc.perform(post("/NoteCreate?patId=1")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(mapper.writeValueAsString(newBody))
	        		.flashAttr("note", newBody))
	            .andExpect(status().is(302))
	            .andExpect(view().name("redirect:/NoteList?patId=1"));
		}
	}

}
