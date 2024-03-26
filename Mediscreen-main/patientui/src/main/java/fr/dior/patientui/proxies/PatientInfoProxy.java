package fr.dior.patientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import fr.dior.patientui.beans.PatientBean;

// Déclaration de l'interface Feign Client avec le nom du service distant et son URL
@FeignClient(name = "mediscreen-patientInfo", url = "http://patientinfo:8081")
public interface PatientInfoProxy {

	// Déclaration d'une méthode GET pour récupérer la liste des patients
	@GetMapping(value = "/PatientInfo/list")
	List<PatientBean> getPatients();

	// Déclaration d'une méthode GET pour récupérer un patient par son identifiant
	@GetMapping(value = "/PatientInfo/byId")
	PatientBean getPatientById (@RequestParam Integer id);

	// Déclaration d'une méthode GET pour récupérer une liste de patients par nom de famille et prénom
	@GetMapping(value = "/PatientInfo/byName")
	List<PatientBean> getPatientsByFamilyAndGiven(
			@RequestParam String family, 
			@RequestParam String given);

	// Déclaration d'une méthode POST pour mettre à jour un patient
	@PostMapping("/PatientInfo/update")
	public PatientBean updatePatient (
			@RequestParam Integer id, 
			@RequestBody PatientBean patient);


	// Déclaration d'une méthode POST pour créer un nouveau patient
	@PostMapping("/PatientInfo/add")
	public PatientBean createPatient (@RequestBody PatientBean patient);
}
