package de.wohnungshelden.applications.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.wohnungshelden.applications.filters.ApplicantsFilter;
import de.wohnungshelden.applications.models.Applicants;
import de.wohnungshelden.applications.services.ApplicantsServiceImpl;
import de.wohnungshelden.applications.validators.ApplicantsManual;
import de.wohnungshelden.applications.validators.ApplicantsPortal;

/**
 * <h1>Applicants Controller</h1>
 * The Controller who handles the endpoints for Applicants. The Controller who
 * gets the data from a service. The Controller who adds data with the help of a
 * service. The Controller who deletes them.
 *
 * @author Christoph Schöll
 * @version 1.0
 * @since 16.03.2025
 */
@RestController
@RequestMapping("/api/applicants")
public class ApplicantsController {

    @Autowired
    ApplicantsServiceImpl applicantsServiceImpl;

    /**
     * This method returns a List of all Applicants in the database.
     *
     * @return List of Applicants
     */
    @RequestMapping(path = "/findAllApplicants", produces = "application/json")
    public List<Applicants> findAllApplicants() {
        return applicantsServiceImpl.findAllApplicants();
    }

    /**
     * This method filters Properties by give criteria. These criteria are added
     * via JSON in the RequestBody
     *
     * @param property_id unique ID for the property
     * @param applicants_filter Object with filter criteria
     * @return List of Applicants
     */
    @RequestMapping(value = "/filter/{property_id}", method = RequestMethod.POST, consumes = "application/json")
    public List<Applicants> filterProperties(@PathVariable long property_id, @RequestBody ApplicantsFilter applicants_filter) {
        return applicantsServiceImpl.filterProperties(property_id, applicants_filter);
    }

    /**
     * This methode adds an Applicant via manual insert to a specific Property.
     *
     * @param property_id unique ID for the property
     * @param applicants the data for the applicant
     * @param result the binding result vor the validation
     * @param model also needed for the validation
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/add/manual/{property_id}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> addApplicants(@PathVariable long property_id, @Validated(ApplicantsManual.class) @RequestBody Applicants applicants, BindingResult result, ModelMap model)
            throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethod("addApplicants", long.class, Applicants.class, BindingResult.class, ModelMap.class), 0),
                    result);
        }
        applicantsServiceImpl.addApplicantsManual(applicants, property_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri());
        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    /**
     * This methode adds an Applicant via portal insert to a specific Property.
     *
     * @param property_id unique ID for the property
     * @param applicants the data for the applicant
     * @param result the binding result vor the validation
     * @param model also needed for the validation
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/add/portal/{property_id}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> addApplicantsPortal(@PathVariable long property_id, @Validated(ApplicantsPortal.class) @RequestBody Applicants applicants, BindingResult result, ModelMap model)
            throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethod("addApplicantsPortal", long.class, Applicants.class, BindingResult.class, ModelMap.class), 0),
                    result);
        }
        applicantsServiceImpl.addApplicantsPortal(applicants, property_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri());
        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    /**
     * This method deletes a specific Applicant by its ID
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void deleteApplicantsByID(@PathVariable long id) {
        applicantsServiceImpl.deleteApplicantsByID(id);
    }
}
