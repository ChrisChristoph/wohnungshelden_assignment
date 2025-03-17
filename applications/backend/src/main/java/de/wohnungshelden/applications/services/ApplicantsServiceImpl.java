package de.wohnungshelden.applications.services;

import java.util.List;

import org.springframework.stereotype.Service;

import de.wohnungshelden.applications.dto.ApplicantFilterDTO;
import de.wohnungshelden.applications.models.Applicant;
import de.wohnungshelden.applications.models.Applicant.Source_Enum;
import de.wohnungshelden.applications.repositories.ApplicantsRepository;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class ApplicantsServiceImpl implements ApplicantsService {

    private final ApplicantsRepository applicantsRepositoy;

    @Override
    public List<Applicant> findAllApplicants() {
        return applicantsRepositoy.findAll();
    }

    @Override
    public List<Applicant> filterProperties(long property_id, ApplicantFilterDTO af) {
        // umschreiben
        return applicantsRepositoy.findByPropertyIdAndFilter(property_id, af.getStatus(), af.getEmail(), af.getNumber_of_persons(), af.getWbs_present());
    }

    @Override
    public Applicant addApplicantManual(Applicant a, long property_id) {
        a.setInitialCreation(property_id);
        // The manual creation will set the CreationSource to MANUAL (see data model)
        a.setCreation_source(Source_Enum.MANUAL);

        return applicantsRepositoy.save(a);
    }

    @Override
    public Applicant addApplicantPortal(Applicant a, long property_id) {
        a.setInitialCreation(property_id);
        // The automatic creation will set the CreationSource to PORTAL
        a.setCreation_source(Source_Enum.PORTAL);

        return applicantsRepositoy.save(a);
    }

    @Override
    public void deleteApplicantsByID(long id) {
        applicantsRepositoy.deleteById(id);
    }
}
