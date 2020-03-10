package de.bauhaus.digital.repository;


import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjektRepository extends CrudRepository<Projekt, Long> {

    List<Projekt> findByName(@Param("name") String name);

    //List<Projekt> findByProjekts(@Param("projekt_id") long projekt_id);
    //List<Projekt> findByprojekt_id(@Param("projekt_id") long projekt_id);

    @Query(value="FROM Projekt p WHERE p.aktiv=true ORDER BY p.datumBeginn, p.datumEnde ASC")
    List<Projekt> findAllProjects();

    @Query(value="SELECT p.angemeldeteTeilnehmer FROM Projekt p WHERE p.name in :name")
    List<Teilnehmer> findUsersByName();



}
