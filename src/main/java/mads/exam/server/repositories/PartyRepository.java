package mads.exam.server.repositories;

import mads.exam.server.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Party findByPartyName(String partyName);
}
