package fr.marc.patientNote.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.service.IPatientNoteService;

@RestController
public class PatientNoteController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientNoteController.class); 

	@Autowired
	private IPatientNoteService patientNoteService;

	/**
	 * Récupérer toutes les notes d'un patient identifié par son ID
	 * @param patId : L'ID du patient
	 * @return Toutes les notes d'un patient identifié par son ID
	 */
	@GetMapping("/PatientNote/byPatient")
	public Iterable<Note> getNotesByPatientId (@RequestParam Integer patId){
		log.info("Get notes fore patient with id = {}",patId);
		return patientNoteService.getNotesByPatientId(patId);
	}

	/**
	 * Obtenir la note identifiée par son ID
	 * @param id : L'ID de la note
	 * @return La note identifiée par son ID
	 */
	@GetMapping("/PatientNote/byId")
	public Optional<Note> getNoteById (@RequestParam String id){
		log.info("Get note with id = {}",id);
		return patientNoteService.getNoteById(id);
	}

	/**
	 * Mettre à jour la note identifiée par son ID
	 * @param id : L'ID de la note
	 * @param note
	 * @return La note mise à jour
	 */
	@PostMapping("/PatientNote/update")
	public Note updateNote (
			@RequestParam String id,
			@RequestBody Note note)
	{
		log.info("Update note with id = {}",id);
		return patientNoteService.updateNote(id, note);
	}
	/**
	 * Créer une nouvelle note pour un patient identifié par son ID
	 * @param patId : L'ID du patient
	 * @param note
	 * @return La note créée
	 */
	@PostMapping("/PatientNote/create")
	public Note createNote (
			@RequestParam Integer patId,
			@RequestBody Note note)
	{
		log.info("Create note {} for patient with id = {}",note, patId);
		return patientNoteService.createNote(patId, note);
	}
}
