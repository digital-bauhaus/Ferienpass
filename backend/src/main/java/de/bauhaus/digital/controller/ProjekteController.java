package de.bauhaus.digital.controller;

import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.exception.ProjektNotFoundException;
import de.bauhaus.digital.exception.UserNotFoundException;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (optionalProjekt.isPresent()) {
            return optionalProjekt.get();
        } else {
            throw new ProjektNotFoundException("Projekt mit id " + id + " wurde nicht gefunden.");
        }
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
    Projekt updateProject(@RequestBody @Valid Projekt projekt) {
        LOG.info("PUT called on /projects resource");

        Optional<Projekt> optionalProjekt = projektRepository.findById(projekt.getId());
        if (optionalProjekt.isPresent()) {
            // We directly save the Projekt we received in the database
            Projekt savedProjekt = projektRepository.save(projekt);

            LOG.info("Projekt with id " + projekt.getId() + " successfully updated in DB.");
            return savedProjekt;
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projekt.getId() + " wurde nicht gefunden.");
        }
    }

    @RequestMapping(path = "/{projektId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable("projektId") Long projektId) {
        LOG.info("DELETE called on /projects/" + projektId);

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (optionalProjekt.isPresent()) {
            projektRepository.deleteById(projektId);
            LOG.info("Projekt with id " + projektId + " deleted.");
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    @GetMapping(path = "/{projektId}/users")
    public @ResponseBody
    List<Teilnehmer> getRegisteredUsersOfProject(@PathVariable("projektId") Long projektId) {
        LOG.info("GET called on /projects/" + projektId + "/users");

        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            Projekt projekt = maybeProjekt.get();
            LOG.info("Returning " + projekt.getAngemeldeteTeilnehmer().size() + " registered Teilnehmer for project " + projekt.getName());
            return projekt.getAngemeldeteTeilnehmer();
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    @RequestMapping(path = "/{projektId}/users/{teilnehmerId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Boolean assignUserToProject(@PathVariable("projektId") Long projektId, @PathVariable("teilnehmerId") Long teilnehmerId)
            throws UserNotFoundException, ProjektNotFoundException {
        LOG.info("PUT called on /projects/" + projektId + "/users/" + teilnehmerId);

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (optionalProjekt.isPresent()) {
            Projekt projekt = optionalProjekt.get();

            Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmerId);
            if (optionalTeilnehmer.isPresent()) {

                Teilnehmer teilnehmer = optionalTeilnehmer.get();
                if (projekt.hatProjektFreiePlaetze()) {
                    return assignUserToProjektWhenNotAlreadyAssigned(teilnehmer, projekt);
                } else {
                    LOG.info("Could not assign " + teilnehmer.getNachname() + " to project " + projekt.getName() + " because all free slots are taken.");
                    return false;
                }
            } else {
                throw new UserNotFoundException("Teilnehmer mit der id " + teilnehmerId + " wurde nicht gefunden.");
            }
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    @RequestMapping(path = "/{projektId}/users/{teilnehmerId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Boolean unassignUserFromProject(@PathVariable("projektId") Long projektId, @PathVariable("teilnehmerId") Long teilnehmerId) {
        LOG.info("DELETE called on /projects/" + projektId + "/users/" + teilnehmerId);

        Optional<Projekt> optionalProjekt = projektRepository.findById(projektId);
        if (optionalProjekt.isPresent()) {
            Projekt projekt = optionalProjekt.get();

            Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmerId);
            if (optionalTeilnehmer.isPresent()) {

                Teilnehmer teilnehmer = optionalTeilnehmer.get();
                projekt.storniereTeilnehmer(teilnehmer);
                projektRepository.save(projekt);
                LOG.info("Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " was cancelled for Projekt: " + projekt.getName());
                return true;
            } else {
                throw new UserNotFoundException("Teilnehmer mit der id " + teilnehmerId + " wurde nicht gefunden.");
            }
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    private Boolean assignUserToProjektWhenNotAlreadyAssigned(Teilnehmer teilnehmer, Projekt projekt) {
        if(projekt.isTeilnehmerNotAlreadyAsignedToProjekt(teilnehmer)) {
            projekt.meldeTeilnehmerAn(teilnehmer);
            projektRepository.save(projekt);
            LOG.info("Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " was registered for Projekt: " + projekt.toString());
        } else {
            LOG.info("Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " was already registered for Projekt: " + projekt.getName());
        }
        return true;
    }

}
