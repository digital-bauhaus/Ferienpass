package de.bauhaus.digital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bauhaus.digital.domain.*;
import de.bauhaus.digital.exception.ProjektNotFoundException;
import de.bauhaus.digital.exception.UserNotFoundException;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import de.bauhaus.digital.transformation.AnmeldungJson;
import de.bauhaus.digital.transformation.AnmeldungToAdmin;
import de.bauhaus.digital.transformation.Project;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);
    @Autowired
    private TeilnehmerRepository teilnehmerRepository;
    @Autowired
    private ProjektRepository projektRepository;


    /*************************************
     * General API
     *********************************/

    @RequestMapping(path = "/hello")
    public @ResponseBody
    String sayHello() {
        LOG.info("GET called on /hello resource");
        return "hello";
    }

    /*******************************************
     * API for user (Teilnehmer) functionality
     ******************************************/

    //Retrieve all users in the data base
    @RequestMapping(path = "/allusers")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Teilnehmer> showAllUsers() {
        LOG.info("GET called on /allusers resource");
        return teilnehmerRepository.findAllUsers();
    }

    // GET USER INFORMATION BY ID
    @GetMapping(path = "/user/{id}")
    public @ResponseBody
    Teilnehmer getUserById(@PathVariable("id") long id) {
        Optional<Teilnehmer> optionalTeilnehmer = teilnehmerRepository.findById(id);
        if(optionalTeilnehmer.isPresent()) {
            return optionalTeilnehmer.get();
        } else {
            throw new UserNotFoundException("Teilnehmer mit der id " + id + " nicht gefunden.");
        }
    }

    //Add a new user (Teilnehmer) based on a user object
    @RequestMapping(path = "/adduser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addNewUser(@RequestBody Teilnehmer user) {

        teilnehmerRepository.save(user);

        LOG.info(user.toString() + " successfully saved into DB");

        return user.getId();
    }

    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Teilnehmer updateUser(@RequestBody Teilnehmer user) {

        return teilnehmerRepository.findById(user.getId()).map(teilnehmer2Update -> {

            //Basic data
            teilnehmer2Update.setVorname(user.getVorname());
            teilnehmer2Update.setNachname(user.getNachname());
            teilnehmer2Update.setGeburtsdatum(user.getGeburtsdatum());
            teilnehmer2Update.setStrasse(user.getStrasse());
            teilnehmer2Update.setPostleitzahl(user.getPostleitzahl());
            teilnehmer2Update.setStadt(user.getStadt());
            teilnehmer2Update.setTelefon(user.getTelefon());
            teilnehmer2Update.setKrankenkasse(user.getKrankenkasse());

            teilnehmer2Update.setArzt(user.getArzt());
            teilnehmer2Update.setNotfallKontakt(user.getNotfallKontakt());
            teilnehmer2Update.setLiegtBehinderungVor(user.isLiegtBehinderungVor());
            teilnehmer2Update.setBehinderung(user.getBehinderung());

            //Diverse
            teilnehmer2Update.setDarfBehandeltWerden(user.isDarfBehandeltWerden());
            teilnehmer2Update.setDarfAlleinNachHause(user.isDarfAlleinNachHause());
            teilnehmer2Update.setDarfReiten(user.isDarfReiten());
            teilnehmer2Update.setDarfSchwimmen(user.isDarfSchwimmen());
            teilnehmer2Update.setSchwimmAbzeichen(user.getSchwimmAbzeichen());
            teilnehmer2Update.setErlaubeMedikamentation(user.isErlaubeMedikamentation());
            teilnehmer2Update.setBezahlt(user.isBezahlt());
            teilnehmer2Update.setEmail((user.getEmail()));

            //Limitations
            teilnehmer2Update.setKrankheiten(user.getKrankheiten());
            teilnehmer2Update.setHitzeempfindlichkeiten(user.getHitzeempfindlichkeiten());
            teilnehmer2Update.setMedikamente(user.getMedikamente());
            teilnehmer2Update.setAllergien(user.getAllergien());
            teilnehmer2Update.setEssenLimitierungen(user.getEssenLimitierungen());

            Teilnehmer savedTeilnehmer = teilnehmerRepository.save(teilnehmer2Update);
            LOG.info("User with id "+ user.getId() + " successfully updated");
            return savedTeilnehmer;

        }).orElseThrow(() -> new UserNotFoundException("User " + user.getId() + " not found"));


    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") long userId) {
        teilnehmerRepository.deleteById(userId);
        LOG.info("User with id "+ userId + " deleted");
    }

    //Assign Project to user
    @RequestMapping(path = "/projekt/{projektId}/user/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Boolean assignProjectToUser(@PathVariable("projektId") Long projektId, @PathVariable("userId") Long userId) {

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

    @RequestMapping(path = "/projekt/{projektId}/user/{userId}", method = RequestMethod.DELETE)
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


    /*******************************************
     * API for projects (Projekte) functionality
     ******************************************/
    //Retrieve all projects
    @RequestMapping(path = "/allprojects", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> showAllProjects() {
        LOG.info("GET called on /allprojects resource");
        return projektRepository.findAllProjects();
    }

    // Retrieve all projects for a user's first and last name
    @RequestMapping(path = "/projectsof")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> showProjectsOfUser(@RequestParam String vorname, @RequestParam String nachname) {
        LOG.info("GET called on /projectsof resource");
        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer t: projekt.getAnmeldungen()) {
                if(t.getVorname().equals(vorname) && t.getNachname().equals(nachname))
                    resultList.add(projekt);
            }
        }
        return resultList;
    }

    @RequestMapping(path = "/user/{userId}/projekte")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> getUsersProjects(@PathVariable("userId") Long userId) {
        LOG.info("GET called on /user/" + userId + "/projekte");
        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer teilnehmer: projekt.getAnmeldungen()) {
                if(teilnehmer.getId() == userId)
                    resultList.add(projekt);
            }
        }
        LOG.info("Returning list with size of " + resultList.size());
        return resultList;
    }

    private LocalDate dateString2LocalDate(@RequestParam String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @RequestMapping(path = "/projekt/{projektId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Boolean setProjectToInactive(@PathVariable("projektId") Long projektId) {

        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            Projekt projekt = maybeProjekt.get();
            projekt.setAktiv(false);
            projektRepository.save(projekt);
            LOG.info(projekt.getName() + " with id " + projekt.getId() + " is set to inactive");
            return true;
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }

    // DELETE PROJECT
    @RequestMapping(path = "/projekt/{projektId}", method = RequestMethod.DELETE)
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


    @RequestMapping(path = "/projekt", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addNewProject(@RequestBody @Valid Projekt projekt) {

        projektRepository.save(projekt);

        LOG.info(projekt.toString() + " successfully saved into DB");

        return projekt.getId();
    }

    @RequestMapping(path = "/projekt", method = RequestMethod.PUT)
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

    @GetMapping(path = "/project/{projektId}")
    public @ResponseBody
    Projekt getProjectById(@PathVariable("projektId") Long projekt_id) {
        Optional<Projekt> maybeProjekt = projektRepository.findById(projekt_id);
        if (maybeProjekt.isPresent()) {
            return maybeProjekt.get();
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projekt_id + " wurde nicht gefunden.");
        }
    }

    @GetMapping(path = "/projekt/{projektId}/users")
    public @ResponseBody
    List<Teilnehmer> getRegisteredUsersByProjectId(@PathVariable("projektId") Long projektId) {
        Optional<Projekt> maybeProjekt = projektRepository.findById(projektId);
        if (maybeProjekt.isPresent()) {
            Projekt projekt = maybeProjekt.get();
            LOG.info("Returning " + projekt.getAnmeldungen().size() + " registered participants for project " + projekt.getName());
            return projekt.getAnmeldungen();
        } else {
            throw new ProjektNotFoundException("Projekt mit der id " + projektId + " wurde nicht gefunden.");
        }
    }


    /*******************************************
     * API for registering from Ferienpass-Anmeldung Microservice
     ******************************************/

    @RequestMapping(path = "/register", method = RequestMethod.POST)
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
                if(projekt.hasProjektFreeSlots()) {
                    projekt.addAnmeldung(neuAngemeldeterTeilnehmer);
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

        LOG.info("Successfully saved new Teilnehmer " + neuAngemeldeterTeilnehmer.toString() + " into Admin-Backend-DB");

        return new ResponseEntity(savedTeilnehmer.getId(), HttpStatus.CREATED);
    }

    private List<Long> addEveryProjektThatHasNoFreeSlots() {
        List<Long> projekteOhneFreiSlots = new ArrayList<>();
        projektRepository.findAllProjects().forEach(projekt -> {
            if (!projekt.hasProjektFreeSlots()) {
                projekteOhneFreiSlots.add(projekt.getId());
            }
        });
        return projekteOhneFreiSlots;
    }

}
