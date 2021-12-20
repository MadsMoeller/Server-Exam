package mads.exam.server;

import mads.exam.server.controllers.Candidates;
import mads.exam.server.models.Candidate;
import mads.exam.server.repositories.CandidateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = Candidates.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class CandidatesControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CandidateRepository candidates;

    @Test
    public void givenCandidatesWhenGetCandidatesThenReturnJsonArray() throws Exception {
        Candidate candidateOne = new Candidate();
        Candidate candidateTwo = new Candidate();
        Candidate candidateThree = new Candidate();

        candidateOne.setName("Bjarne");
        candidateTwo.setName("Peter");
        candidateThree.setName("Isabella");

        List<Candidate> allCandidates = Arrays.asList(candidateOne, candidateTwo, candidateThree);

        given(candidates.findAll()).willReturn(allCandidates);

        mvc.perform(get("/candidates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Bjarne")))
                .andExpect(jsonPath("$[1].name", is("Peter")))
                .andExpect(jsonPath("$[2].name", is("Isabella")));

        verify(candidates, VerificationModeFactory.times(1)).findAll();
        reset(candidates);
    }
}
