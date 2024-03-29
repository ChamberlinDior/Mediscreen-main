package fr.dior.patientReport.controller;

import java.time.LocalDate;

import fr.dior.patientReport.model.Report;
import fr.dior.patientReport.service.IPatientReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dior.patientReport.model.PatientAndNotes;

@RestController
@ComponentScan
@EnableAutoConfiguration
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
