package fr.marc.patientReport.repository;

import org.springframework.data.repository.CrudRepository;

import fr.marc.patientReport.model.Triggers;

public interface PatientReportRepository extends CrudRepository<Triggers, Integer> {

}
