package com.eventtracker.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventtracker.model.Event;
import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("/party")
public class GetEventsController {
	
    @RequestMapping(value = "/{partyId}/events", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventsForParty(@PathVariable String partyId) {
    	return ImmutableList.of(new Event(partyId, "He did it", "PEEPEE"));
    }
}
