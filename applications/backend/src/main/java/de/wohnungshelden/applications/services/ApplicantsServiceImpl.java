package de.wohnungshelden.applications.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wohnungshelden.applications.filters.ApplicantsFilter;
import de.wohnungshelden.applications.models.Applicants;
import de.wohnungshelden.applications.models.Applicants.Source_Enum;
import de.wohnungshelden.applications.repositories.ApplicantsRepository;

/**
 * <h1>Applicants Service Implementation</h1>
 * This Class implements the Service interface for the ApplicantsService. It is
 * responsible for the actual data handling und the business side.
 *
 * @author Christoph Schöll
 * @version 1.0
 * @since 16.03.2025
 */
@Service
public class ApplicantsServiceImpl implements ApplicantsService {

    @Autowired
    private ApplicantsRepository applicantsRepositoy;

    @Override
    public List<Applicants> findAllApplicants() {
        return applicantsRepositoy.findAll();
    }

    @Override
    public List<Applicants> filterProperties(long property_id, ApplicantsFilter af) {
        return applicantsRepositoy.findByPropertyIdAndFilter(property_id, af.getStatus(), af.getEmail(), af.getNumber_of_persons(), af.getWbs_present());
    }

    @Override
    public void addApplicantsManual(Applicants a, long property_id) {
        a.setInitialCreation(property_id);
        // The manual creation will set the CreationSource to MANUAL (see data model)
        a.setCreation_source(Source_Enum.MANUAL);

        applicantsRepositoy.save(a);
    }

    @Override
    public void addApplicantsPortal(Applicants a, long property_id) {
        a.setInitialCreation(property_id);
        // The automatic creation will set the CreationSource to PORTAL
        a.setCreation_source(Source_Enum.PORTAL);

        applicantsRepositoy.save(a);
    }

    @Override
    public void deleteApplicantsByID(long id) {
        applicantsRepositoy.deleteById(id);
    }
}
