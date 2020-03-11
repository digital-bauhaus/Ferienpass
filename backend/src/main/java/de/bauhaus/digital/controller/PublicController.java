package de.bauhaus.digital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import de.bauhaus.digital.transformation.AnmeldungJson;
import de.bauhaus.digital.transformation.AnmeldungToAdmin;
import de.bauhaus.digital.transformation.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ResponseEntity<?> registerUser(@RequestBody @Valid Teilnehmer user) {

        Teilnehmer savedUser;

        Optional<Teilnehmer> maybeTeilnehmer = teilnehmerRepository.findById(user.getId());
        if (maybeTeilnehmer.isPresent()) {
            // we already saved the Teilnehmer before, so we update it
            savedUser = teilnehmerRepository.save(user);
        } else {
            savedUser = teilnehmerRepository.save(user);
        }

        List<Pair<Long, Boolean>> projectsWithAssignmentState = savedUser.getGewuenschteProjekte().stream().map((projektId) -> {
            return Pair.of(projektId,
                    projekteController.assignUserToProject(projektId, savedUser.getId()));
        }).collect(Collectors.toList());
        System.out.println(projectsWithAssignmentState.toString());

        if (projectsWithAssignmentState.stream().noneMatch(Pair::getSecond)) {
            // we have at least one project that could not be assigned
            return ResponseEntity.status(HttpStatus.CONFLICT).body(projectsWithAssignmentState);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.getId());
    }

    /*******************************************
     * API for registering from Ferienpass-Anmeldung Microservice
     ******************************************/

    @RequestMapping(path = "/registerold", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerNewTeilnehmer(@RequestBody AnmeldungJson anmeldungJson) throws Exception {

        LOG.info("New POST request from Ferienpass-Anmeldung Microservice containing new Teilnehmer");
        ObjectMapper mapper = new ObjectMapper();
        LOG.info("The anmeldungJson looks like: " + mapper.writeValueAsString(anmeldungJson));

        Teilnehmer neuAngemeldeterTeilnehmer = AnmeldungToAdmin.mapAnmeldedataToTeilnehmer(anmeldungJson);

        boolean oneOrMoreProjekteVoll = false;

        for (Project project : anmeldungJson.getProjects()) {
            if(project.isRegistered()) {
                Projekt projekt = projektRepository.findById(project.getId().longValue()).orElse(null);
                if(projekt.hatProjektFreiePlaetze()) {
                    projekt.meldeTeilnehmerAn(neuAngemeldeterTeilnehmer);
                } else {
                    LOG.info("The Projekt " + projekt.getName() + " with Id " + projekt.getId() + " has no free Slots left!");
                    oneOrMoreProjekteVoll = true;
                }
            }
        }

        if (oneOrMoreProjekteVoll) {
            List<Long> everyProjektWithoutFreeSlots = addEveryProjektThatHasNoFreeSlots();
            LOG.info("Respond with Http 409 Conflict and the list of all Projekt´s, that has no free slots.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(everyProjektWithoutFreeSlots);
        }

        Teilnehmer savedTeilnehmer = teilnehmerRepository.save(neuAngemeldeterTeilnehmer);
        mailController.sendMail(savedTeilnehmer);

        LOG.info("Successfully saved new Teilnehmer " + neuAngemeldeterTeilnehmer.toString() + " into Admin-Backend-DB");

        return new ResponseEntity(savedTeilnehmer.getId(), HttpStatus.CREATED);
    }

    private List<Long> addEveryProjektThatHasNoFreeSlots() {
        List<Long> projekteOhneFreiSlots = new ArrayList<>();
        projektRepository.findAllActiveSortedByDatum().forEach(projekt -> {
            if (!projekt.hatProjektFreiePlaetze()) {
                projekteOhneFreiSlots.add(projekt.getId());
            }
        });
        return projekteOhneFreiSlots;
    }

}
