package fr.dior.patientui.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
/**
 * Classe DTO (Data Transfer Object) pour PatientBean.
 * Il représente une version simplifiée de PatientBean, se concentrant sur le nom de famille et le prénom.
 * Utilisé pour la création et la mise à jour des informations du patient avec des champs limités.
 */
@Data
public class PatientBeanDTO {

	@NotBlank(message = "Last name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String family;

	@NotBlank(message = "First name is mandatory")
	@Size(max = 100, message = "100 characters maximum are allowed")
	private String given;
}
