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
        LOG.info("GET called on /users/" + id + "");
        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(id);
        if(optionalTeilnehmer.isPresent()) {
            return optionalTeilnehmer.get();
        } else {
            throw new UserNotFoundException("Teilnehmer with id " + id + " not found.");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addUser(@RequestBody @Valid Teilnehmer user) {
        LOG.info("POST called on /users resource");

        teilnehmerRepository.save(user);

        LOG.info("Teilnehmer successfully saved into DB with id " + user.getId());

        return user.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Teilnehmer updateUser(@RequestBody @Valid Teilnehmer user) {
        LOG.info("PUT called on /users resource");

        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(user.getId());
        if (optionalTeilnehmer.isPresent()) {
            // We directly save the user we received in the database
            Teilnehmer savedTeilnehmer = teilnehmerRepository.save(user);

            LOG.info("Teilnehmer with id "+ user.getId() + " successfully updated");
            return savedTeilnehmer;
        } else {
            throw new UserNotFoundException("Teilnehmer with id " + user.getId() + " not found.");
        }
    }


    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") long userId) {
        // Before we delete the Teilnehmer, we need to delete every Projekt assignement
        // otherwise we will run into https://github.com/digital-bauhaus/Ferienpass/issues/71
        getRegisteredProjectsOfUser(userId).stream().forEach(projekt -> projekt.deleteTeilnehmerVonAllenProjekten(getUserById(userId)));

        teilnehmerRepository.deleteById(userId);
        LOG.info("User with id "+ userId + " deleted");
    }

    @RequestMapping(path = "/{userId}/projects")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getRegisteredProjectsOfUser(@PathVariable("userId") Long userId) {
        LOG.info("GET called on /user/" + userId + "/projects");

        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer teilnehmer: projekt.getAngemeldeteTeilnehmer()) {
                if(teilnehmer.getId() == userId)
                    resultList.add(projekt);
            }
            for (Teilnehmer teilnehmer: projekt.getStornierteTeilnehmer()) {
                if(teilnehmer.getId() == userId)
                    resultList.add(projekt);
            }
        }
        LOG.info("Returning list with size of " + resultList.size());
        return resultList;
    }

    @RequestMapping(path = "/{userId}/cancelledprojects")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getCancelledProjectsOfUser(@PathVariable("userId") Long userId) {
        LOG.info("GET called on /user/" + userId + "/cancelledprojects");
        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer teilnehmer: projekt.getStornierteTeilnehmer()) {
                if(teilnehmer.getId() == userId)
                    resultList.add(projekt);
            }
        }
        LOG.info("Returning list with size of " + resultList.size());
        return resultList;
    }

}
