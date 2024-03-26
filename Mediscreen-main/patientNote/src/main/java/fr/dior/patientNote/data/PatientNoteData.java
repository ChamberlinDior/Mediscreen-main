package fr.dior.patientNote.data;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.dior.patientNote.model.Note;
import fr.dior.patientNote.repository.PatientNoteRepository;

@Component
public class PatientNoteData {

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	/**
	 * Initialise les données initiales des notes des patients.
	 */
	public void initialisation() {
		// Supprime toutes les notes existantes
		patientNoteRepository.deleteAll();
        // Ajoute des exemples de notes pour différents patients
		patientNoteRepository.save(new Note(1, LocalDateTime.of(2023, 4, 1, 0, 0, 0),
				"Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level"));
		patientNoteRepository.save(new Note(2, LocalDateTime.of(2023, 4, 1, 0, 0, 0), 
				"Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late"));
		patientNoteRepository.save(new Note(2, LocalDateTime.of(2023, 4, 2, 0, 0, 0), 
				"Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic"));
		patientNoteRepository.save(new Note(3, LocalDateTime.of(2023, 4, 1, 0, 0, 0),
				"Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker"));
		patientNoteRepository.save(new Note(3, LocalDateTime.of(2023, 4, 1, 0, 0, 0),
				"Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high"));
		patientNoteRepository.save(new Note(4, LocalDateTime.of(2023, 4, 1, 0, 0, 0),
				"Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication"));
		patientNoteRepository.save(new Note(4, LocalDateTime.of(2023, 4, 2, 0, 0, 0),
				"Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are experiencing back pain when seated for a long time"));
		patientNoteRepository.save(new Note(4, LocalDateTime.of(2023, 4, 3, 0, 0, 0),
				"Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above recommended level"));
		patientNoteRepository.save(new Note(4, LocalDateTime.of(2023, 4, 4, 0, 0, 0),
				"Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction"));
	}
}
