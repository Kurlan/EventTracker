package com.eventtracker.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventtracker.model.Party;
import com.eventtracker.model.UserParty;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class PartyRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PartyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Party createParty(Party party, UserParty userParty) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(userParty);
            session.save(party);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.info(e.getMessage());
            return null;
        } finally {
            session.close();
        }
        return party;
    }

    public Party getParty(String partyId) {
        Session session = sessionFactory.openSession();
        log.info("partyId: " + partyId);
        Transaction transaction = null;
        Party party = null;

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Party.class).add(Restrictions.eq("id", partyId))
                    .setMaxResults(1);
            party = (Party) criteria.uniqueResult();
            session.getTransaction().commit();

        } catch (Exception e) {
            log.info(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return party;
    }

    @SuppressWarnings("unchecked")
    public List<Party> getPartiesByUserId(String userId) {
        Session session = sessionFactory.openSession();
        log.info("Getting parties by userId: " + userId);
        Transaction transaction = null;
        List<Party> parties = null;

        try {
            transaction = session.beginTransaction();
            Query query = session
                    .createSQLQuery(
                            "select * from party as p inner join user_party as up on up.party_id = p.id where user_id = :user_id")
                    .addEntity(Party.class);

            query.setParameter("user_id", userId);
            parties = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            log.info(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return parties;
    }

}
