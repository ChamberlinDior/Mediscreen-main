package fr.dior.patientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
// Annotation pour activer la découverte des clients Feign dans le package "fr.marc.patientui
@EnableFeignClients("fr.dior.patientui")
@SpringBootApplication
public class PatientuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientuiApplication.class, args);
	}

}
