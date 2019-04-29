package de.bauhaus.digital.repository;


import de.bauhaus.digital.domain.Teilnehmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TeilnehmerRepository extends CrudRepository<Teilnehmer, Long> {

    @Query(value="FROM Teilnehmer")
    List<Teilnehmer> findAllUsers();

    @Query(value="FROM Teilnehmer u WHERE u.bezahlt = true")
    List<Teilnehmer> findAllUsersThatHavePayed();

    List<Teilnehmer> findById(@Param("id") int id);

    @Query(value="FROM Teilnehmer u WHERE u.vorname LIKE CONCAT('%',:vorname,'%') or u.vorname LIKE CONCAT('%',:nachname,'%') or u.nachname LIKE CONCAT('%',:vorname,'%') or u.nachname LIKE CONCAT('%',:nachname,'%')")
    List<Teilnehmer> findByName(@Param("vorname") String vorname, @Param("nachname") String nachname);

    List<Teilnehmer> findByNachname(@Param("nachname") String nachname);

    List<Teilnehmer> findByVorname(@Param("vorname") String vorname);

    int findProjekt_idByVornameAndNachname(@Param("vorname") String vorname, @Param("nachname") String nachname);

    List<Teilnehmer> findByRegistrierungsdatum(@Param("registrierungsdatum") LocalDate registrierungsdatum);
}
