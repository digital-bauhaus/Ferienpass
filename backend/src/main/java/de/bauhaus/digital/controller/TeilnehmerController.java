package de.bauhaus.digital.controller;

import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.exception.UserNotFoundException;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import java.util.ArrayList;
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
@RequestMapping("/api/users")
public class TeilnehmerController {

    private static final Logger LOG = LoggerFactory.getLogger(TeilnehmerController.class);

    @Autowired
    private TeilnehmerRepository teilnehmerRepository;
    @Autowired
    private ProjektRepository projektRepository;

    @RequestMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Teilnehmer> getUsers() {
        LOG.info("GET called on /users resource");

        return teilnehmerRepository.findAllActive();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Teilnehmer getUserById(@PathVariable("id") long id) {
        LOG.info("GET called on /users/" + id);

        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(id);
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + id + " wurde nicht gefunden.");
        }

        return optionalTeilnehmer.get();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addUser(@RequestBody @Valid Teilnehmer teilnehmer) {
        LOG.info("POST called on /users resource");

        teilnehmerRepository.save(teilnehmer);

        LOG.info("Teilnehmer successfully saved into DB with id " + teilnehmer.getId());

        return teilnehmer.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Teilnehmer updateUser(@RequestBody @Valid Teilnehmer teilnehmer) {
        LOG.info("PUT called on /users resource");

        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmer.getId());
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + teilnehmer.getId() + " wurde nicht gefunden.");
        }

        // We directly save the Teilnehmer we received in the database
        Teilnehmer savedTeilnehmer = teilnehmerRepository.save(teilnehmer);

        LOG.info("Teilnehmer with id "+ teilnehmer.getId() + " successfully updated in DB.");
        return savedTeilnehmer;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") long id) {
        LOG.info("DELETE called on /users/" + id);

        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(id);
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + id + " wurde nicht gefunden.");
        }

        // Before we delete the Teilnehmer, we need to delete every Projekt assignment
        // otherwise we will run into https://github.com/digital-bauhaus/Ferienpass/issues/71
        Teilnehmer teilnehmer = optionalTeilnehmer.get();
        for (Projekt projekt : projektRepository.findAll()) {
            projekt.entferneTeilnehmerVonProjekt(teilnehmer);
        }

        teilnehmerRepository.deleteById(id);
        LOG.info("Teilnehmer with id "+ id + " deleted.");
    }

    @RequestMapping(path = "/{teilnehmerId}/projects")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getRegisteredProjectsOfUser(@PathVariable("teilnehmerId") Long teilnehmerId) {
        LOG.info("GET called on /users/" + teilnehmerId + "/projects");

        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmerId);
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + teilnehmerId + " wurde nicht gefunden.");
        }

        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer teilnehmer: projekt.getAngemeldeteTeilnehmer()) {
                if(teilnehmer.getId() == teilnehmerId)
                    resultList.add(projekt);
            }
        }
        LOG.info("Returning list with size of " + resultList.size());
        return resultList;
    }

    @RequestMapping(path = "/{teilnehmerId}/cancelledprojects")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getCancelledProjectsOfUser(@PathVariable("teilnehmerId") Long teilnehmerId) {
        LOG.info("GET called on /users/" + teilnehmerId + "/cancelledprojects");

        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(teilnehmerId);
        if (!optionalTeilnehmer.isPresent()) {
            throw new UserNotFoundException("Teilnehmer mit id " + teilnehmerId + " wurde nicht gefunden.");
        }

        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer teilnehmer: projekt.getStornierteTeilnehmer()) {
                if(teilnehmer.getId() == teilnehmerId)
                    resultList.add(projekt);
            }
        }
        LOG.info("Returning list with size of " + resultList.size());
        return resultList;
    }

}
