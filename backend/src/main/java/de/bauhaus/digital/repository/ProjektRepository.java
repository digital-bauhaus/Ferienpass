package de.bauhaus.digital.repository;


import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjektRepository extends CrudRepository<Projekt, Long> {

    @Query(value="FROM Projekt p WHERE p.aktiv=true")
    List<Projekt> findAllActive();

    @Query(value="FROM Projekt p WHERE p.aktiv=true ORDER BY p.datumBeginn, p.datumEnde ASC")
    List<Projekt> findAllActiveSortedByDatum();

}
