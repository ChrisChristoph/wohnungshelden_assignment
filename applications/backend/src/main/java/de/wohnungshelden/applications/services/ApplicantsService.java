package de.wohnungshelden.applications.services;

import java.util.List;

import de.wohnungshelden.applications.dto.ApplicantFilterDTO;
import de.wohnungshelden.applications.models.Applicant;


public interface ApplicantsService {

    List<Applicant> findAllApplicants();
    List<Applicant> filterProperties(long property_id, ApplicantFilterDTO af);
    Applicant addApplicantManual(Applicant a, long property_id);
    Applicant addApplicantPortal(Applicant a, long property_id);
    void deleteApplicantsByID(long id);
}
