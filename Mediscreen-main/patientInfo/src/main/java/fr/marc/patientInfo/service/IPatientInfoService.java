package fr.marc.patientInfo.service;

import java.util.List;
import java.util.Optional;

import fr.marc.patientInfo.model.Patient;

public interface IPatientInfoService {

	/**
	 * Pour récupérer la liste de tous les patients
	 * @return la liste de tous les patients
	 */
	Iterable<Patient> getPatients();

	/**
	 * Pour trouver un patient selon un identifiant
	 * @param id
	 * @return le patient selon cet identifiant
	 */
	Optional<Patient> getPatientById(Integer id);

	/**
	 * Pour trouver la liste de patients selon un nom de famille et un prénom
	 * @param family = Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given = Prénom selon le standard HL7 (Health Level Seven)
	 * @return la liste de patients selon ce nom de famille et prénom
	 */
	List<Patient> getPatientsByFamilyAndGiven(String family, String given);

	/**
	 * Pour trouver un patient correspondant à un nom de famille, un prénom et une date de naissance
	 * @param family : Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given : Prénom selon le standard HL7 (Health Level Seven)
	 * @param dob : Date de naissance selon le standard HL7 (Health Level Seven)
	 * @return Le premier patient correspondant à un nom de famille, un prénom et une date de naissance
	 */
	Optional<Patient> getPatientByFamilyAndGivenAndDob (String family, String given, String dob);

	/**
	 * Pour mettre à jour un patient désigné par son identifiant.
	 * @param id : L'identifiant du patient que vous souhaitez mettre à jour
	 * @param patient : Nouvelles données du patient
	 * @return Le patient mis à jour
	 */
	Patient updatePatient(Integer id, Patient patient);

	/**
	 * Pour créer un nouveau patient
	 * @param patient
	 * @return Le patient créé
	 */
	Patient createPatient(Patient patient);
}
