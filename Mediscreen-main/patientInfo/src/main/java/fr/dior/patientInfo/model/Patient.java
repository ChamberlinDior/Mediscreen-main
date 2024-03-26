package fr.dior.patientInfo.model;


import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "patient")
public class Patient {

	// Identifiant unique généré automatiquement pour chaque patient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// Nom de famille selon le standard HL7 (Health Level Seven)
	private String family;

	// Prénom selon le standard HL7 (Health Level Seven)
	private String given;

	// Date de naissance selon le standard HL7 (Health Level Seven)
	private String dob;

	// Deux valeurs possibles : F pour féminin, M pour masculin
	private String sex;
	// Adresse du patient
	private String address;
	// Numéro de téléphone du patient
	private String phone;

}
