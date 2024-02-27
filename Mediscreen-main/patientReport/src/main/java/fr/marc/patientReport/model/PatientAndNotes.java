package fr.marc.patientReport.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientAndNotes {
	
	private Patient patient;
	
	private List<Note> notes;
}
