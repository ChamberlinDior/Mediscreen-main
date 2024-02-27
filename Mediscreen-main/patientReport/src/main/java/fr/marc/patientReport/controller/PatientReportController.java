package fr.marc.patientReport.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.marc.patientReport.model.Note;
import fr.marc.patientReport.model.Patient;
import fr.marc.patientReport.model.PatientAndNotes;
import fr.marc.patientReport.model.Report;
import fr.marc.patientReport.service.IPatientReportService;

@RestController
public class PatientReportController {
	
	private static final Logger log = LoggerFactory.getLogger(PatientReportController.class); 
	
	@Autowired
	private IPatientReportService patientReportService;
	
	@PostMapping("/PatientReport")
	public Report getReport(
			@RequestBody PatientAndNotes patientAndNotes) 
	{
		log.info("Get report for patient with id = {}",patientAndNotes.getPatient().getId());
		LocalDate currentDate = LocalDate.now();
		return patientReportService.calculateDiabetesRisk(
				patientAndNotes.getPatient(),
				patientAndNotes.getNotes(),
				currentDate);
	}

}
