package fr.marc.patientReport.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

	private String id;

	private Integer patId;

	private LocalDateTime date;

	private String body;

}
