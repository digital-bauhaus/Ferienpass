package de.bauhaus.digital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FerienpassApplication {

	private static final Logger LOG = LoggerFactory.getLogger(FerienpassApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(FerienpassApplication.class, args);

		checkIfMailtextIsPresentAndShutdownSpringBootIfNot(ctx);
	}

	private static void checkIfMailtextIsPresentAndShutdownSpringBootIfNot(ConfigurableApplicationContext ctx) {
		try {
			ctx.getBean("backendController");
			LOG.info("Precondition-Check bestanden: Env-Variable SENDGRID_API_KEY und Mail-Konfigurationsdatei gefunden!");
		} catch (NoSuchBeanDefinitionException noSuchBean) {
			LOG.error("Precondition-Check NICHT bestanden: Entweder es wurde die Env-Variable SENDGRID_API_KEY nicht gesetzt oder die Mail-Konfigurationsdatei backend/src/main/resources/mail/mailtext.txt nicht gefunden: " + noSuchBean.getMessage());
			ctx.close();
		}
	}
}
