package de.bauhaus.digital.controller;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.TrackingSettings;
import de.bauhaus.digital.domain.Teilnehmer;
import java.io.IOException;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/mail")
@ConditionalOnResource(resources = "mail/mailtext.txt") // check, whether mail/mailtext.txt is on the classpath, since we need it for sending mails!
@ConditionalOnProperty(
        value = "SENDGRID_API_KEY" // check, whether SENDGRID_API_KEY environment variable is set, since we need it for sending mails!
)
public class MailController {

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);

    @Value(value = "classpath:mail/mailtext.txt")
    private Resource mailtext;

    @Value(value = "${ferienpass.confirmmail.bcc}")
    private String confirmMailBcc;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void sendMail(@RequestBody Teilnehmer teilnehmer) throws IOException {
        LOG.info("POST called on /mail resource");

        Mail mail = new Mail();

        // tracking settings
        TrackingSettings trackingSettings = new TrackingSettings();
        ClickTrackingSetting clickTrackingSetting = new ClickTrackingSetting();
        clickTrackingSetting.setEnableText(false);
        trackingSettings.setClickTrackingSetting(clickTrackingSetting);
        mail.setTrackingSettings(trackingSettings);

        // sender
        Email from = new Email("ferienpass@stadtweimar.de", "Ferienpass Weimar");
        mail.setFrom(from);

        // subject
        mail.setSubject("Ihre Anmeldung für den Ferienpass Weimar: Nur ein Schritt fehlt noch!");

        // recipient and bcc for ferienbuero
        Email toTeilnehmer = new Email(teilnehmer.getEmail());
        Email toFerienbuero = new Email(confirmMailBcc);
        Personalization personalization = new Personalization();
        personalization.addTo(toTeilnehmer);
        personalization.addBcc(toFerienbuero);
        mail.addPersonalization(personalization);

        // content
        Content content = new Content("text/plain", readMailText());
        mail.addContent(content);

        // actual mail request
        Request sendgridRequest = new Request();
        sendgridRequest.setMethod(Method.POST);
        sendgridRequest.setEndpoint("mail/send");
        sendgridRequest.setBody(mail.build());

        SendGrid sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Response sendgridResponse = sendGrid.api(sendgridRequest);

        LOG.info("AnmeldebestaetigungsMail send to " + teilnehmer.getEmail()
                + " with StatusCode " + sendgridResponse.getStatusCode()
                + " & Headers " + sendgridResponse.getHeaders());
    }

    protected String readMailText() throws IOException {
        return StreamUtils.copyToString(mailtext.getInputStream(), Charset.defaultCharset());
    }

}
