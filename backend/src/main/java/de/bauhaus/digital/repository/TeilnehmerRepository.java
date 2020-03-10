package de.bauhaus.digital.repository;


import de.bauhaus.digital.domain.Teilnehmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TeilnehmerRepository extends CrudRepository<Teilnehmer, Long> {

    @Query(value="FROM Teilnehmer t WHERE t.aktiv=true")
    List<Teilnehmer> findAllActive();

    @Query(value="FROM Teilnehmer t WHERE t.aktiv=true ORDER BY t.nachname, t.vorname ASC")
    List<Teilnehmer> findAllActiveSortedByName();

}
