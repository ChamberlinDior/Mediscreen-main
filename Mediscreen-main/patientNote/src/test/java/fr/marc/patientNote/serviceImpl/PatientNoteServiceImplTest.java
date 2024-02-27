package fr.marc.patientNote.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;
import fr.marc.patientNote.service.IPatientNoteService;

@ExtendWith(MockitoExtension.class)
public class PatientNoteServiceImplTest {
	
	private IPatientNoteService patientNoteService;
	
	@Mock
	private PatientNoteRepository patientNoteRepository;
	
	private Note note1;
	private Note note2;
	
	@Captor
	ArgumentCaptor<Note> noteCaptor;
	
	@BeforeEach
	public void init() {
		patientNoteService = new PatientNoteServiceImpl(patientNoteRepository);
		note1 = Note.builder()
				.id("1")
				.patId(1)
				.date(LocalDateTime.of(2023, 4, 1, 0, 0, 0))
				.body("Body1")
				.build();
		note2 = Note.builder()
				.id("2")
				.patId(1)
				.date(LocalDateTime.of(2023, 4, 2, 0, 0, 0))
				.body("Body2")
				.build();
	}
	
	@Nested
	class GetNotesByPatientId {
		@Test
		public void success() {
			when(patientNoteRepository.findByPatIdOrderByDateDesc(1))
				.thenReturn(Arrays.asList(note2,note1));
			assertThat(patientNoteService.getNotesByPatientId(1))
				.contains(note1)
				.contains(note2);
			verify(patientNoteRepository).findByPatIdOrderByDateDesc(1);
		}
		
		@Test
		public void no_note() {
			when(patientNoteRepository.findByPatIdOrderByDateDesc(15))
				.thenReturn(new ArrayList<Note>());
			assertThat(patientNoteService.getNotesByPatientId(15))
				.isEmpty();
			verify(patientNoteRepository).findByPatIdOrderByDateDesc(15);
		}
	}
	
	@Nested
	class GetNoteById {
		@Test
		public void success() {
			when(patientNoteRepository.findById("1"))
				.thenReturn(Optional.of(note1));
			assertThat(patientNoteService.getNoteById("1"))
				.isEqualTo(Optional.of(note1));
			verify(patientNoteRepository).findById("1");
		}
		
		@Test
		public void no_note() {
			when(patientNoteRepository.findById("15"))
				.thenReturn(Optional.empty());
			assertThat(patientNoteService.getNoteById("15"))
				.isNull();
			verify(patientNoteRepository).findById("15");
		}
	}
	
	@Nested
	class UpdateNote {
		@Test
		public void success() {
			Note updatedNote = Note.builder()
					.id("2")
					.patId(1)
					.date(LocalDateTime.of(2023, 4, 2, 0, 0, 0))
					.body("Body updated")
					.build();
			when(patientNoteRepository.findById("2"))
				.thenReturn(Optional.of(note2));
			when(patientNoteRepository.save(note2))
				.thenReturn(updatedNote);
			Note returnNote = patientNoteService.updateNote("2", updatedNote);
			verify(patientNoteRepository).save(noteCaptor.capture());
			assertThat(noteCaptor.getValue()).isEqualTo(note2);
			assertThat(returnNote).isEqualTo(updatedNote);
			verify(patientNoteRepository).findById("2");
			verify(patientNoteRepository).save(note2);
		}
		
		@Test
		public void no_note_with_this_id_should_return_null() {
			Note updatedNote = Note.builder()
					.id("5")
					.patId(1)
					.date(LocalDateTime.of(2023, 4, 2, 0, 0, 0))
					.body("Body updated")
					.build();
			when(patientNoteRepository.findById("5"))
				.thenReturn(Optional.empty());
			Note returnNote = patientNoteService.updateNote("5", updatedNote);
			assertThat(returnNote).isNull();
			verify(patientNoteRepository).findById("5");
		}
	}
	
	@Nested
	class CreateNote {
		@Test
		public void success() {
			Note newBody = Note.builder()
					.body("Body created")
					.build();
			Note createdNote = Note.builder()
					.id("1")
					.patId(1)
					.date(LocalDateTime.now())
					.body("Body created")
					.build();
			when(patientNoteRepository.save(any(Note.class)))
				.thenReturn(createdNote);
			Note returnNote = patientNoteService.createNote(1, newBody);
			verify(patientNoteRepository).save(noteCaptor.capture());
			assertThat(noteCaptor.getValue().getPatId()).isEqualTo(1);
			assertThat(noteCaptor.getValue().getBody()).isEqualTo("Body created");
			assertThat(returnNote).isEqualTo(createdNote);
		}
	}

}
