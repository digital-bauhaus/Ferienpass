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

    @GetMapping(path = "/{projektId}")
    public @ResponseBody
    Projekt getProjectById(@PathVariable("projektId") Long projekt_id) {
        Optional<Projekt> maybeProjekt = projektRepository.findById(projekt_id);
        if (maybeProjekt.isPresent()) {
            return maybeProjekt.get();
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projekt_id + " wurde nicht gefunden.");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addProject(@RequestBody @Valid Projekt projekt) {

        projektRepository.save(projekt);

        LOG.info(projekt.toString() + " successfully saved into DB");

        return projekt.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Projekt updateProject(@RequestBody @Valid Projekt projekt) {

        Optional<Projekt> maybeProjekt = projektRepository.findById(projekt.getId());
        if (maybeProjekt.isPresent()) {
            Projekt foundProjekt = maybeProjekt.get();
            foundProjekt = projekt;
            projektRepository.save(foundProjekt);
            LOG.info("Projekt with id " + projekt.getId() + " successfully updated on DB.");
            return foundProjekt;
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projekt.getId() + " wurde nicht gefunden.");
        }
    }

    @RequestMapping(path = "/{projektId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Boolean deleteProject(@PathVariable("projektId") Long projektId) {

        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            projektRepository.deleteById(projektId);
            LOG.info("Projekt with id " + projektId + " has been deleted.");
            return true;
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    @GetMapping(path = "/{projektId}/users")
    public @ResponseBody
    List<Teilnehmer> getRegisteredUsersOfProject(@PathVariable("projektId") Long projektId) {
        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            Projekt projekt = maybeProjekt.get();
            LOG.info("Returning " + projekt.getAngemeldeteTeilnehmer().size() + " registered participants for project " + projekt.getName());
            return projekt.getAngemeldeteTeilnehmer();
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    @RequestMapping(path = "/{projektId}/users/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Boolean assignUserToProject(@PathVariable("projektId") Long projektId, @PathVariable("userId") Long userId)
            throws UserNotFoundException, ProjektNotFoundException {

        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            Projekt projekt = maybeProjekt.get();

            Optional<Teilnehmer> maybeTeilnehmer = teilnehmerRepository.findById(userId);
            if (maybeTeilnehmer.isPresent()) {

                Teilnehmer teilnehmer = maybeTeilnehmer.get();
                if (projekt.hasProjektFreeSlots()) {
                    return assignUserToProjektWhenNotAlreadyAssigned(teilnehmer, projekt);
                } else {
                    LOG.info("Could not assign " + teilnehmer.getNachname() + " to project " + projekt.getName() + " because all free slots are taken.");
                    return false;
                }
            } else {
                throw new UserNotFoundException("Teilnehmer mit der id " + userId + " wurde nicht gefunden.");
            }
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    private Boolean assignUserToProjektWhenNotAlreadyAssigned(Teilnehmer teilnehmer, Projekt projekt) {
        if(projekt.isTeilnehmerNotAlreadyAsignedToProjekt(teilnehmer)) {
            projekt.addAnmeldung(teilnehmer);
            projektRepository.save(projekt);
            LOG.info("Der Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " wurde bei folgenden Projekt angemeldet: " + projekt.toString());
        } else {
            LOG.info("Teilnehmer " + teilnehmer.getNachname() + " bereits bei Projekt angemeldet " + projekt.getName() + ".");
        }
        return true;
    }

    @RequestMapping(path = "/{projektId}/users/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Boolean unassignUserFromProject(@PathVariable("projektId") Long projektId, @PathVariable("userId") Long userId) {

        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            Projekt projekt = maybeProjekt.get();

            Optional<Teilnehmer> maybeTeilnehmer = teilnehmerRepository.findById(userId);
            if (maybeTeilnehmer.isPresent()) {

                Teilnehmer teilnehmer = maybeTeilnehmer.get();
                projekt.addStornierung(teilnehmer);
                projektRepository.save(projekt);
                LOG.info("Der Teilnehmer " + teilnehmer.getVorname() + " " + teilnehmer.getNachname() + " wurde aus dem folgenden Projekt abgemeldet: " + projekt.toString());
                return true;
            } else {
                throw new UserNotFoundException("Teilnehmer mit der id " + userId + " wurde nicht gefunden.");
            }
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

}
