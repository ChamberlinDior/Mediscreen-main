package fr.marc.patientui.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.marc.patientui.beans.NoteBean;
import fr.marc.patientui.beans.PatientAndNotesBean;
import fr.marc.patientui.beans.PatientBean;
import fr.marc.patientui.beans.PatientBeanDTO;
import fr.marc.patientui.beans.ReportBean;
import fr.marc.patientui.proxies.PatientInfoProxy;
import fr.marc.patientui.proxies.PatientNoteProxy;
import fr.marc.patientui.proxies.PatientReportProxy;
import jakarta.validation.Valid;
/**
 * Contrôleur gérant les opérations liées aux patients (CRUD, recherche, affichage d'informations).
 */
@Controller
public class PatientController {
	// Logger pour le suivi des logs
	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	// Proxies pour les opérations liées aux patients, notes et rapports médicaux
	private final PatientInfoProxy patientInfoProxy;
	private final PatientNoteProxy patientNoteProxy;
	private final PatientReportProxy patientReportProxy;
	/**
	 * Constructeur du contrôleur des patients.
	 * @param patientInfoProxy : Proxy pour les opérations liées aux informations des patients.
	 * @param patientNoteProxy : Proxy pour les opérations liées aux notes médicales des patients.
	 * @param patientReportProxy : Proxy pour les opérations liées aux rapports médicaux des patients.
	 */
	public PatientController(
			PatientInfoProxy patientInfoProxy, 
			PatientNoteProxy patientNoteProxy, 
			PatientReportProxy patientReportProxy) {
		this.patientInfoProxy = patientInfoProxy;
		this.patientNoteProxy = patientNoteProxy;
		this.patientReportProxy = patientReportProxy;
	}
	/**
	 * Affiche la page d'accueil.
	 * @return La vue de la page d'accueil.
	 */
	@GetMapping("/")
	public String home() {
		return "Home";
	}
	/**
	 * Affiche la liste des patients.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de la liste des patients.
	 */
	@GetMapping("/PatientList")
	public String patientListPage(Model model){
		// Récupération de la liste des patients via le proxy
		List<PatientBean> patients =  patientInfoProxy.getPatients();
	    model.addAttribute("patients", patients);
		return "PatientList";
	}
	/**
	 * Affiche la page de recherche de patients.
	 * @param family : Nom de famille pour la recherche.
	 * @param given : Prénom pour la recherche.
	 * @param patientBean : Objet PatientBean contenant les données de recherche.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de la recherche de patients.
	 */
	@GetMapping("/PatientSearch")
	public String patientSearchPage(
			@RequestParam (name="family", defaultValue="") String family,
			@RequestParam (name="given", defaultValue="") String given,
			@ModelAttribute PatientBean patientBean,
			Model model)
	{
		// Récupération des patients correspondant aux critères de recherche
		List<PatientBean> patients = patientInfoProxy.getPatientsByFamilyAndGiven(family, given);
		model.addAttribute("patients",patients);
		// Affichage d'un message d'erreur si aucun patient n'est trouvé
		if (patients.isEmpty() && !family.isEmpty() && !given.isEmpty()) {
			String errorMessage = "Sorry, " + family + " " + given + " is not registered.";
			model.addAttribute("errorMessage",errorMessage);
		}
		return "PatientSearch";
	}
	/**
	 * Valide la recherche de patients.
	 * @param patientBean : Objet PatientBeanDTO contenant les données de recherche.
	 * @param result : Résultat de la validation.
	 * @return Redirige vers la recherche de patients avec les paramètres de recherche.
	 */
	@PostMapping("/PatientSearch")
	public String PatientResearch (
			@Valid 
			@ModelAttribute("patientBean") PatientBeanDTO patientBean,
			BindingResult result)
	{
		// Redirection vers la recherche de patients avec les paramètres de recherche
		if (result.hasErrors()) {
            log.info("BindingResult = {}",result);
			return "PatientSearch";
        }
		return "redirect:/PatientSearch?family=" + patientBean.getFamily() + "&&given=" + patientBean.getGiven();
	}
	/**
	 * Affiche la page d'informations d'un patient.
	 * @param id : Identifiant du patient.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue des informations du patient.
	 */
	@GetMapping("/PatientInfo")
	public String patientInfoPage(@RequestParam Integer id, Model model){
		// Récupération des informations du patient et de ses notes médicales
		PatientBean patient = patientInfoProxy.getPatientById(id);
		model.addAttribute("patient",patient);
		PatientAndNotesBean patientAndNotes = PatientAndNotesBean.builder()
				.patient(patient)
				.notes(patientNoteProxy.getNotesByPatientId(id))
				.build();
		model.addAttribute("report",patientReportProxy.getReport(patientAndNotes));
		return "PatientInfo";
	}
	/**
	 * Affiche la page de mise à jour d'un patient.
	 * @param id : Identifiant du patient à mettre à jour.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de mise à jour d'un patient.
	 */
	@GetMapping("/PatientUpdate")
	public String patientUpdatePage(@RequestParam Integer id, Model model){
		PatientBean patient;
		try {
			// Tentative de récupération des informations du patient pour la mise à jour
			patient = patientInfoProxy.getPatientById(id);
		} catch (Exception e) {
			// Création d'un nouveau patient en cas d'erreur de récupération
			patient = new PatientBean();
		}
		model.addAttribute("patient",patient);
		return "PatientUpdate";
	}
	/**
	 * Valide la mise à jour d'un patient.
	 * @param id : Identifiant du patient à mettre à jour.
	 * @param patient : Objet PatientBean contenant les données mises à jour.
	 * @param result : Résultat de la validation.
	 * @param model : Modèle pour l'affichage des données.
	 * @return Redirige vers les informations du patient après la mise à jour.
	 */
	@PostMapping("/PatientUpdate")
	public String PatientUpdateValidation (
			@RequestParam Integer id,
			@Valid 
			@ModelAttribute("patient") PatientBean patient,
			BindingResult result,
			Model model)
	{
		log.info("Patient Attributes: {}", patient);
		// Vérification des erreurs de validation
		if (result.hasErrors()) {
            log.info("BindingResult = {}",result);
			return "PatientUpdate";
        }
		// Mise à jour du patient via le proxy
		PatientBean patientResult = patientInfoProxy.updatePatient(id, patient);
		// Gestion d'une éventuelle erreur lors de la mise à jour
		if (patientResult.getId()==null) {
			String errorMessage = "Sorry, " + patient.getFamily() + " " + patient.getGiven() + " already exist.";
			model.addAttribute("errorMessage",errorMessage);
			return "PatientUpdate";
		}
		// Redirection vers les informations du patient après la mise à jour
		return "redirect:/PatientInfo?id=" + id;
	}
	/**
	 * Affiche la page de création d'un nouveau patient.
	 * @param model : Modèle pour l'affichage des données.
	 * @return La vue de création d'un nouveau patient.
	 */
	@GetMapping("/PatientCreate")
	public String patientCreatePage(Model model){
		PatientBean patient = new PatientBean();
		model.addAttribute("patient",patient);
		return "PatientCreate";
	}
	/**
	 * Valide la création d'un nouveau patient.
	 * @param patient : Objet PatientBean contenant les données du nouveau patient.
	 * @param result : Résultat de la validation.
	 * @param model : Modèle pour l'affichage des données.
	 * @return Redirige vers les informations du nouveau patient après la création.
	 */
	@PostMapping("/PatientCreate")
	public String PatientCreateValidation (
			@Valid 
			@ModelAttribute("patient") PatientBean patient,
			BindingResult result,
			Model model)
	{
		log.info("Patient Attributes: {}", patient);
		// Vérification des erreurs de validation
		if (result.hasErrors()) {
            log.info("BindingResult = {}",result);
			return "PatientCreate";
        }
		// Création du nouveau patient via le proxy
		PatientBean patientResult = patientInfoProxy.createPatient(patient);
		// Gestion d'une éventuelle erreur lors de la création
		if (patientResult.getId()==null) {
			String errorMessage = "Sorry, " + patient.getFamily() + " " + patient.getGiven() + " already exist.";
			model.addAttribute("errorMessage",errorMessage);
			return "PatientCreate";
		}
		// Redirection vers les informations du nouveau patient après la création
		return "redirect:/PatientInfo?id=" + patientResult.getId();
	}
	
}
