package fr.dior.patientReport.repository;

import fr.dior.patientReport.model.Triggers;
import org.springframework.data.repository.CrudRepository;

public interface PatientReportRepository extends CrudRepository<Triggers, Integer> {

}
