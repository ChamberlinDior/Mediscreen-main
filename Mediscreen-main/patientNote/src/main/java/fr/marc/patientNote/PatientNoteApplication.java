package fr.marc.patientNote;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.marc.patientNote.data.PatientNoteData;
import fr.marc.patientNote.model.Note;
import fr.marc.patientNote.repository.PatientNoteRepository;

@SpringBootApplication
@EnableMongoRepositories
public class PatientNoteApplication implements CommandLineRunner {

	@Autowired
	private PatientNoteRepository patientNoteRepository;
	
	@Autowired
	private PatientNoteData patientNoteData;

	public static void main(String[] args) {
		SpringApplication.run(PatientNoteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Initialisation des données de notes
		patientNoteData.initialisation();

		// Récupérer toutes les notes
		System.out.println("-------------------------------");
		System.out.println("Notes found with findAll():");
		System.out.println("-------------------------------");
		for (Note note : patientNoteRepository.findAll()) {
			System.out.println(note);
		}
		System.out.println();

		// Récupérer toutes les notes du patient avec l'ID = 1
		System.out.println("-------------------------------");
		System.out.println("Notes for patient with id = 4 :");
		System.out.println("-------------------------------");
		for (Note note : patientNoteRepository.findByPatIdOrderByDateDesc(4)) {
			System.out.println(note);
		}
		System.out.println();
	}

}
