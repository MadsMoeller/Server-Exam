package mads.exam.server;

import mads.exam.server.controllers.Parties;
import mads.exam.server.models.Party;
import mads.exam.server.repositories.CandidateRepository;
import mads.exam.server.repositories.PartyRepository;
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
@WebMvcTest(value = Parties.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class PartiesControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PartyRepository parties;

    @MockBean
    private CandidateRepository candidates;

    @Test
    public void givenPartiesWhenGetPartiesThenReturnJsonArray() throws Exception {
        Party partyOne = new Party();
        Party partyTwo = new Party();
        Party partyThree = new Party();

        partyOne.setPartyName("Højre");
        partyTwo.setPartyName("Liberalisterne");
        partyThree.setPartyName("Lokalpolitikerne");

        List<Party> allParties = Arrays.asList(partyOne, partyTwo, partyThree);

        given(parties.findAll()).willReturn(allParties);

        mvc.perform(get("/parties")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].partyName", is("Højre")))
                .andExpect(jsonPath("$[1].partyName", is("Liberalisterne")))
                .andExpect(jsonPath("$[2].partyName", is("Lokalpolitikerne")));

        verify(parties, VerificationModeFactory.times(1)).findAll();
        reset(parties);
    }
}
