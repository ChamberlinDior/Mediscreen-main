package fr.marc.patientui.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {

	private Integer id; // Identifiant du patient

	// Nom de famille selon la norme HL7 (Health Level Seven)
	@NotBlank(message = "Last name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String family;  // Nom de famille du patient

	// Prénom selon la norme HL7 (Health Level Seven)
	@NotBlank(message = "First name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String given; // Prénom du patient


	// Date de naissance selon la norme HL7 (Health Level Seven)
	@NotBlank(message = "Birthdate is mandatory")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date format must be YYYY-MM-DD")
	private String dob; // Date de naissance du patient


	// Deux valeurs autorisées : F ou M
	@NotBlank(message = "Sex is mandatory")
	@Pattern(regexp = "^[FM]$", message = "Only F or M is mandatory")
	@Size(max = 1, message = "1 character maximum are allowed (F or M)")
	private String sex;  // Sexe du patient

	@Size(max = 100, message = "100 characters maximum are allowed")
	private String address; // Adresse du patient


	@Size(max = 20, message = "100 characters maximum are allowed")
	private String phone; // Numéro de téléphone du patient

}
