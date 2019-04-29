package de.jonashackt.springbootvuejs.repository;


import de.jonashackt.springbootvuejs.domain.Projekt;
import de.jonashackt.springbootvuejs.domain.Teilnehmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjektRepository extends CrudRepository<Projekt, Long> {

    List<Projekt> findByName(@Param("name") String name);

    //List<Projekt> findByProjekts(@Param("projekt_id") long projekt_id);
    //List<Projekt> findByprojekt_id(@Param("projekt_id") long projekt_id);

    @Query(value="FROM Projekt p WHERE p.aktiv=true")
    List<Projekt> findAllProjects();

    @Query(value="SELECT p.anmeldungen FROM Projekt p WHERE p.name in :name")
    List<Teilnehmer> findUsersByName();



}
