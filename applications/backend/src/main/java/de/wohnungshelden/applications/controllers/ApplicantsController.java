package de.wohnungshelden.applications.controllers;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.wohnungshelden.applications.dto.ApplicantFilterDTO;
import de.wohnungshelden.applications.models.Applicant;
import de.wohnungshelden.applications.services.ApplicantsService;
import de.wohnungshelden.applications.validators.ApplicantManual;
import de.wohnungshelden.applications.validators.ApplicantPortal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@RequestMapping("/api/applicants")
@RequiredArgsConstructor
public class ApplicantsController {

    private final ApplicantsService applicantsService;

    /**
     * This method returns a List of all Applicants in the database.
     *
     * @return List of Applicants
     */
    @GetMapping(path = "/", produces = "application/json")
    public List<Applicant> findAllApplicants() {
        return applicantsService.findAllApplicants();
    }

    /**
     * This method filters Properties by give criteria. These criteria are added
     * via JSON in the RequestBody
     *
     * @param property_id unique ID for the property
     * @param applicants_filter Object with filter criteria
     * @return List of Applicants
     */
    @GetMapping(value = "/{property_id}", consumes = "application/json")
    public List<Applicant> filterApplicantsByProperty(@PathVariable long property_id, @RequestBody ApplicantFilterDTO applicants_filter) {
        return applicantsService.filterProperties(property_id, applicants_filter);
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
    @RequestMapping(value = "/manual/{property_id}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Applicant> addApplicantManual(@PathVariable long property_id, @Validated(ApplicantManual.class) @RequestBody Applicant applicant, BindingResult result, ModelMap model)
            throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException {

        if (result.hasErrors()) {
            result.getAllErrors().forEach((e)-> {log.error(e.getDefaultMessage());});
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethod("addApplicantManual", long.class, Applicant.class, BindingResult.class, ModelMap.class), 0),
                    result);
        }
        
        log.info("Add manual Applicant: ");
        log.info("Property: " + property_id);
        log.info(String.valueOf(applicant));
        return new ResponseEntity<>(applicantsService.addApplicantManual(applicant, property_id), HttpStatus.CREATED);
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
    @RequestMapping(value = "/portal/{property_id}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Applicant> addApplicantPortal(@PathVariable long property_id, @Validated(ApplicantPortal.class) @RequestBody Applicant applicant, BindingResult result, ModelMap model)
            throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException {

        if (result.hasErrors()) {
            result.getAllErrors().forEach((e)-> {log.error(e.getDefaultMessage());});
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethod("addApplicantPortal", long.class, Applicant.class, BindingResult.class, ModelMap.class), 0),
                    result);
        }

        log.info("Add portal Applicant: ");
        log.info("Property: " + property_id);
        log.info(String.valueOf(applicant));
        return  new ResponseEntity<>(applicantsService.addApplicantPortal(applicant, property_id), HttpStatus.CREATED);
    }

    /**
     * This method deletes a specific Applicant by its ID
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteApplicantsByID(@PathVariable long id) {
        applicantsService.deleteApplicantsByID(id);
    }
}
