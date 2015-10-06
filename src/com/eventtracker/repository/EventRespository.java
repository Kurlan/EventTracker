package com.eventtracker.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventtracker.model.Event;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class EventRespository {
    private final SessionFactory sessionFactory;

    @Autowired
    public EventRespository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Event createEvent(Event event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                log.info(e.getMessage());
                transaction.rollback();
            }
            return null;
        } finally {
            session.close();
        }
        return event;
    }

    @SuppressWarnings("unchecked")
    public List<Event> getEventsForPartyId(String partyId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Event> events = null;

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Event.class).add(Restrictions.eq("partyId", partyId))
                    .addOrder(Order.desc("date"));
            events = (List<Event>) criteria.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return events;
    }
}
