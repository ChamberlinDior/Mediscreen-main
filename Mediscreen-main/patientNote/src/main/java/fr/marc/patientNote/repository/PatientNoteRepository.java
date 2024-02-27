package fr.marc.patientNote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.marc.patientNote.model.Note;
/**
 * Interface de repository pour les opérations liées aux notes médicales dans la base de données MongoDB.
 */
@Repository
public interface PatientNoteRepository extends MongoRepository<Note, String> {
	/**
	 * Récupère une liste de notes médicales associées à un patient triées par date de manière décroissante.
	 * @param patId Identifiant du patient.
	 * @return Liste de notes médicales triées par date de manière décroissante.
	 */
	List<Note> findByPatIdOrderByDateDesc(Integer patId);

}
