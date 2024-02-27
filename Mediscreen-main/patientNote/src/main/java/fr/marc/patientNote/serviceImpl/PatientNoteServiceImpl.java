package fr.marc.patientNote.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;
import fr.marc.patientNote.service.IPatientNoteService;

@Service
public class PatientNoteServiceImpl implements IPatientNoteService {

	private static final Logger log = LoggerFactory.getLogger(PatientNoteServiceImpl.class); 

	private PatientNoteRepository patientNoteRepository;
	
	public PatientNoteServiceImpl(PatientNoteRepository patientNoteRepository) {
		this.patientNoteRepository = patientNoteRepository;
	}

	/**
	 * Récupère toutes les notes d'un patient identifié par son ID.
	 * @param patId : Identifiant du patient.
	 * @return Liste des notes d'un patient identifié par son ID.
	 * 			new Note() si aucune note n'est associée à cet ID.
	 */
	@Override
	public Iterable<Note> getNotesByPatientId(Integer patId) {
		log.info("Get notes for patient with id = {}",patId);
		List<Note> noteList = patientNoteRepository.findByPatIdOrderByDateDesc(patId);
		if (noteList.isEmpty()){
			return new ArrayList<Note>();
		}
		return noteList;
	}

	/**
	 * Récupère la note identifiée par son ID.
	 * @param id : Identifiant de la note.
	 * @return La note identifiée par son ID.
	 * 			null si aucune note n'est associée à cet ID.
	 */
	@Override
	public Optional<Note> getNoteById(String id) {
		log.info("Get note with id = {}",id);
		Optional<Note> note = patientNoteRepository.findById(id);
		if (note.isPresent()) {
			return note;
		}
		log.error("There is no note with id = {} ",id);
		return null;
	}

	/**
	 * Met à jour une note identifiée par son ID.
	 * @param id : Identifiant de la note à mettre à jour.
	 * @param note : Nouvelles données de la note.
	 * @return La note mise à jour.
	 */
	@Override
	public Note updateNote(String id, Note note) {
		Optional<Note> currentNote = patientNoteRepository.findById(id);
		if (currentNote.isEmpty()) {
			return null;
		}
		currentNote.get().setBody(note.getBody());
		return patientNoteRepository.save(currentNote.get());
	}

	/**
	 * Crée une nouvelle note pour un patient donné.
	 * @param patId : Identifiant du patient pour lequel créer une note.
	 * @param note : Note contenant le corps de la note à créer.
	 * @return La note créée.
	 */
	@Override
	public Note createNote(Integer patId, Note note) {
		Note creatingNote = Note.builder()
				.patId(patId)
				.date(LocalDateTime.now())
				.body(note.getBody())
				.build();
		return patientNoteRepository.save(creatingNote);
	}

}
