package fr.marc.patientInfo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.marc.patientInfo.model.Patient;

@Repository
public interface PatientInfoRepository extends CrudRepository<Patient, Integer> {

	/**
	 * Pour récupérer une liste de patients correspondant à un nom de famille et un prénom
	 * @param family : Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given : Prénom selon le standard HL7 (Health Level Seven)
	 * @return Une liste de patients correspondant à ce nom de famille et prénom
	 */
	List<Patient> findByFamilyAndGiven(String family, String given);

	/**
	 * Pour récupérer un patient correspondant à un nom de famille, un prénom et une date de naissance
	 * @param family : Nom de famille selon le standard HL7 (Health Level Seven)
	 * @param given : Prénom selon le standard HL7 (Health Level Seven)
	 * @param dob : Date de naissance selon le standard HL7 (Health Level Seven)
	 * @return Le premier patient correspondant à un nom de famille, un prénom et une date de naissance
	 */
	Optional<Patient> findFirstByFamilyAndGivenAndDob(String family, String given, String dob);

	/**
	 * Pour récupérer la liste de tous les patients triés par ordre alphabétique selon le nom de famille
	 * @return la liste de tous les patients triés par ordre alphabétique selon le nom de famille
	 */
	Iterable<Patient> findAllByOrderByFamilyAsc();

}
