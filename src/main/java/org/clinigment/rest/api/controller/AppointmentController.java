
package org.clinigment.rest.api.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.RollbackFailureException;
import org.clinigment.rest.api.model.Appointment;

/**
 *
 * @author csaba
 */
public class AppointmentController {
    
    private UserTransaction userTransaction = null;
    private EntityManagerFactory emFactory = null;
    
    public AppointmentController(UserTransaction userTransaction, EntityManagerFactory emFactory) {
        this.userTransaction = userTransaction;
        this.emFactory = emFactory;
    }
    
    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    public void create(Appointment appointment) throws RollbackFailureException, Exception {
        EntityManager em = null;
        System.out.println("Log " + appointment);
        try {
            userTransaction.begin();
            em = getEntityManager();
            
            em.persist(appointment);
            
            userTransaction.commit();
        } catch (Exception ex) {
            try {
                userTransaction.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Appointment findAppointment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Appointment.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Appointment> findAllAppointments() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Appointment.class));
            Query q = em.createQuery(cq);
            
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}