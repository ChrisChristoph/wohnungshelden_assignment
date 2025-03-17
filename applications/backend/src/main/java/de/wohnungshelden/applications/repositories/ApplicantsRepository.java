package de.wohnungshelden.applications.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.wohnungshelden.applications.models.Applicant;

public interface ApplicantsRepository extends JpaRepository<Applicant, Long> {

    @Query(value = "select a from Applicant a where a.property_id = :property_id and ((cast(:status as text) is null or cast(a.status as text) = :status) and (cast(:email as text) is null or a.email = :email) and (:number_of_persons = -1 or a.number_of_persons = :number_of_persons) and (cast(:wbs_present as boolean) is null or a.wbs_present = :wbs_present)) order by creation_timestamp desc")
    List<Applicant> findByPropertyIdAndFilter(@Param("property_id") long property_id, @Param("status") String status, @Param("email") String email, @Param("number_of_persons") int number_of_persons, @Param("wbs_present") Boolean wbs_present);
}
