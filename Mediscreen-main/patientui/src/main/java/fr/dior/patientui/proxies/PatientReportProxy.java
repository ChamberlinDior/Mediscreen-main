package fr.dior.patientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.dior.patientui.beans.PatientAndNotesBean;
import fr.dior.patientui.beans.ReportBean;

// Déclaration de l'interface Feign Client avec le nom du service distant et son URL
@FeignClient(name = "mediscreen-patientReport", url = "http://patientreport:8083")
public interface PatientReportProxy {
	// Déclaration d'une méthode POST pour obtenir un rapport basé sur les informations du patient et de ses notes
	@PostMapping("/PatientReport")
	public ReportBean getReport(@RequestBody PatientAndNotesBean patientAndNotes);
}
