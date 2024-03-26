package fr.dior.patientReport.service;

import java.time.LocalDate;
import java.util.List;

import fr.dior.patientReport.model.Report;
import fr.dior.patientReport.model.Note;
import fr.dior.patientReport.model.Patient;

// Interface définissant le contrat pour le service de rapport de patient
public interface IPatientReportService {

	/**
	 * Pour calculer le risque de diabète
	 * @param patient : Le patient pour lequel calculer le risque
	 * @param notes : La liste des notes médicales liées au patient
	 * @param currentDate : La date actuelle pour l'évaluation du risque
	 * @return un rapport contenant l'âge du patient et l'évaluation du risque de diabète
	 */

	// Méthode pour calculer le risque de diabète en fonction des informations du patient, des notes médicales et de la date actuelle
	Report calculateDiabetesRisk(Patient patient, List<Note> notes, LocalDate currentDate);

}
