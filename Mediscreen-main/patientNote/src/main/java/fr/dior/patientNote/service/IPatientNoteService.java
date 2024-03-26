package fr.dior.patientNote.service;

import java.util.Optional;

import fr.dior.patientNote.model.Note;
/**
 * Interface de service pour les opérations liées aux notes médicales.
 */
public interface IPatientNoteService {

	/**
	 * Récupère toutes les notes d'un patient identifié par son ID.
	 * @param patId : Identifiant du patient.
	 * @return Liste des notes d'un patient identifié par son ID.
	 */
	Iterable<Note> getNotesByPatientId(Integer patId);

	/**
	 * Récupère la note identifiée par son ID.
	 * @param id : Identifiant de la note.
	 * @return La note identifiée par son ID.
	 */
	Optional<Note> getNoteById(String id);

	/**
	 * Met à jour une note identifiée par son ID.
	 * @param id : Identifiant de la note à mettre à jour.
	 * @param note : Nouvelles données de la note.
	 * @return La note mise à jour.
	 */
	Note updateNote(String id, Note note);

	/**
	 * Crée une nouvelle note pour un patient donné.
	 * @param patId : Identifiant du patient pour lequel créer une note.
	 * @param note : Note contenant le corps de la note à créer.
	 * @return La note créée.
	 */
	Note createNote(Integer patId,Note note);
	
}
