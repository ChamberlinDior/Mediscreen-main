package fr.dior.patientui.proxies;

import java.util.Optional;

import fr.dior.patientui.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


// Déclaration de l'interface Feign Client avec le nom du service distant et son URL
@FeignClient(name = "mediscreen-patientNote", url = "http://patient-note:8080")
public interface PatientNoteProxy {

	// Déclaration d'une méthode GET pour récupérer les notes d'un patient par son identifiant
	@GetMapping("/PatientNote/byPatient")
	Iterable<NoteBean> getNotesByPatientId (@RequestParam Integer patId);

	// Déclaration d'une méthode GET pour récupérer une note par son identifiant
	@GetMapping("/PatientNote/byId")
	public Optional<NoteBean> getNoteById (@RequestParam String id);

	// Déclaration d'une méthode POST pour mettre à jour une note
	@PostMapping("/PatientNote/update")
	public NoteBean updateNote (@RequestParam String id,@RequestBody NoteBean note);

	// Déclaration d'une méthode POST pour créer une nouvelle note pour un patient
	@PostMapping("/PatientNote/create")
	public NoteBean createNote (@RequestParam Integer patId,@RequestBody NoteBean note);
}
