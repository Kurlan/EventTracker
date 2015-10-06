package com.eventtracker.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventtracker.model.Event;
import com.eventtracker.utils.EventUtils;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventUtils eventUtils;

    @Autowired
    public EventController(EventUtils eventUtils) {
        this.eventUtils = eventUtils;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventsForParty(@RequestParam("partyId") String partyId) {
        return eventUtils.getEventsForPartyId(partyId);
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Event createEvent(@RequestParam("description") String description, @RequestParam("type") String type,
            @RequestParam("partyId") String partyId,
            @RequestParam("date") @DateTimeFormat(iso = ISO.DATE_TIME) Date date) {
        return eventUtils.createEvent(description, type, partyId, date);
    }
}
