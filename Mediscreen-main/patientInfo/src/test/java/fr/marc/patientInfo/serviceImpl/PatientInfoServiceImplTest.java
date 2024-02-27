package fr.marc.patientInfo.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.marc.patientInfo.model.Patient;
import fr.marc.patientInfo.repository.PatientInfoRepository;
import fr.marc.patientInfo.service.IPatientInfoService;

@ExtendWith(MockitoExtension.class)
public class PatientInfoServiceImplTest {
	
	private IPatientInfoService patientInfoService;
	
	@Mock
	private PatientInfoRepository patientInfoRepository;
	
	private Patient patient1;
	private Patient patient2;
	private Patient patient3;
	
	@Captor
	ArgumentCaptor<Patient> patientCaptor;
	
	
	@BeforeEach
	public void init() {
		patientInfoService = new PatientInfoServiceImpl(patientInfoRepository);
		patient1 = Patient.builder()
				.id(1)
				.family("Last1")
				.given("First1")
				.dob("2001-01-01")
				.sex("F")
				.address("Address1")
				.phone("111111")
				.build();
		patient2 = Patient.builder()
				.id(2)
				.family("Last2")
				.given("First2")
				.dob("2002-02-02")
				.sex("M")
				.address("Address2")
				.phone("222222")
				.build();
		patient3 = Patient.builder()
				.id(3)
				.family("Last3")
				.given("First3")
				.dob("2003-03-03")
				.sex("F")
				.address("Address3")
				.phone("333333")
				.build();
	}
	
	@Test
	public void getPatients_success() {
		when(patientInfoRepository.findAllByOrderByFamilyAsc())
			.thenReturn(Arrays.asList(patient1,patient2,patient3));
		assertThat(patientInfoService.getPatients())
			.contains(patient1)
			.contains(patient2)
			.contains(patient3);
		verify(patientInfoRepository).findAllByOrderByFamilyAsc();
	}
	
	@Nested
	class GetPatientById {
		@Test
		public void success() {
			when(patientInfoRepository.findById(1))
				.thenReturn(Optional.of(patient1));
			assertThat(patientInfoService.getPatientById(1))
				.isEqualTo(Optional.of(patient1));
			verify(patientInfoRepository).findById(1);
		}
		
		@Test
		public void no_patient() {
			when(patientInfoRepository.findById(5))
				.thenReturn(Optional.of(new Patient()));
			assertThat(patientInfoService.getPatientById(5))
				.isEqualTo(Optional.of(new Patient()));
			verify(patientInfoRepository).findById(5);
		}
	}
	
	@Nested
	class GetPatientsByFamilyAndGiven {
		@Test
		public void success() {
			when(patientInfoRepository.findByFamilyAndGiven("Last1","First1"))
				.thenReturn(Arrays.asList(patient1));
			assertThat(patientInfoService.getPatientsByFamilyAndGiven("Last1","First1"))
				.contains(patient1);
			verify(patientInfoRepository).findByFamilyAndGiven("Last1","First1");
		}
		
		@Test
		public void no_patient() {
			when(patientInfoRepository.findByFamilyAndGiven("Last5","First5"))
				.thenReturn(new ArrayList<>());
			assertThat(patientInfoService.getPatientsByFamilyAndGiven("Last5","First5"))
				.isEmpty();
			verify(patientInfoRepository).findByFamilyAndGiven("Last5","First5");
		}
	}
	
	@Nested
	class GetPatientByFamilyAndGivenAndDob {
		@Test
		public void success() {
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01"))
				.thenReturn(Optional.of(patient1));
			assertThat(patientInfoService.getPatientByFamilyAndGivenAndDob("Last1","First1","2001-01-01"))
				.isEqualTo(Optional.of(patient1));
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01");
		}
		
		@Test
		public void no_patient() {
			
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last5","First5","2005-05-05"))
				.thenReturn(Optional.of(new Patient()));
			assertThat(patientInfoService.getPatientByFamilyAndGivenAndDob("Last5","First5","2005-05-05"))
				.isEqualTo(Optional.of(new Patient()));
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last5","First5","2005-05-05");
		}
	}
	
