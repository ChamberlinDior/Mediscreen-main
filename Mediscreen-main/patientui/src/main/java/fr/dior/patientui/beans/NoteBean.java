package fr.dior.patientui.beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteBean {
	
	private String id; // Identifiant de la note
	
	private Integer patId;  // Identifiant du patient associé à la note
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date; // Date de la note
	
    private String body;// Contenu de la note

}
