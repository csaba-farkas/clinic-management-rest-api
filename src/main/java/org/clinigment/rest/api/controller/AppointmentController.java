
package org.clinigment.rest.api.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.controller.exceptions.RollbackFailureException;
import org.clinigment.rest.api.model.Appointment;
import org.clinigment.rest.api.model.Employee;
import org.clinigment.rest.api.model.Patient;

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
            
            String errorMessage = "";
            if(em.find(Employee.class, appointment.getDoctorId()) == null) {
                errorMessage += "Doctor/Hygienist with id " + appointment.getDoctorId() + " doesn't exist.";
            }
            
            if(appointment.getPatientId() != null) {
                if(em.find(Patient.class, appointment.getPatientId()) == null) {
                    errorMessage += "<br />Patient with id " + appointment.getPatientId() + " doesn't exist.";
                }
            }
            
            if(errorMessage.length() > 0) {
                throw new NonexistentEntityException(errorMessage);
            }
            
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
    
    public void edit(Appointment newAppointment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            userTransaction.begin();
            em = getEntityManager();
            
            //Find patient-to-update in database
            System.out.println("Appointment id: " + newAppointment.getId());
            Appointment oldAppointment = em.find(Appointment.class, newAppointment.getId());
            if(oldAppointment == null) {
                throw new NonexistentEntityException("Appointment with ID " + oldAppointment.getId() + " doesn't exist.");
            }
            //Call update method of patient entity class to update patient
            oldAppointment.update(newAppointment);
            
            userTransaction.commit();
        } catch (Exception ex) {
            try {
                userTransaction.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = newAppointment.getId();
                if (findAppointment(id) == null) {
                    throw new NonexistentEntityException("The appointment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            userTransaction.begin();
            em = getEntityManager();
            Appointment appointment = em.getReference(Appointment.class, id);
            if(appointment == null) {
                throw new NonexistentEntityException("The appointment with id " + id + " no longer exists.");
            }
            em.remove(appointment);
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

    public List<Appointment> findAppointmentsByDateAndDoctorId(LocalDate date, Long doctorId) {
        EntityManager em = getEntityManager();
        try {
            System.out.println("I'm here");
            TypedQuery<Appointment> query = em
                                            .createNamedQuery("Appointment.findByPatientIdOnDate", Appointment.class)
                                            .setParameter("patientId", doctorId)
                                            .setParameter("date", date);
            System.out.println("query: " + query.toString());
            return query.getResultList();
        } catch(Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
        
    }
}
