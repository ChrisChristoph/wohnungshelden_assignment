package de.wohnungshelden.applications.dto;

import org.springframework.lang.NonNull;

public class ApplicantFilterDTO {
    @NonNull
    private long property_id;

    private String status;

    private String email;

    private int number_of_persons = -1;

    private Boolean wbs_present;

    public long getProperty_id() {
        return property_id;
    }

    public void setProperty_id(long property_id) {
        this.property_id = property_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber_of_persons() {
        return number_of_persons;
    }

    public void setNumber_of_persons(int number_of_persons) {
        this.number_of_persons = number_of_persons;
    }

    public Boolean getWbs_present() {
        return wbs_present;
    }

    public void setWbs_present(Boolean wbs_present) {
        this.wbs_present = wbs_present;
    }
}
