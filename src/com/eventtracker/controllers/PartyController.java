package com.eventtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventtracker.model.Party;
import com.eventtracker.utils.PartyUtils;

@Controller
@RequestMapping("/party")
public class PartyController {

    private final PartyUtils partyUtils;

    @Autowired
    public PartyController(PartyUtils partyUtils) {
        this.partyUtils = partyUtils;
    }

    @RequestMapping(value = "/{partyId}", method = RequestMethod.GET)
    @ResponseBody
    public Party getParty(@PathVariable String partyId) {
        return partyUtils.getParty(partyId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Party createParty(@RequestParam String name, @RequestParam String description,
            @RequestParam("ownerUserId") String ownerUserId) {

        return partyUtils.createParty(name, description, ownerUserId);
    }
}
