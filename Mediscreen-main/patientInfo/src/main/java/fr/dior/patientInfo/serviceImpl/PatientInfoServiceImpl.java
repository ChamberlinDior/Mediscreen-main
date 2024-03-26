package fr.dior.patientInfo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.dior.patientInfo.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.dior.patientInfo.repository.PatientInfoRepository;
import fr.dior.patientInfo.service.IPatientInfoService;

@Service
public class PatientInfoServiceImpl implements IPatientInfoService {
	// Déclaration d'un logger pour enregistrer des informations de journalisation
	private static final Logger log = LoggerFactory.getLogger(PatientInfoServiceImpl.class);
	// Injection de dépendance du repository PatientInfoRepository
	private PatientInfoRepository patientInfoRepository;
	// Constructeur pour l'injection de dépendance
	public PatientInfoServiceImpl(PatientInfoRepository patientInfoRepository) {
		this.patientInfoRepository = patientInfoRepository;
	}

	/**
	 * Pour récupérer la liste de tous les patients
	 * @return la liste de tous les patients
	 */

	// Implémentation de la méthode de l'interface IPatientInfoService pour récupérer tous les patients
	@Override
	public Iterable<Patient> getPatients(){
		log.info("Get all patients");
		return patientInfoRepository.findAllByOrderByFamilyAsc();
	}

	/**
	 * Pour trouver un patient selon un identifiant
	 * @param id
	 * @return le patient selon cet identifiant
	 *         null s'il n'y a pas de patient avec cet identifiant
	 */

	// Implémentation de la méthode de l'interface IPatientInfoService pour récupérer un patient par ID
	@Override
	public Optional<Patient> getPatientById(Integer id){
		log.info("Get the patient with id = {}",id);
		Optional<Patient> patient = patientInfoRepository.findById(id);
		if (patient.isPresent()) {
			return patient;
		}
		log.error("There is no patient with id = {} ",id);
		return null;
	}

	/**
	 * Pour trouver la liste de patients selon un nom de famille et un prénom
	 * @param family = Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given = Prénom selon le standard HL7 (Health Level Seven)
	 * @return la liste de patients selon ce nom de famille et prénom
	 *         new Patient() s'il n'y a pas de patient selon ce nom de famille et prénom
	 */

	// Implémentation de la méthode de l'interface IPatientInfoService pour récupérer des patients par nom et prénom
	@Override
	public List<Patient> getPatientsByFamilyAndGiven(String family, String given){
		log.info("Get the patient {} {}",family, given);
		List<Patient> patientList = patientInfoRepository.findByFamilyAndGiven(family, given);
		if (patientList.isEmpty()) {
			return new ArrayList<Patient>();
		}
		return patientList;
	}

	/**
	 * Pour trouver un patient correspondant à un nom de famille, un prénom et une date de naissance
	 * @param family : Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given : Prénom selon le standard HL7 (Health Level Seven)
	 * @param dob : Date de naissance selon le standard HL7 (Health Level Seven)
	 * @return Le premier patient correspondant à un nom de famille, un prénom et une date de naissance
	 */

	// Implémentation de la méthode de l'interface IPatientInfoService pour récupérer un patient par nom, prénom et date de naissance
	@Override
	public Optional<Patient> getPatientByFamilyAndGivenAndDob(String family, String given, String dob){
		log.info("Get the patient {} {} {}", family, given, dob);
		return patientInfoRepository.findFirstByFamilyAndGivenAndDob(family, given, dob);
	}

	/**
	 * Pour mettre à jour un patient désigné par son identifiant.
	 * @param id : L'identifiant du patient que vous souhaitez mettre à jour
	 * @param updatedPatient : Nouvelles données du patient
	 * @return Le patient mis à jour
	 *         null s'il n'y a pas de patient avec cet identifiant
	 *         new Patient() si les données correspondent à un autre patient
	 */

	// Implémentation de la méthode de l'interface IPatientInfoService pour mettre à jour un patient

	@Override
	public Patient updatePatient(Integer patientId, Patient updatedPatient){
		log.info("Update the patient with id = {}",patientId);
		// Récupération du patient actuel par son identifiant
		Optional<Patient> currentPatient = patientInfoRepository.findById(patientId);
		if (currentPatient.isEmpty()) {
			return null;
		}
		// Test si le patient à mettre à jour existe déjà
		Optional<Patient> wantedPatient = patientInfoRepository.findFirstByFamilyAndGivenAndDob(
				updatedPatient.getFamily(),
				updatedPatient.getGiven(), 
				updatedPatient.getDob());
		if (wantedPatient.isPresent() && !wantedPatient.get().getId().equals(patientId)) {
			return new Patient();
		}

		// Mise à jour des données du patient
		currentPatient.get().setFamily(updatedPatient.getFamily());
		currentPatient.get().setGiven(updatedPatient.getGiven());
		currentPatient.get().setDob(updatedPatient.getDob());
		currentPatient.get().setSex(updatedPatient.getSex());
		currentPatient.get().setAddress(updatedPatient.getAddress());
		currentPatient.get().setPhone(updatedPatient.getPhone());
		// Sauvegarde du patient mis à jour dans le repository
		return patientInfoRepository.save(currentPatient.get());
	}

	/**
	 * Pour créer un nouveau patient
	 * @param patient
	 * @return Le patient créé
	 *         null si ce patient existe déjà
	 */

	// Implémentation de la méthode de l'interface IPatientInfoService pour créer un nouveau patient
	@Override
	public Patient createPatient(Patient patient){
		// Recherche si le patient existe déjà avec le même nom, prénom et date de naissance
		Optional<Patient> findingPatient = patientInfoRepository.findFirstByFamilyAndGivenAndDob(patient.getFamily(),patient.getGiven(),
				patient.getDob());

		// Si le patient existe déjà, retourne un nouveau Patient()
		if (findingPatient.isPresent()) {
			return new Patient();
		}

		// Si le patient n'existe pas, sauvegarde le nouveau patient dans le repository
		return patientInfoRepository.save(patient);
	}



}
