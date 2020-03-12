package de.bauhaus.digital.repository;


import de.bauhaus.digital.domain.Projekt;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProjektRepository extends CrudRepository<Projekt, Long> {

    @Query(value="FROM Projekt p WHERE p.aktiv=true")
    List<Projekt> findAllActive();

    @Query(value="FROM Projekt p WHERE p.aktiv=true ORDER BY p.datumBeginn, p.datumEnde ASC")
    List<Projekt> findAllActiveSortedByDatum();

}
