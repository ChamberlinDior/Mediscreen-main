package fr.dior.patientui.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;

import fr.dior.patientui.beans.NoteBean;
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

import fr.dior.patientui.beans.PatientBean;
import fr.dior.patientui.proxies.PatientInfoProxy;
import fr.dior.patientui.proxies.PatientNoteProxy;

@SpringBootTest  // Indique que cette classe est un test Spring Boot
@AutoConfigureMockMvc  // Configure automatiquement le MockMvc
public class NoteControllerTest {

	@Autowired
	private MockMvc mockMvc;  // Injection de MockMvc pour simuler les requêtes HTTP

	@MockBean
	private PatientNoteProxy patientNoteProxy;  // Mock pour simuler le proxy de notes de patients

	@MockBean
	private PatientInfoProxy patientInfoProxy;  // Mock pour simuler le proxy d'informations de patients

	private static final Logger log = LoggerFactory.getLogger(NoteControllerTest.class);  // Logger

	private NoteBean note1;  // Déclaration de NoteBean pour le test
	private NoteBean note2;  // Déclaration de NoteBean pour le test
	private PatientBean patient1;  // Déclaration de PatientBean pour le test

	@BeforeEach
	public void init() {
		// Initialisation des objets pour les tests
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
		// Simulation de réponses du proxy pour les tests
		when(patientNoteProxy.getNotesByPatientId(1))
				.thenReturn(List.of(note1, note2));
		when(patientInfoProxy.getPatientById(1))
				.thenReturn(patient1);

		// Effectue une requête GET et vérifie les attentes
		mockMvc.perform(get("/NoteList?patId=1"))
				.andExpect(status().isOk())  // Vérifie le statut de la réponse HTTP
				.andExpect(view().name("NoteList"))  // Vérifie le nom de la vue retournée
				.andExpect(content().string(containsString("last1")))  // Vérifie la présence du nom de famille
				.andExpect(content().string(containsString("first1")))  // Vérifie la présence du prénom
				.andExpect(content().string(containsString("notes")))  // Vérifie la présence du mot "notes"
				.andExpect(content().string(containsString("Body1")))  // Vérifie la présence du corps de la note 1
				.andExpect(content().string(containsString("Body2")));  // Vérifie la présence du corps de la note 2
	}

	@Nested
	class NoteUpdateRequest {
		@Test
		public void update_note1() throws Exception {
			ObjectMapper mapper = new ObjectMapper();  // Initialise un objet ObjectMapper
			NoteBean updatedNote = NoteBean.builder()
					.body("Body updated")
					.build();
			// Simulation de la mise à jour de la note
			when(patientNoteProxy.updateNote("1", updatedNote))
					.thenReturn(note1);

			// Effectue une requête POST pour mettre à jour une note et vérifie les attentes
			mockMvc.perform(post("/NoteUpdate?id=1")
							.contentType(MediaType.APPLICATION_JSON)  // Type de contenu JSON
							.content(mapper.writeValueAsString(updatedNote))  // Corps de la requête
							.flashAttr("note", updatedNote))  // Attribut flash pour passer les données à la vue suivante
					.andExpect(status().is(302))  // Vérifie le statut de redirection
					.andExpect(view().name("redirect:/NoteList?patId=1"));  // Vérifie la redirection vers la liste des notes
		}
	}

	@Nested
	class NoteCreateRequest {
		@Test
		public void create_note() throws Exception {
			ObjectMapper mapper = new ObjectMapper();  // Initialise un objet ObjectMapper
			NoteBean newBody = NoteBean.builder()
					.body("New body")
					.build();
			NoteBean createdNote = NoteBean.builder()
					.id("3")
					.patId(1)
					.date(LocalDateTime.of(2023, 4, 3, 0, 0, 0))
					.body("New body")
					.build();
			// Simulation de la création d'une nouvelle note
			when(patientNoteProxy.createNote(1, newBody))
					.thenReturn(createdNote);

			// Effectue une requête POST pour créer une nouvelle note et vérifie les attentes
			mockMvc.perform(post("/NoteCreate?patId=1")
							.contentType(MediaType.APPLICATION_JSON)  // Type de contenu JSON
							.content(mapper.writeValueAsString(newBody))  // Corps de la requête
							.flashAttr("note", newBody))  // Attribut flash pour passer les données à la vue suivante
					.andExpect(status().is(302))  // Vérifie le statut de redirection
					.andExpect(view().name("redirect:/NoteList?patId=1"));  // Vérifie la redirection vers la liste des notes
		}
	}

}
