package com.eventtracker.utils;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventtracker.model.Party;
import com.eventtracker.model.UserParty;
import com.eventtracker.repository.PartyRepository;

@Service
public class PartyUtils {

    private final PartyRepository partyRepository;

    @Autowired
    public PartyUtils(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public Party createParty(String name, String descrtipion, String userId, String relationshipType, String photoURL) {
        String partyId = UUID.randomUUID().toString();
        String relationshipId = UUID.randomUUID().toString();
        Party party = new Party(partyId, name, descrtipion, photoURL);
        UserParty userParty = new UserParty(relationshipId, userId, partyId, relationshipType);
        return partyRepository.createParty(party, userParty);
    }

    public Party getParty(String partyId) {
        return partyRepository.getParty(partyId);
    }

    public List<Party> getPartyByUser(String userId) {
        return partyRepository.getPartiesByUserId(userId);
    }

}
