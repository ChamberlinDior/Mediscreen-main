package fr.marc.patientNote.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Représente une note médicale associée à un patient.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notes")
public class Note {

	@Id
	private String id;  // Identifiant unique de la note

	private Integer patId; // Identifiant du patient associé à la note

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime date;// Date de la note

	private String body; // Contenu de la note

	/**
	 * Constructeur pour créer une nouvelle instance de Note avec les détails spécifiés.
	 * @param patId Identifiant du patient associé à la note.
	 * @param date Date de la note.
	 * @param body Contenu de la note.
	 */

	public Note(Integer patId, LocalDateTime date, String body) {
		this.patId = patId;
		this.date = date;
		this.body = body;
	}

}
