package de.bauhaus.digital.controller;

import com.fasterxml.jackson.annotation.JsonView;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.exception.ProjektNotFoundException;
import de.bauhaus.digital.exception.RegistrationInvalidException;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/public")
public class PublicController {

    private static final Logger LOG = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private TeilnehmerRepository teilnehmerRepository;
    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private MailController mailController;

    @Autowired
    private ProjekteController projekteController;

    /*************************************
     * Registration API
     *************************************/

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void registerUser(@JsonView(Views.Public.class) @RequestBody @Valid Teilnehmer user) throws IOException {
        LOG.info("POST called on /public/register resource");

        if (user.getGewuenschteProjekte().isEmpty()) {
            throw new RegistrationInvalidException("Eine Anmeldung ohne gewählte Projekte ist nicht möglich.");
        }

        Teilnehmer savedTeilnehmer = teilnehmerRepository.save(user);

        user.getGewuenschteProjekte().forEach((projektId) -> {
            Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
            if (!optionalProjekt.isPresent()) {
                throw new ProjektNotFoundException("Projekt mit id " + projektId + " wurde nicht gefunden.");
            }

            // Throws FullyBookedException if not possible
            projekteController.assignUserToProject(projektId, savedTeilnehmer.getId());
        });

        mailController.sendMail(savedTeilnehmer);
    }

    /*************************************
     * Project API
     *************************************/

    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getProjects() {
        LOG.info("GET called on /public/projects resource");

        return projekteController.getProjects();
    }

}
