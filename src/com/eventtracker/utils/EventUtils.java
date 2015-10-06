package com.eventtracker.utils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventtracker.model.Event;
import com.eventtracker.repository.EventRespository;

@Service
public class EventUtils {

    private final EventRespository eventRepository;

    @Autowired
    public EventUtils(EventRespository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String description, String type, String partyId, Date date, String reporterUserId) {
        String eventId = UUID.randomUUID().toString();
        Event event = new Event(eventId, description, type, partyId, date, reporterUserId);
        return eventRepository.createEvent(event);
    }

    public List<Event> getEventsForPartyId(String partyId) {
        return eventRepository.getEventsForPartyId(partyId);
    }

}
