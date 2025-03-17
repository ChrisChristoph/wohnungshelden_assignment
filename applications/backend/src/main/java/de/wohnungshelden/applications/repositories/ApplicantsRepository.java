package de.wohnungshelden.applications.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.wohnungshelden.applications.models.Applicants;

public interface ApplicantsRepository extends JpaRepository<Applicants, Long> {

    @Query(value = "SELECT * FROM wohnungshelden.applicants a WHERE a.property_id = :property_id AND ((cast(:status as text) IS NULL OR a.status = :status) AND (cast(:email as text) IS NULL OR a.email = :email) AND (:number_of_persons = -1 OR a.number_of_persons = :number_of_persons) AND (cast(:wbs_present as boolean) IS NULL OR a.wbs_present = :wbs_present)) ORDER BY a.creation_timestamp DESC", nativeQuery = true)
    List<Applicants> findByPropertyIdAndFilter(@Param("property_id") long property_id, @Param("status") String status, @Param("email") String email, @Param("number_of_persons") int number_of_persons, @Param("wbs_present") Boolean wbs_present);
}
