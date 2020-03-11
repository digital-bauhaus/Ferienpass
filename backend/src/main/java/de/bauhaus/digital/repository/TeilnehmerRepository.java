package de.bauhaus.digital.repository;


import de.bauhaus.digital.domain.Teilnehmer;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TeilnehmerRepository extends CrudRepository<Teilnehmer, Long> {

    @Query(value="FROM Teilnehmer t WHERE t.aktiv=true")
    List<Teilnehmer> findAllActive();

    @Query(value="FROM Teilnehmer t WHERE t.aktiv=true ORDER BY t.nachname, t.vorname ASC")
    List<Teilnehmer> findAllActiveSortedByName();

}
