package de.bauhaus.digital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bauhaus.digital.domain.*;
import de.bauhaus.digital.exception.ResourceNotFoundException;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import de.bauhaus.digital.transformation.AnmeldungJson;
import de.bauhaus.digital.transformation.AnmeldungToAdmin;
import de.bauhaus.digital.transformation.Project;
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
import java.util.Map;

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
        return teilnehmerRepository.findById(id).orElse(null);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
            teilnehmer2Update.setBehinderung(user.getBehinderung());

            //Diverse
            teilnehmer2Update.setDarfBehandeltWerden(user.isDarfBehandeltWerden());
            teilnehmer2Update.setDarfAlleinNachHause(user.isDarfAlleinNachHause());
            teilnehmer2Update.setDarfReiten(user.isDarfReiten());
            teilnehmer2Update.setDarfSchwimmen(user.isDarfSchwimmen());
            teilnehmer2Update.setSchwimmAbzeichen(user.getSchwimmAbzeichen());
            teilnehmer2Update.setErlaubeMedikamentation(user.isErlaubeMedikamentation());
            teilnehmer2Update.setBezahlt(user.isBezahlt());

            //Limitations
            teilnehmer2Update.setKrankheiten(user.getKrankheiten());
            teilnehmer2Update.setHitzeempfindlichkeiten(user.getHitzeempfindlichkeiten());
            teilnehmer2Update.setMedikamente(user.getMedikamente());
            teilnehmer2Update.setAllergien(user.getAllergien());
            teilnehmer2Update.setEssenLimitierungen(user.getEssenLimitierungen());

            Teilnehmer savedTeilnehmer = teilnehmerRepository.save(teilnehmer2Update);
            LOG.info("User with id "+ user.getId() + " successfully updated");
            return savedTeilnehmer;

        }).orElseThrow(() -> new ResourceNotFoundException("User " + user.getId() + " not found"));


    }


    //Assign Project to user
    @RequestMapping(path = "/assignProject", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Boolean assignProjectToUser(@RequestBody Map<String, Long> ids) {
        Long user_id = ids.get("user");
        Long projekt_id = ids.get("project");
        LOG.info("Assigning project with id: " + projekt_id + " for user id: " + user_id);
        Teilnehmer teilnehmer = teilnehmerRepository.findById(user_id).orElse(null);
        if (teilnehmer == null)
            return false;
        Projekt projekt = projektRepository.findById(projekt_id).orElse(null);
        if (projekt == null)
            return false;

        if (projekt.hasProjektFreeSlots()) {
            return assignUserToProjektWhenNotAlreadyAssigned(teilnehmer, projekt);
        } else {
            LOG.info("Could not assign " + teilnehmer.getNachname() + " to project " + projekt.getName() + " because all free slots are taken.");
            return false;
        }
    }

    private Boolean assignUserToProjektWhenNotAlreadyAssigned(Teilnehmer teilnehmer, Projekt projekt) {
        if(projekt.isTeilnehmerNotAlreadyAsignedToProjekt(teilnehmer)) {
            projekt.addAnmeldung(teilnehmer);
            projektRepository.save(projekt);
            LOG.info("Successfully assigned project " + projekt.getName() + " to user " + teilnehmer.getNachname());
        } else {
            LOG.info("Teilnehmer " + teilnehmer.getNachname() + " already assigned to project " + projekt.getName() + ".");
        }
        return true;
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

        if(oneOrMoreProjekteVoll) {
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
            if(!projekt.hasProjektFreeSlots()) {
                projekteOhneFreiSlots.add(projekt.getId());
            }
        });
        return projekteOhneFreiSlots;
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
        for (Projekt p:projektRepository.findAll()) {
            for (Teilnehmer t: p.getAnmeldungen()) {
                if(t.getVorname().equals(vorname) && t.getNachname().equals(nachname))
                    resultList.add(p);
            }
        }
        return resultList;
    }

    // Retrieve all projects for a user's ID
    @RequestMapping(path = "/projectsofid")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Projekt> showProjectsOfUserById(@RequestParam Long userID) {
        LOG.info("GET called on /projectsofid resource with userID: " + userID);
        List<Projekt> resultList = new ArrayList<>();
        for (Projekt projekt : projektRepository.findAll()) {
            for (Teilnehmer teilnehmer: projekt.getAnmeldungen()) {
                if(teilnehmer.getId() == userID)
                    resultList.add(projekt);
            }
        }
        LOG.info("Returning list with size of " + resultList.size());
        return resultList;
    }

    // CREATE PROJECT
    @RequestMapping(path = "/createproject")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addNewProject(@RequestParam String name, @RequestParam String date,
                       @RequestParam String endDate, @RequestParam int minAge,
                       @RequestParam int maxAge, @RequestParam int price,
                       @RequestParam int slots,
                       @RequestParam int slotsReserved, @RequestParam String traeger, @RequestParam String weblink) {
        Projekt project = new Projekt(name, dateString2LocalDate(date),
                dateString2LocalDate(endDate), minAge, maxAge, price, slots,
                slotsReserved, traeger, weblink);
        projektRepository.save(project);
        LOG.info(project.toString() + "successfully saved into DB");

        return project.getId();
    }

    // UPDATE PROJECT
    @RequestMapping(path = "/updateproject")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Boolean updateProject(@RequestParam Long id, @RequestParam String name,
                          @RequestParam String date,
                          @RequestParam String endDate, @RequestParam int minAge,
                          @RequestParam int maxAge, @RequestParam int price,
                          @RequestParam int slots,
                       @RequestParam int slotsReserved, @RequestParam String traeger, @RequestParam String weblink) {
        Projekt project = projektRepository.findById(id).orElse(null);
        if (project == null) {
            LOG.info("Could not find a project to update with id:" + id);
            return false;
        }
        if (slots < project.getSlotsGesamt() && slots < project.getSlotsGesamt() - project.getSlotsFrei()) {
            LOG.info("Could not update project, because there are already too many slots taken.");
            return  false;
        }
        if(slotsReserved > project.getSlotsReserviert() && slotsReserved - project.getSlotsReserviert() > project.getSlotsFrei()) {
            LOG.info("Could not update project, because you want to reserve more slots than slots are free.");
            return  false;
        }
        project.setName(name);

        String[] dateRaw = date.split(",");
        project.setDatum(LocalDate.of(Integer.valueOf(dateRaw[0]), Integer.valueOf(dateRaw[1]), Integer.valueOf(dateRaw[2])));
        dateRaw = endDate.split(",");
        project.setDatumEnde(LocalDate.of(Integer.valueOf(dateRaw[0]), Integer.valueOf(dateRaw[1]), Integer.valueOf(dateRaw[2])));

        project.setMindestAlter(minAge);
        project.setHoechstAlter(maxAge);
        project.setKosten(price);
        project.setSlotsGesamt(slots);
        project.setSlotsReserviert(slotsReserved);
        project.setTraeger(traeger);
        project.setWebLink(weblink);
        projektRepository.save(project);
        LOG.info(project.toString() + "successfully saved into DB");

        return true;
    }

    private LocalDate dateString2LocalDate(@RequestParam String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // DELETE PROJECT
    @RequestMapping(path = "/deleteproject")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Boolean deleteProject(@RequestParam Long project_id) {

        Projekt p = projektRepository.findById(project_id).orElse(null);
        if (p == null)
            return  false;
        p.setAktiv(false);
        projektRepository.save(p);
        LOG.info(p.toString() + " is set to inactive");

        return true;
    }


    @RequestMapping(path = "/addproject", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Long addNewProject(@RequestBody Projekt projekt) {

        projektRepository.save(projekt);

        LOG.info(projekt.toString() + " successfully saved into DB");

        return projekt.getId();
    }


    // GET PROJECT INFORMATION BY ID
    @GetMapping(path = "/project/{projekt_id}")
    public @ResponseBody
    Projekt getProjectById(@PathVariable("projekt_id") Long projekt_id) {
        return projektRepository.findById(projekt_id).orElse(null);
    }

    //Get all users (Teilnehmer) for a given project by ID
    @GetMapping(path = "/projectRegistrations/{projekt_id}")
    public @ResponseBody
    List<Teilnehmer> getRegisteredUsersByProjectId(@PathVariable("projekt_id") Long projekt_id) {
        Projekt projekt = projektRepository.findById(projekt_id).orElse(null);
        if (projekt == null) {
            LOG.info("Did not found project for id: " + projekt_id);
            return null;
        }
        LOG.info("Returning " + projekt.getAnmeldungen().size() + " registered participants for project " + projekt.getName());
        return projekt.getAnmeldungen();
    }


}