	@Nested
	class UpdatePatient {
		@Test
		public void success() {
			Patient updatedPatient = Patient.builder()
					.id(3)
					.family("Last3")
					.given("First3")
					.dob("2003-03-03")
					.sex("F")
					.address("Address4")
					.phone("444444")
					.build();
			when(patientInfoRepository.findById(3))
				.thenReturn(Optional.of(patient3));
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last3","First3","2003-03-03"))
				.thenReturn(Optional.of(patient3));
			when(patientInfoRepository.save(patient3))
				.thenReturn(updatedPatient);
			Patient returnPatient = patientInfoService.updatePatient(3, updatedPatient);
			verify(patientInfoRepository).save(patientCaptor.capture());
			assertThat(patientCaptor.getValue())
				.isEqualTo(patient3);
		    assertThat(returnPatient).isEqualTo(updatedPatient);
			verify(patientInfoRepository).findById(3);
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last3","First3","2003-03-03");
			verify(patientInfoRepository).save(patient3);
		}
		
		@Test
		public void no_patient_with_this_id_should_return_null() {
			Patient updatedPatient = Patient.builder()
					.id(5)
					.family("Last3")
					.given("First3")
					.dob("2003-03-03")
					.sex("F")
					.address("Address4")
					.phone("444444")
					.build();
			when(patientInfoRepository.findById(5))
				.thenReturn(Optional.empty());
			Patient returnPatient = patientInfoService.updatePatient(5, updatedPatient);
		    List<Patient> capturedPatients = patientCaptor.getAllValues();
		    assertThat(capturedPatients).isEmpty();
		    assertThat(returnPatient).isNull();
			verify(patientInfoRepository).findById(5);
		}
		
		@Test
		public void patient_already_exist_should_return_new_patient() {
			Patient updatedPatient = Patient.builder()
					.id(3)
					.family("Last1")
					.given("First1")
					.dob("2001-01-01")
					.sex("F")
					.address("Address3")
					.phone("333333")
					.build();
			when(patientInfoRepository.findById(3))
				.thenReturn(Optional.of(patient3));
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01"))
				.thenReturn(Optional.of(patient1));
			Patient returnPatient = patientInfoService.updatePatient(3, updatedPatient);
		    List<Patient> capturedPatients = patientCaptor.getAllValues();
			assertThat(capturedPatients).isEmpty();
		    assertThat(returnPatient).isEqualTo(new Patient());
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01");
			verify(patientInfoRepository).findById(3);
		}
	}
	
	@Nested
	class CreatePatient {
		@Test
		public void success() {
			Patient patientToCreate = Patient.builder()
					.family("Last4")
					.given("First4")
					.dob("2004-04-04")
					.sex("M")
					.address("Address4")
					.phone("444444")
					.build();
			Patient patientCreated = Patient.builder()
					.id(4)
					.family("Last4")
					.given("First4")
					.dob("2004-04-04")
					.sex("M")
					.address("Address4")
					.phone("444444")
					.build();
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last4","First4","2004-04-04"))
				.thenReturn(Optional.empty());
			when(patientInfoRepository.save(patientToCreate))
				.thenReturn(patientCreated);
			Patient returnPatient = patientInfoService.createPatient(patientToCreate);
			verify(patientInfoRepository).save(patientCaptor.capture());
			assertThat(patientCaptor.getValue())
				.isEqualTo(patientToCreate);
		    assertThat(returnPatient).isEqualTo(patientCreated);
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last4","First4","2004-04-04");
			verify(patientInfoRepository).save(patientToCreate);
		}
		
		@Test
		public void patient_already_exist() {
			Patient patientToCreate = Patient.builder()
					.family("Last1")
					.given("First1")
					.dob("2001-01-01")
					.sex("F")
					.address("Address1")
					.phone("111111")
					.build();
			when(patientInfoRepository.findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01"))
				.thenReturn(Optional.of(patient1));
			Patient returnPatient = patientInfoService.createPatient(patientToCreate);
		    assertThat(returnPatient).isEqualTo(new Patient());
			verify(patientInfoRepository).findFirstByFamilyAndGivenAndDob("Last1","First1","2001-01-01");
		}
	}
	
}
