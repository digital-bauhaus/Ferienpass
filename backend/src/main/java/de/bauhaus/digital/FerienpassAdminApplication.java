package de.bauhaus.digital;

import de.bauhaus.digital.domain.*;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;

@SpringBootApplication
public class FerienpassAdminApplication implements CommandLineRunner {

	@Autowired
	private TeilnehmerRepository teilnehmerRepository;

	@Autowired
	private ProjektRepository projektRepository;


	@Override
	public void run(String... args) throws Exception {
		createSampleUser();
		createSampleProject(
				"Ball Werfen",
				20,
				LocalDate.of(2018, 7, 16),
				LocalDate.of(2018, 7, 17),
				"Tasifan",
				10,
				17);
		createSampleProject(
				"Bauspielplatz",
				10,
				LocalDate.of(2018, 8, 02),
				LocalDate.of(2018, 8, 02),
				"Nordlicht e.V.",
				6,
				80);
		createSampleProject(
				"Papier-Werkstatt",
				8,
				LocalDate.of(2018, 7, 23),
				LocalDate.of(2018, 7, 25),
				"Sektion Weimar des Deutschen Alpenvereins e.V.",
				8,
				12);
	}

	private void createSampleUser() {
		Arzt arzt = new Arzt("Eich", "Route 1 Alabastia, 39829",
				"555-6891");
		Kontakt kontact = new Kontakt("Igor Eich", "Route 4 Neuborkia  96825", "555-2532");

		String essenLimitierungen = "Laktoseintoleranz";
		String krankheiten = "Grippe: Muss oft Husten und braucht Hustenbonbons";
		String allergien = "Heuschnupfen: braucht Nasenspray, siehe Medikamente";
		Behinderung behinderung = new Behinderung();
		behinderung.setRollstuhlNutzungNotwendig(true);
		behinderung.setMerkzeichen_Taubblind_TBL(true);
		String schwimmAbzeichen = "Seepferdchen";
		String hitze = "Wärme: bis 25 Grad ist alles okay";
		String medikamente = "Nasentropfen_ maximal 2x am Tag ein Schub";
		String email = "myEmail@weimar.de";

		Teilnehmer user = new Teilnehmer(
				"Gary",
				"Eich",
				LocalDate.of(2005,10,20),LocalDate.now(),
				"Bahnhofstraße 4",
				"Weimar",
				"99423",
				"03544444",
				"0453434",
				true,
				kontact,
				true,
				false,
				false,
				schwimmAbzeichen,
				false,
				false,
				arzt,
				allergien,
				essenLimitierungen,
				krankheiten,
				true,
				behinderung,
				hitze,
				medikamente,
				email);

		teilnehmerRepository.save(user);

		System.out.println(user.toString() + " successfully saved into DB");
	}

	private Projekt createSampleProject(String projektName, int slotsGesamt,
										LocalDate datum, LocalDate endeDatum,
										String traeger, int mindestAlter,
										int hoechstAlter) {
		Projekt project = new Projekt(projektName, datum, endeDatum,
				mindestAlter, hoechstAlter, 20, slotsGesamt, 1, traeger, "www" +
				".google.com");
		projektRepository.save(project);
		System.out.println(project.toString() + " successfully saved into DB");
		return project;
	}


	public static void main(String[] args) {
		SpringApplication.run(FerienpassAdminApplication.class, args);
	}

	// Enable CORS globally for requests from Anmeldung frontend in webpack dev-server mode
/*	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8080");
			}
		};
	}*/
}
