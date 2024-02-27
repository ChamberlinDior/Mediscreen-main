package fr.marc.patientReport.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.marc.patientReport.model.Note;
import fr.marc.patientReport.model.Patient;
import fr.marc.patientReport.model.Report;
import fr.marc.patientReport.model.Triggers;
import fr.marc.patientReport.repository.PatientReportRepository;

@ExtendWith(MockitoExtension.class)
public class PatientReportServiceImplTest {
	
	private PatientReportServiceImpl patientReportService;
	
	@Mock
	private PatientReportRepository patientReportRepository;
	
	@BeforeEach
	public void init() {
		patientReportService = new PatientReportServiceImpl(patientReportRepository);
	}
	
	@Test
	public void calculateAge_should_return_56() {
		Patient patient = Patient.builder()
				.dob("1966-11-25")
				.build();
		LocalDate currentDate = LocalDate.of(2023,04,10);
		assertThat(patientReportService.calculateAge(patient, currentDate))
			.isEqualTo(56);
	}
	
	@Test
	public void calculateTriggersNumber_should_return_2() {
		Note note1 = Note.builder()
				.body("Lorem Cholestérol Ipsum")
				.build();
		Note note2 = Note.builder()
				.body("Vertige Bla Bla")
				.build();
		List<Note> notes = List.of(note1,note2);
		Triggers trigger1 = new Triggers(1,"cholestérol");
		Triggers trigger2 = new Triggers(2,"fumeur");
		Triggers trigger3 = new Triggers(3,"vertige");
		List<Triggers> triggers = List.of(trigger1,trigger2,trigger3);
		when(patientReportRepository.findAll())
			.thenReturn(triggers);
		assertThat(patientReportService.calculateTriggersNumber(notes))
			.isEqualTo(2);
	}
	
	@Test
	public void assesTheRisk_different_data() {
		assertThat(patientReportService.assessTheRisk("F",29,2))
			.isEqualTo("None");
		assertThat(patientReportService.assessTheRisk("F",29,4))
			.isEqualTo("Danger");
		assertThat(patientReportService.assessTheRisk("F",29,7))
			.isEqualTo("Early onset");
		assertThat(patientReportService.assessTheRisk("M",29,3))
			.isEqualTo("Danger");
		assertThat(patientReportService.assessTheRisk("M",29,5))
			.isEqualTo("Early onset");
		assertThat(patientReportService.assessTheRisk("F",33,2))
			.isEqualTo("Borderline");
		assertThat(patientReportService.assessTheRisk("M",33,7))
			.isEqualTo("Danger");
		assertThat(patientReportService.assessTheRisk("F",33,8))
			.isEqualTo("Early onset");
	}
	
	@Test
	public void calculateDiabetesRiskTest() {
		Patient patient = Patient.builder()
				.sex("M")
				.dob("1966-11-25")
				.build();
		LocalDate currentDate = LocalDate.of(2023,04,10);
		Note note1 = Note.builder()
				.body("Lorem Cholestérol Ipsum")
				.build();
		Note note2 = Note.builder()
				.body("Vertige Bla Bla")
				.build();
		List<Note> notes = List.of(note1,note2);
		Triggers trigger1 = new Triggers(1,"cholestérol");
		Triggers trigger2 = new Triggers(2,"fumeur");
		Triggers trigger3 = new Triggers(3,"vertige");
		List<Triggers> triggers = List.of(trigger1,trigger2,trigger3);
		when(patientReportRepository.findAll())
			.thenReturn(triggers);
		Report report = patientReportService.calculateDiabetesRisk(patient,notes,currentDate);
		assertThat(report.getAge()).isEqualTo(56);
		assertThat(report.getAssessment()).isEqualTo("Borderline");
	}

}
