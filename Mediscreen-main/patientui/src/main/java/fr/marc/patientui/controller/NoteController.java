package fr.marc.patientui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.proxies.PatientInfoProxy;
import fr.marc.patientui.proxies.PatientNoteProxy;
/**
 * Contrôleur gérant les opérations liées aux notes médicales (CRUD).
 */
@Controller
public class NoteController {
	
	private static final Logger log = LoggerFactory.getLogger(NoteController.class); 
	private final PatientNoteProxy patientNoteProxy;
	private final PatientInfoProxy patientInfoProxy;
	/**
	 * Constructeur du contrôleur de notes.
	 * @param patientNoteProxy : Proxy pour les opérations liées aux notes médicales.
	 * @param patientInfoProxy : Proxy pour les opérations liées aux informations des patients.
	 */
	public NoteController(PatientNoteProxy patientNoteProxy, PatientInfoProxy patientInfoProxy) {
		this.patientNoteProxy = patientNoteProxy;
		this.patientInfoProxy = patientInfoProxy;
	}
	/**
	 * Affiche la liste des notes pour un patient donné.
	 * @param patId : Identifiant du patient.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de la liste des notes médicales.
	 */
	@GetMapping("/NoteList")
	public String noteListPage(
			@RequestParam Integer patId,
			Model model)
	{
		log.info("Note Attributes: {}", patientNoteProxy.getNotesByPatientId(patId));
		model.addAttribute("notes", patientNoteProxy.getNotesByPatientId(patId));
		model.addAttribute("patient",patientInfoProxy.getPatientById(patId));
		return "NoteList";
	}
	/**
	 * Affiche la page de mise à jour d'une note médicale.
	 * @param id : Identifiant de la note médicale à mettre à jour.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de la mise à jour de la note médicale.
	 */
	@GetMapping("/NoteUpdate")
	public String noteUpdatePage(@RequestParam String id, Model model){
		NoteBean note = patientNoteProxy.getNoteById(id).get();
		model.addAttribute("note",note);
		PatientBean patient = patientInfoProxy.getPatientById(note.getPatId());
		model.addAttribute("patient", patient);
		return "NoteUpdate";
	}
	/**
	 * Valide la mise à jour d'une note médicale.
	 * @param id : Identifiant de la note médicale à mettre à jour.
	 * @param note : Objet NoteBean contenant les nouvelles données.
	 * @return Redirige vers la liste des notes médicales du patient.
	 */
	@PostMapping("/NoteUpdate")
	public String noteUpdateValidation(
			@RequestParam String id,
			@ModelAttribute("note") NoteBean note) 
	{
		NoteBean updatedNote = patientNoteProxy.updateNote(id, note);
		log.info("Note Attributes: {}",updatedNote);
		return "redirect:/NoteList?patId=" + updatedNote.getPatId();
	}
	/**
	 * Affiche la page de création d'une nouvelle note médicale.
	 * @param patId : Identifiant du patient pour lequel créer une note médicale.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de création d'une nouvelle note médicale.
	 */
	@GetMapping("/NoteCreate")
	public String noteCreatePage(@RequestParam Integer patId, Model model){
		NoteBean note = new NoteBean();
		model.addAttribute("note",note);
		PatientBean patient = patientInfoProxy.getPatientById(patId);
		model.addAttribute("patient", patient);
		return "NoteCreate";
	}
	/**
	 * Valide la création d'une nouvelle note médicale.
	 * @param patId : Identifiant du patient pour lequel créer une note médicale.
	 * @param note : Objet NoteBean contenant les données de la nouvelle note médicale.
	 * @return Redirige vers la liste des notes médicales du patient.
	 */
	@PostMapping("/NoteCreate")
	public String noteCreateValidation(
			@RequestParam Integer patId,
			@ModelAttribute("note") NoteBean note)
	{
		patientNoteProxy.createNote(patId, note);
		return "redirect:/NoteList?patId=" + patId.toString();
	}
	

}
