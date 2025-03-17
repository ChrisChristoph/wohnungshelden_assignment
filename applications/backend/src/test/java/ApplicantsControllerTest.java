
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.wohnungshelden.applications.Application;
import de.wohnungshelden.applications.controllers.ApplicantsController;
import de.wohnungshelden.applications.models.Applicant;
import de.wohnungshelden.applications.services.ApplicantsServiceImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
@WebMvcTest(ApplicantsController.class)
public class ApplicantsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ApplicantsServiceImpl applicantsServiceImpl;

    @Test
    public void findAllApplicants() throws Exception {

        Applicant testApplicants = new Applicant();
        testApplicants.setFirst_name("first_name");

        List<Applicant> allApplicants = Arrays.asList(testApplicants);

        given(applicantsServiceImpl.findAllApplicants()).willReturn(allApplicants);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/applicants/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].first_name", is(testApplicants.getFirst_name())));
    }

    @Test
    public void addApplicantsManualCreated() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/manual/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"first_name\":\"test_name\",\"last_name\":\"test_last_name\",\"email\":\"email@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void addApplicantsManualFailerMissingFirstName() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/manual/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"last_name\":\"test_last_name\",\"email\":\"email@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.first_name", is("First Name is required")))
                .andReturn();
    }

    @Test
    public void addApplicantsManualFailerMissingLastName() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/manual/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"first_name\":\"test_name\",\"email\":\"email@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.last_name", is("Last Name is required")))
                .andReturn();
    }

    @Test
    public void addApplicantsManualFailerMissingEmail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/manual/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"first_name\":\"test_name\",\"last_name\":\"test_last_name\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("Email is required")))
                .andReturn();
    }

    @Test
    public void addApplicantsManualFailerWrongEmailFormat() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/manual/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"first_name\":\"test_name\",\"last_name\":\"test_last_name\",\"email\":\"emailemail.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("Email should be valid")))
                .andReturn();
    }

    @Test
    public void addApplicantsPortalCreated() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/portal/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"last_name\":\"test_last_name\",\"email\":\"email@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void addApplicantsPortalFailerMissingLastName() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/portal/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"email@email.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.last_name", is("Last Name is required")))
                .andReturn();
    }

    @Test
    public void addApplicantsPortalFailerMissingEmail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/portal/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"last_name\":\"test_last_name\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("Email is required")))
                .andReturn();
    }

    @Test
    public void addApplicantsPortalFailerWrongEmailFormat() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/applicants/portal/{property_id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"last_name\":\"test_last_name\",\"email\":\"emailemail.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("Email should be valid")))
                .andReturn();
    }
}
