package mads.exam.server.controllers;

import mads.exam.server.models.Candidate;
import mads.exam.server.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Candidates {

    @Autowired
    CandidateRepository candidates;

    @GetMapping("/candidates")
    public Iterable<Candidate> findAllCandidates(){
        return candidates.findAll();
    }

    @GetMapping("/candidates/{id}")
    public Candidate findCandidateById(@PathVariable Long id){
        return candidates.findById(id).get();
    }

    @GetMapping("/candidates/parties/{partyId}")
    public Iterable<Candidate> findCandidatesByPartyId(@PathVariable Long partyId){
        return candidates.findCandidatesByPartyId(partyId);
    }

    @PostMapping("/candidates")
    public Candidate addNewCandidate(@RequestBody Candidate newCandidate){
        newCandidate.setId(null);
        return candidates.save(newCandidate);
    }

    @PutMapping("/candidates/{id}")
    public String putCandidate(@PathVariable Long id, @RequestBody Candidate candidateToUpdateWith){
        if(candidates.existsById(id)){
            candidateToUpdateWith.setId(id);
            candidates.save(candidateToUpdateWith);
            return "Candidate updated";
        }else{
            return "Candidate not found";
        }
    }

    @PatchMapping("/candidates/{id}")
    public String patchCandidate(@PathVariable Long id, @RequestBody Candidate candidateToUpdateWith){
        return candidates.findById(id).map(foundCandidate -> {
            if(candidateToUpdateWith.getName() != null){
                foundCandidate.setName(candidateToUpdateWith.getName());
            }
            if(candidateToUpdateWith.getPersonalVotes() != null){
                foundCandidate.setPersonalVotes(candidateToUpdateWith.getPersonalVotes());
            }
            if(candidateToUpdateWith.getParty() != null){
                foundCandidate.setParty(candidateToUpdateWith.getParty());
            }
            candidates.save(foundCandidate);
            return "Candidate updated";
        }).orElse("Candidate not found");
    }

    @DeleteMapping("/candidates/{id}")
    public void deleteCandidateById(@PathVariable Long id){
        candidates.deleteById(id);
    }
}
