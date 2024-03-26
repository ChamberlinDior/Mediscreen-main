package fr.dior.patientInfo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.dior.patientInfo.model.Patient;
import fr.dior.patientInfo.service.IPatientInfoService;

@RestController
public class PatientInfoController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientInfoController.class); 
	
	@Autowired
	private IPatientInfoService patientInfoService;

	/**
	 * Obtenir la liste de tous les patients
	 * @return la liste de tous les patients
	 */
	@GetMapping("/PatientInfo/list")
	public Iterable<Patient> getPatients(){
		log.info("GET /PatientInfo/list => list of all patients {}", patientInfoService.getPatients());
		return patientInfoService.getPatients();
	}

	/**
	 * Obtenir un patient selon un identifiant
	 * @param id
	 * @return le patient selon cet identifiant s'il existe
	 */
	@GetMapping("/PatientInfo/byId")
	public Optional<Patient> getPatientById (@RequestParam Integer id){
		log.info("Get the patient with id = {}",id);
		return patientInfoService.getPatientById(id);
    }

	/**
	 * Obtenir la liste des patients selon un nom et un prénom
	 * @param family : Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given : Prénom selon le standard HL7 (Health Level Seven)
	 * @return la liste des patients selon ce nom de famille et prénom s'ils existent
	 */
	@GetMapping("/PatientInfo/byName")
	public List<Patient> getPatientsByFamilyAndGiven(
			@RequestParam String family,
			@RequestParam String given)
	{
		log.info("Get the patient {} {}",family, given);
		return patientInfoService.getPatientsByFamilyAndGiven(family, given);
	}

	/**
	 * Mettre à jour un patient désigné par son identifiant.
	 * @param id : L'identifiant du patient que vous souhaitez mettre à jour
	 * @param patient : Nouvelles données du patient
	 * @return Le patient mis à jour
	 */
	@PostMapping("/PatientInfo/update")
	public Patient updatePatient (
			@RequestParam Integer id, 
			@RequestBody Patient patient) 
	{
		log.info("Update the patient with id = {}",id);
		return patientInfoService.updatePatient(id, patient);
	}

	/**
	 * Créer un nouveau patient
	 * @param patient
	 * @return Le patient créé
	 */
	@PostMapping("/PatientInfo/add")
	public Patient createPatient (@RequestBody Patient patient) {
		log.info("Create the patient {}", patient);
		return patientInfoService.createPatient(patient);
	}
}
