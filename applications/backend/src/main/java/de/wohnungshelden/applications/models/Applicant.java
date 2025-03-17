package de.wohnungshelden.applications.models;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import de.wohnungshelden.applications.validators.ApplicantPortal;
import de.wohnungshelden.applications.validators.ApplicantManual;

@Entity
@Table(name = "applicants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {

    @Id
    @GeneratedValue(generator = "applicants_seq")
    @SequenceGenerator(name = "applicants_seq", sequenceName = "applicants_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @Email(groups = {ApplicantManual.class, ApplicantPortal.class}, message = "Email should be valid")
    @NotBlank(groups = {ApplicantManual.class, ApplicantPortal.class}, message = "Email is required")
    private String email;

    public static enum Salutation_Enum {
        MR, MRS
    }
    @Enumerated(EnumType.STRING)
    private Salutation_Enum salutation;

    @NotBlank(groups = ApplicantManual.class, message = "First Name is required")
    private String first_name;

    @NotBlank(groups = {ApplicantManual.class, ApplicantPortal.class}, message = "Last Name is required")
    private String last_name;

    private int number_of_persons;

    private Boolean wbs_present;

    private Date earliest_move_in_date;

    private Boolean pets;

    public static enum Status_Enum {
        CREATED, INVITED, DECLINED
    }
    @Enumerated(EnumType.STRING)
    private Status_Enum status;

    private String applicant_comment;

    private String user_comment;

    private Timestamp creation_timestamp;

    private long property_id;

    public static enum Source_Enum {
        MANUAL, PORTAL
    }
    @Enumerated(EnumType.STRING)
    private Source_Enum creation_source;


    public void setInitialCreation(long property_id) {
        this.property_id = property_id;
        // When an application is created the status is set to CREATED
        this.status = Status_Enum.CREATED;
        // The creationTimestamp must be set as the current timestamp
        this.creation_timestamp = getTimestamp();
    }

    private Timestamp getTimestamp() {
        Date now = new Date();
        Timestamp creationT = new Timestamp(now.getTime());
        return creationT;
    }
}
