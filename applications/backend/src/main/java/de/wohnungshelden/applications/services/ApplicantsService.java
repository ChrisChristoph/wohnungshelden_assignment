package de.wohnungshelden.applications.services;

import java.util.List;

import de.wohnungshelden.applications.filters.ApplicantsFilter;
import de.wohnungshelden.applications.models.Applicants;


public interface ApplicantsService {

    List<Applicants> findAllApplicants();
    List<Applicants> filterProperties(long property_id, ApplicantsFilter af);
    void addApplicantsManual(Applicants a, long property_id);
    void addApplicantsPortal(Applicants a, long property_id);
    void deleteApplicantsByID(long id);
}
