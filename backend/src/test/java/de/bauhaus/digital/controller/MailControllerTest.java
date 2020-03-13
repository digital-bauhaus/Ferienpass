package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmerBuilder;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.http.ContentType;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailControllerTest extends AbstractControllerTest {

    @Autowired
    private MailController mailController;

    @Test
    public void should_be_able_to_read_mail_text() throws IOException {
        String mailText = mailController.readMailText();

        assertThat(mailText, containsString("Das Ferienpass-Team Weimar"));
    }

    @Test
    public void sending_email_works() {

        // Please set SENDGRID_API_KEY=SG.xyz (see Heroku Config Vars!)

        Teilnehmer teilnehmer = createSampleTeilnehmerBuilder()
                .email("test@test.de")
                .build();

        given()
            .body(teilnehmer)
            .contentType(ContentType.JSON)
        .when()
            .post(BASE_URL + "/mail/")
        .then()
            .statusCode(HttpStatus.SC_CREATED);
    }
}
