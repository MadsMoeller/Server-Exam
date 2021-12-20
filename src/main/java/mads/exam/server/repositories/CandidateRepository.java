package mads.exam.server.repositories;

import mads.exam.server.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findCandidatesByPartyId(Long partyId);

    Candidate findByName(String name);

}
