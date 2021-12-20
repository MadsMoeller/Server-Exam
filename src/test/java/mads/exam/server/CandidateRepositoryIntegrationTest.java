package mads.exam.server;

import mads.exam.server.models.Candidate;
import mads.exam.server.repositories.CandidateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CandidateRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CandidateRepository candidates;

    @Test
    public void whenFindByNameThenReturnCandidate(){
        Candidate candidate = new Candidate();
        candidate.setName("Bjarne");
        testEntityManager.persist(candidate);
        testEntityManager.flush();

        Candidate candidateFound = candidates.findByName("Bjarne");

        assertEquals(candidateFound.getName(), candidate.getName());
    }
}
