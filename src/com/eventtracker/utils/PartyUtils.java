package com.eventtracker.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventtracker.model.Party;
import com.eventtracker.repository.PartyRepository;

@Service
public class PartyUtils {

    private final PartyRepository partyRepository;

    @Autowired
    public PartyUtils(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public Party createParty(String name, String descrtipion, String ownerUserId) {
        String partyId = UUID.randomUUID().toString();
        Party party = new Party(partyId, name, descrtipion, ownerUserId);
        return partyRepository.createParty(party);
    }

    public Party getParty(String partyId) {
        return partyRepository.getParty(partyId);
    }

}
