package de.wohnungshelden.applications.models;

import java.sql.Timestamp;
import java.util.Date;

import de.wohnungshelden.applications.validators.ApplicantsManual;
import de.wohnungshelden.applications.validators.ApplicantsPortal;
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

@Entity
@Table(name = "applicants")
public class Applicants {

    @Id
    @GeneratedValue(generator = "applicants_seq")
    @SequenceGenerator(name = "applicants_seq", sequenceName = "applicants_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private long id;

    @Email(groups = {ApplicantsManual.class, ApplicantsPortal.class}, message = "Email should be valid")
    @NotBlank(groups = {ApplicantsManual.class, ApplicantsPortal.class}, message = "Email is required")
    private String email;

    public static enum Salutation_Enum {
        MR, MRS
    }
    @Enumerated(EnumType.STRING)
    private Salutation_Enum salutation;

    @NotBlank(groups = ApplicantsManual.class, message = "First Name is required")
    private String first_name;

    @NotBlank(groups = {ApplicantsManual.class, ApplicantsPortal.class}, message = "Last Name is required")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Salutation_Enum getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation_Enum salutation) {
        this.salutation = salutation;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getNumber_of_persons() {
        return number_of_persons;
    }

    public void setNumber_of_persons(int number_of_persons) {
        this.number_of_persons = number_of_persons;
    }

    public Boolean isWbs_present() {
        return wbs_present;
    }

    public void setWbs_present(Boolean wbs_present) {
        this.wbs_present = wbs_present;
    }

    public Date getEarliest_move_in_date() {
        return earliest_move_in_date;
    }

    public void setEarliest_move_in_date(Date earliest_move_in_date) {
        this.earliest_move_in_date = earliest_move_in_date;
    }

    public Boolean isPets() {
        return pets;
    }

    public void setPets(Boolean pets) {
        this.pets = pets;
    }

    public Status_Enum getStatus() {
        return status;
    }

    public void setStatus(Status_Enum status) {
        this.status = status;
    }

    public String getApplicant_comment() {
        return applicant_comment;
    }

    public void setApplicant_comment(String applicant_comment) {
        this.applicant_comment = applicant_comment;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public Timestamp getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(Timestamp creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public long getProperty_id() {
        return property_id;
    }

    public void setProperty_id(long property_id) {
        this.property_id = property_id;
    }

    public Source_Enum getCreation_source() {
        return creation_source;
    }

    public void setCreation_source(Source_Enum creation_source) {
        this.creation_source = creation_source;
    }

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
