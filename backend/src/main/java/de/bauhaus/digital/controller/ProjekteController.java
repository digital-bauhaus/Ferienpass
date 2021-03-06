package de.bauhaus.digital.controller;

import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.exception.ProjektFullyBookedException;
import de.bauhaus.digital.exception.ProjektNotFoundException;
import de.bauhaus.digital.exception.UserNotFoundException;
import de.bauhaus.digital.exception.ValidationException;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/projects")
@Validated
public class ProjekteController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjekteController.class);

    @Autowired
    private TeilnehmerRepository teilnehmerRepository;
    @Autowired
    private ProjektRepository projektRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getProjects() {
        LOG.info("GET called on /projects resource");

        return projektRepository.findAllActiveSortedByDatum();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Projekt getProjectById(@PathVariable("id") Long id) {
        LOG.info("GET called on /projects/" + id);

        Optional<Projekt> optionalProjekt = projektRepository.findById(id);
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + id + " wurde nicht gefunden.");
        }

        return optionalProjekt.get();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addProject(@RequestBody @Valid Projekt projekt) {
        LOG.info("POST called on /projects resource");

        projektRepository.save(projekt);

        LOG.info("Projekt successfully saved into DB with id " + projekt.getId());

        return projekt.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Projekt updateProject(@RequestBody @Valid Projekt projekt) throws Throwable {
        LOG.info("PUT called on /projects resource");

        Optional<Projekt> optionalProjekt = projektRepository.findById(projekt.getId());
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + projekt.getId() + " wurde nicht gefunden.");
        }

        // Important: Since we ignore the Anmeldungen and Stornierungen to and from the JSON,
        // we have to explicitly use the ones from the original, so they do not get lost
        Projekt originalProjekt = optionalProjekt.get();
        Projekt projektWithTeilnehmern = Projekt.newBuilder(projekt)
                .angemeldeteTeilnehmer(originalProjekt.getAngemeldeteTeilnehmer())
                .stornierteTeilnehmer(originalProjekt.getStornierteTeilnehmer())
                .build();

        // We directly save the Projekt we received in the database
        // Note: Since we do not have access to the Anmeldungen-List in the JSON (this property is ignored)
        // we have to do some checks here
        if (projektWithTeilnehmern.getPlaetzeGesamt() < projektWithTeilnehmern.getPlaetzeBelegt()) {
            throw new ValidationException("Es dürfen nicht weniger Gesamtplätze angegeben werden, als aktuell belegt sind.");
        }

        Projekt savedProjekt = projektRepository.save(projektWithTeilnehmern);

        LOG.info("Projekt with id " + projektWithTeilnehmern.getId() + " successfully updated in DB.");
        return savedProjekt;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable("id") Long id) {
        LOG.info("DELETE called on /projects/" + id);

        Optional<Projekt> optionalProjekt = projektRepository.findById(id);
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + id + " wurde nicht gefunden.");
        }

        projektRepository.deleteById(id);
        LOG.info("Projekt with id " + id + " deleted.");
    }

    @GetMapping(path = "/{projektId}/users")
    public @ResponseBody
    List<Teilnehmer> getRegisteredUsersOfProject(@PathVariable("projektId") Long projektId) {
        LOG.info("GET called on /projects/" + projektId + "/users");

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + projektId + " wurde nicht gefunden.");
        }

        Projekt projekt = optionalProjekt.get();
        LOG.info("Returning " + projekt.getAngemeldeteTeilnehmer().size() + " registered Teilnehmer for project " + projekt.getName());
        return projekt.getAngemeldeteTeilnehmer();
    }

    @GetMapping(path = "/{projektId}/cancelledusers")
    public @ResponseBody
    List<Teilnehmer> getCancelledUsersOfProject(@PathVariable("projektId") Long projektId) {
        LOG.info("GET called on /projects/" + projektId + "/cancelledusers");

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + projektId + " wurde nicht gefunden.");
        }

        Projekt projekt = optionalProjekt.get();
        LOG.info("Returning " + projekt.getStornierteTeilnehmer().size() + " cancelled Teilnehmer for project " + projekt.getName());
        return projekt.getStornierteTeilnehmer();
    }

    @RequestMapping(path = "/{projektId}/users/{teilnehmerId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void assignUserToProject(@PathVariable("projektId") Long projektId, @PathVariable("teilnehmerId") Long teilnehmerId)
            throws UserNotFoundException, ProjektNotFoundException {
        LOG.info("PUT called on /projects/" + projektId + "/users/" + teilnehmerId);

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + projektId + " wurde nicht gefunden.");
        }
        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmerId);
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + teilnehmerId + " wurde nicht gefunden.");
        }

        Projekt projekt = optionalProjekt.get();
        Teilnehmer teilnehmer = optionalTeilnehmer.get();
        if (projekt.hatProjektFreiePlaetze()) {
            meldeTeilnehmerAnWennNichtBereitsAngemeldet(teilnehmer, projekt);
        } else {
            LOG.info("Could not assign " + teilnehmer.getNachname() + " to project " + projekt.getName() + " because all free slots are taken.");
            throw new ProjektFullyBookedException("Veranstaltung mit id " + projektId + " ist leider schon komplett ausgebucht.");
        }
    }

    @RequestMapping(path = "/{projektId}/users/{teilnehmerId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void unassignUserFromProject(@PathVariable("projektId") Long projektId, @PathVariable("teilnehmerId") Long teilnehmerId) {
        LOG.info("DELETE called on /projects/" + projektId + "/users/" + teilnehmerId);

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (!optionalProjekt.isPresent()) {
            throw new ProjektNotFoundException("Veranstaltung mit id " + projektId + " wurde nicht gefunden.");
        }
        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmerId);
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + teilnehmerId + " wurde nicht gefunden.");
        }

        Projekt projekt = optionalProjekt.get();
        Teilnehmer teilnehmer = optionalTeilnehmer.get();
        projekt.storniereTeilnehmer(teilnehmer);
        projektRepository.save(projekt);
        LOG.info("Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " was cancelled for Projekt: " + projekt.getName());
    }

    private void meldeTeilnehmerAnWennNichtBereitsAngemeldet(Teilnehmer teilnehmer, Projekt projekt) {
        if(!projekt.istTeilnehmerAngemeldet(teilnehmer)) {
            projekt.meldeTeilnehmerAn(teilnehmer);
            projektRepository.save(projekt);
            LOG.info("Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " registered for Projekt: " + projekt.toString());
        } else {
            LOG.info("Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " was already registered for Projekt: " + projekt.getName());
        }
    }

}
