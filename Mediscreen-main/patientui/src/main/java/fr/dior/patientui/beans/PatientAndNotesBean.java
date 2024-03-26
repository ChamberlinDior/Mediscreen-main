package fr.dior.patientui.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientAndNotesBean {
	
	private PatientBean patient; // Informations sur le patient
	
	private Iterable<NoteBean> notes; // Liste des notes médicales associées au patient
}
