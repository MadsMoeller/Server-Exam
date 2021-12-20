package mads.exam.server;

import mads.exam.server.models.Party;
import mads.exam.server.repositories.PartyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PartyRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PartyRepository parties;

    @Test
    public void whenFindByPartyNameThenReturnParty(){
        Party party = new Party();
        party.setPartyName("Højre");
        testEntityManager.persist(party);
        testEntityManager.flush();

        Party partyFound = parties.findByPartyName("Højre");

        assertEquals(partyFound.getPartyName(), party.getPartyName());
    }
}
