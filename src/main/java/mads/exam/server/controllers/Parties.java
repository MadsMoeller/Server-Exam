package mads.exam.server.controllers;

import mads.exam.server.models.Party;
import mads.exam.server.repositories.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Parties {

    @Autowired
    PartyRepository parties;

    @GetMapping("/parties")
    public Iterable<Party> findAllParties(){
        return parties.findAll();
    }

    @GetMapping("/parties/{id}")
    public Party findPartyById(@PathVariable Long id){
        return parties.findById(id).get();
    }

    @PostMapping("/parties")
    public Party addNewParty(@RequestBody Party newParty){
        newParty.setId(null);
        return parties.save(newParty);
    }

    @PutMapping("/parties/{id}")
    public String putParty(@PathVariable Long id, @RequestBody Party partyToUpdateWith){
        if(parties.existsById(id)){
            partyToUpdateWith.setId(id);
            parties.save(partyToUpdateWith);
            return "Party updated";
        }else{
            return "Party not found";
        }
    }

    @PatchMapping("/parties/{id}")
    public String patchParty(@PathVariable Long id, @RequestBody Party partyToUpdateWith){
        return parties.findById(id).map(foundParty -> {
            if(partyToUpdateWith.getPartyName() != null){
                foundParty.setPartyName(partyToUpdateWith.getPartyName());
            }
            if(partyToUpdateWith.getPartyLetter() != null){
                foundParty.setPartyLetter(partyToUpdateWith.getPartyLetter());
            }
            if(partyToUpdateWith.getPartyVotes() != null){
                foundParty.setPartyVotes(partyToUpdateWith.getPartyVotes());
            }
            parties.save(foundParty);
            return "Party updated";
        }).orElse("Party not found");
    }

    @DeleteMapping("/parties/{id}")
    public void deletePartyById(@PathVariable Long id){
        parties.deleteById(id);
    }
}
