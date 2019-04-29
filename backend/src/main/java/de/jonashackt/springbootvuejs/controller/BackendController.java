package de.jonashackt.springbootvuejs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.ProjektRepository;
import de.jonashackt.springbootvuejs.repository.TeilnehmerRepository;
import de.jonashackt.springbootvuejs.transformation.AnmeldungJson;
import de.jonashackt.springbootvuejs.transformation.AnmeldungToAdmin;
import de.jonashackt.springbootvuejs.transformation.Project;
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
        return teilnehmerRepository.findOne(id);
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


    //Assign Project to user
    @RequestMapping(path = "/assignProject", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Boolean assignProjectToUser(@RequestBody Map<String, Long> ids) {
        Long user_id = ids.get("user");
        Long projekt_id = ids.get("project");
        LOG.info("Assigning project with id: " + projekt_id + " for user id: " + user_id);
        Teilnehmer teilnehmer = teilnehmerRepository.findOne(user_id);
        if (teilnehmer == null)
            return false;
        Projekt projekt = projektRepository.findOne(projekt_id);
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
                Projekt projekt = projektRepository.findOne(project.getId().longValue());
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
    Long addNewProject(@RequestParam String name, @RequestParam String date, @RequestParam String endDate, @RequestParam int age, @RequestParam int price, @RequestParam int slots,
                       @RequestParam int slotsReserved, @RequestParam String traeger, @RequestParam String weblink) {
        Projekt project = new Projekt(name, dateString2LocalDate(date), dateString2LocalDate(endDate), age, price, slots, slotsReserved, traeger, weblink);
        projektRepository.save(project);
        LOG.info(project.toString() + "successfully saved into DB");

        return project.getId();
    }

    // UPDATE PROJECT
    @RequestMapping(path = "/updateproject")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Boolean updateProject(@RequestParam Long id, @RequestParam String name, @RequestParam String date, @RequestParam String endDate, @RequestParam int age, @RequestParam int price, @RequestParam int slots,
                       @RequestParam int slotsReserved, @RequestParam String traeger, @RequestParam String weblink) {
        Projekt project = projektRepository.findOne(id);
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

        project.setAlterLimitierung(age);
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

        Projekt p = projektRepository.findOne(project_id);
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
        return projektRepository.findOne(projekt_id);
    }

    //Get all users (Teilnehmer) for a given project by ID
    @GetMapping(path = "/projectRegistrations/{projekt_id}")
    public @ResponseBody
    List<Teilnehmer> getRegisteredUsersByProjectId(@PathVariable("projekt_id") Long projekt_id) {
        Projekt projekt = projektRepository.findOne(projekt_id);
        if (projekt == null) {
            LOG.info("Did not found project for id: " + projekt_id);
            return null;
        }
        LOG.info("Returning " + projekt.getAnmeldungen().size() + " registered participants for project " + projekt.getName());
        return projekt.getAnmeldungen();
    }

    //UPDATE USER
    @RequestMapping(path = "/updateUser")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Teilnehmer updateUser(@RequestParam Long userId, @RequestParam String vorname, @RequestParam String nachname,
                          @RequestParam String geburtsdatum, @RequestParam String strasse, @RequestParam String plz,
                          @RequestParam String stadt, @RequestParam String tel, @RequestParam String krankenkasse,
                          @RequestParam String kontaktName, @RequestParam String kontaktAdresse,
                          @RequestParam String kontaktTel, @RequestParam String arztName, @RequestParam String arztAdresse,
                          @RequestParam String arztTel, @RequestParam Boolean erlaubeMedikamentation, @RequestParam Boolean darfSchwimmen,
                          @RequestParam Boolean darfReiten, @RequestParam Boolean darfAlleinNachHause, @RequestParam String schwimmAbzeichen,
                          @RequestParam Boolean bezahlt, @RequestParam Boolean darfBehandeltWerden, @RequestParam Boolean liegtBehinderungVor,
                          @RequestParam Boolean behinderungG, @RequestParam Boolean behinderungH, @RequestParam Boolean behinderungAG,
                          @RequestParam Boolean behinderungB1, @RequestParam Boolean behinderungG1, @RequestParam Boolean behinderungB,
                          @RequestParam Boolean behinderungTBL, @RequestParam Boolean rollstuhl,
                          @RequestParam String behinderungHilfsmittel, @RequestParam Boolean wertMarke, @RequestParam Boolean begleitungNotwending,
                          @RequestParam Boolean begleitPflege, @RequestParam Boolean begleitMedVor, @RequestParam Boolean begleitMobilität,
                          @RequestParam Boolean begleitOrientierung, @RequestParam Boolean begleitSozial, @RequestParam String eingeschränkteSinne,
                          @RequestParam String hinweiseZumUmgang, @RequestParam Boolean behinderungUnterstützung,
                          @RequestParam String untersützungKontakt, @RequestParam Boolean kostenÜbernahme,
                          @RequestParam String krankheiten, @RequestParam String allergien, @RequestParam String essenLimitierungen,
                          @RequestParam String medikamente, @RequestParam String hitzeempfindlichkeiten) {
        Teilnehmer teilnehmer = teilnehmerRepository.findOne(userId);
        if (teilnehmer == null) {
            LOG.info("Could not update user with id: " + userId + " because there is no entry in the database.");
            return null;
        }
        //Handicap
        teilnehmer.setLiegtBehinderungVor(liegtBehinderungVor);
        if (liegtBehinderungVor) {
            Behinderung b = teilnehmer.getBehinderung();
            b.setMerkzeichen_BeeintraechtigungImStrassenverkehr_G(behinderungG);
            b.setMerkzeichen_Taubblind_TBL(behinderungTBL);
            b.setMerkzeichen_AussergewoehnlicheGehbehinderung_aG(behinderungAG);
            b.setMerkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(behinderungB);
            b.setMerkzeichen_Blind_Bl(behinderungB1);
            b.setMerkzeichen_Hilflosigkeit_H(behinderungH);
            b.setRollstuhlNutzungNotwendig(rollstuhl);
            b.setMerkzeichen_Gehoerlos_Gl(behinderungG1);
            b.setHinweiseZumUmgangMitDemKind(hinweiseZumUmgang);
            b.setBeantragungKostenuebernahmeBegleitpersonNotwendig(kostenÜbernahme);
            b.setBegleitpersonMedizinischeVersorgung(begleitMedVor);
            b.setBegleitpersonMobilitaet(begleitMobilität);
            b.setBegleitpersonSozialeBegleitung(begleitSozial);
            b.setBegleitpersonOrientierung(begleitOrientierung);
            b.setBegleitpersonPflege(begleitPflege);
            b.setBegleitungNotwendig(begleitungNotwending);
            b.setEingeschraenkteSinne(eingeschränkteSinne);
            b.setUnterstuetzungSucheBegleitpersonNotwendig(behinderungUnterstützung);
            b.setGewohnterBegleitpersonenDienstleister(untersützungKontakt);
            b.setWertmarkeVorhanden(wertMarke);
            b.setWeitereHilfsmittel(behinderungHilfsmittel);
        }

        //Basic data
        teilnehmer.setVorname(vorname);
        teilnehmer.setNachname(nachname);
        String[] dateRaw = geburtsdatum.split(",");
        teilnehmer.setGeburtsdatum(LocalDate.of(Integer.valueOf(dateRaw[0]), Integer.valueOf(dateRaw[1]), Integer.valueOf(dateRaw[2])));
        teilnehmer.setStrasse(strasse);
        teilnehmer.setPostleitzahl(plz);
        teilnehmer.setStadt(stadt);
        teilnehmer.setTelefon(tel);
        teilnehmer.setKrankenkasse(krankenkasse);

        //Emergency contact
        Kontakt kontakt = teilnehmer.getNotfallKontakt();
        kontakt.setAddress(kontaktAdresse);
        kontakt.setName(kontaktName);
        kontakt.setTelephone(kontaktTel);
        teilnehmer.setNotfallKontakt(kontakt);

        //Medical contact
        Arzt arzt = teilnehmer.getArzt();
        arzt.setAddress(arztAdresse);
        arzt.setName(arztName);
        arzt.setTelephone(arztTel);
        teilnehmer.setArzt(arzt);

        //Diverse
        teilnehmer.setDarfBehandeltWerden(darfBehandeltWerden);
        teilnehmer.setDarfAlleinNachHause(darfAlleinNachHause);
        teilnehmer.setDarfReiten(darfReiten);
        teilnehmer.setDarfSchwimmen(darfSchwimmen);
        teilnehmer.setSchwimmAbzeichen(schwimmAbzeichen);
        teilnehmer.setErlaubeMedikamentation(erlaubeMedikamentation);
        teilnehmer.setBezahlt(bezahlt);

        //Limitations
        teilnehmer.setKrankheiten(krankheiten);
        teilnehmer.setHitzeempfindlichkeiten(hitzeempfindlichkeiten);
        teilnehmer.setMedikamente(medikamente);
        teilnehmer.setAllergien(allergien);
        teilnehmer.setEssenLimitierungen(essenLimitierungen);



        teilnehmerRepository.save(teilnehmer);
        LOG.info("Successfully updated Teilnehmer " + nachname);
        return teilnehmer;
    }


}
