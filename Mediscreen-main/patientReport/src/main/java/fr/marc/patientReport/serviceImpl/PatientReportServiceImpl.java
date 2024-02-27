package fr.marc.patientReport.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.marc.patientReport.model.Note;
import fr.marc.patientReport.model.Patient;
import fr.marc.patientReport.model.Report;
import fr.marc.patientReport.model.Triggers;
import fr.marc.patientReport.repository.PatientReportRepository;
import fr.marc.patientReport.service.IPatientReportService;

@Service
public class PatientReportServiceImpl implements IPatientReportService {

	// Injection de dépendance du repository nécessaire pour accéder aux données des termes déclencheurs
	private PatientReportRepository patientReportRepository;

	// Constructeur de la classe qui permet l'injection de dépendance lors de la création de l'instance
	public PatientReportServiceImpl(PatientReportRepository patientReportRepository) {
		this.patientReportRepository = patientReportRepository;
	}

	/**
	 * Calcule le risque de diabète
	 * @param patient : Patient pour lequel le risque est évalué
	 * @param notes : Liste des notes médicales du patient
	 * @param currentDate : Date actuelle pour calculer l'âge
	 * @return Rapport contenant l'âge du patient et l'évaluation du risque de diabète
	 */

	// Méthode pour calculer le risque de diabète en fonction des informations du patient et de ses notes médicales
	@Override
	public Report calculateDiabetesRisk(Patient patient, List<Note> notes, LocalDate currentDate) {
		// Calcul de l'âge du patient en utilisant la méthode calculateAge
		Integer age = calculateAge(patient, currentDate);
		// Calcul du nombre de termes déclencheurs présents dans les notes médicales
		Integer triggersNumber = calculateTriggersNumber(notes);

		// Création d'un rapport contenant l'âge du patient et l'évaluation du risque de diabète
		Report report = Report.builder()
				.age(age)
				.assessment(assessTheRisk(patient.getSex(),age,triggersNumber))
				.build();
		return report;
	}

	/**
	 * Calcule l'âge d'un patient
	 * @param patient : Patient pour lequel l'âge est calculé
	 * @param currentDate : Date actuelle pour le calcul de l'âge
	 * @return L'âge du patient sous forme d'entier
	 */

	// Méthode pour calculer l'âge du patient
	Integer calculateAge(Patient patient, LocalDate currentDate) {
		// Formatter pour la conversion des chaînes de date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Conversion de la date de naissance du patient en objet LocalDate
		LocalDate birthdate = LocalDate.parse(patient.getDob(), formatter);
		// Calcul de la différence en années entre la date de naissance et la date actuelle
		return (int) ChronoUnit.YEARS.between(birthdate, currentDate);
	}

	/**
	 * Calcule le nombre de termes déclencheurs dans une liste de notes
	 * @param notes : Liste de notes médicales
	 * @return Le nombre de termes déclencheurs
	 */

	// Méthode pour calculer le nombre de termes déclencheurs dans une liste de notes médicales
	Integer calculateTriggersNumber(List<Note> notes) {
		// Utilisation d'un ensemble pour éviter les doublons de termes déclencheurs
		Set<String> triggersTermFound = new HashSet<String>();
		// Récupération de tous les termes déclencheurs depuis le repository
		Iterable<Triggers> triggersList = patientReportRepository.findAll();
		// Parcours de toutes les notes médicales du patient
		for (Note note : notes) {
			// Parcours de tous les termes déclencheurs disponibles
			for (Triggers triggers : triggersList) {
				String term = triggers.getTerm();

				// Vérification si le terme déclencheur est présent dans le corps de la note médicale (insensible à la casse)
				if (note.getBody().toLowerCase().contains(term)){
					triggersTermFound.add(term);
				}
			}
		}
		// Retourne le nombre total de termes déclencheurs uniques trouvés dans les notes médicales
		return triggersTermFound.size();
	}
	/**
	 * Évalue le risque de diabète en fonction du sexe, de l'âge et du nombre de termes déclencheurs
	 * @param sex : Sexe du patient (F ou M)
	 * @param age : Âge du patient
	 * @param triggersNumber : Nombre de termes déclencheurs
	 * @return L'évaluation du risque de diabète
	 */

	// Méthode pour évaluer le risque de diabète en fonction du sexe, de l'âge et du nombre de termes déclencheurs
	String assessTheRisk(String sex, Integer age, Integer triggersNumber) {
		// Si le patient est une femme et a 30 ans ou moins
		if (sex.equals("F") && age <= 30) {
			// Évaluation du risque de diabète en fonction du nombre de termes déclencheurs
			if (triggersNumber > 3 && triggersNumber < 7) {return "Danger";}
			if (triggersNumber > 6) {return "Early onset";}
		}
		// Si le patient est un homme et a 30 ans ou moins
		if (sex.equals("M") && age <= 30) {
			// Évaluation du risque de diabète en fonction du nombre de termes déclencheurs
			if (triggersNumber == 3 || triggersNumber == 4) {return "Danger";}
			if (triggersNumber > 4) {return "Early onset";}
		}
		// Si le patient a plus de 30 ans
		if (age > 30) {
			// Si aucun des critères ci-dessus n'est satisfait, le risque de diabète est considéré comme "Aucun"
			if (triggersNumber > 1 && triggersNumber < 6) {return "Borderline";}
			if (triggersNumber == 6 || triggersNumber == 7) {return "Danger";}
			if (triggersNumber > 7) {return "Early onset";}
		}
		return "None";
	}

}
