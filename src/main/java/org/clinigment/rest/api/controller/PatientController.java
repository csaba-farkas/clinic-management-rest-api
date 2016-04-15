
package org.clinigment.rest.api.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.controller.exceptions.RollbackFailureException;
import org.clinigment.rest.api.model.Allergy;
import org.clinigment.rest.api.model.Patient;
import org.clinigment.rest.api.model.PatientAddress;

/**
 *
 * @author csaba
 */
public class PatientController implements Serializable {

    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;
    
    public PatientController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patient patient) throws RollbackFailureException, Exception {
        EntityManager em = null;
        System.out.println("Log " + patient);
        try {
            utx.begin();
            em = getEntityManager();
            
            //Address - detach address to avoid nullpointer exception on patient id and store it temp
            PatientAddress tempAddress = null;
            System.out.println("log address: " + patient.getAddress());
            if(patient.getAddress() != null) {
                tempAddress = patient.getAddress();
                
                patient.setAddress(null);
            }
            
            //Allergy collection - deatach to avoid nullpointer exception and store it in temp variable
            List<Allergy> bufferedAllergyCollection = new ArrayList<>();
            if(patient.getAllergyCollection() != null && patient.getAllergyCollection().size() > 0) {
                bufferedAllergyCollection = patient.getAllergyCollection();
                patient.setAllergyCollection(null);
            }
            
            em.persist(patient);
            
            em.flush();
            Long patientId = patient.getId();
            
            //Modify address with patient (now with id)
            tempAddress.setPatient(patient);
            patient.setAddress(tempAddress);
            
            //Modify allergy types with patient (now with id)
            for(int i = 0; i < bufferedAllergyCollection.size(); i++) {
                bufferedAllergyCollection.get(i).setPatient(patient);
            }
            patient.setAllergyCollection(bufferedAllergyCollection);
            
            //Update patient with changes
            em.merge(patient);
            
            //Commit transaction
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public void edit(Patient patient) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            patient = em.merge(patient);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = patient.getId();
                if (findPatient(id) == null) {
                    throw new NonexistentEntityException("The patient with id " + id + " no longer exists.");
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
            utx.begin();
            em = getEntityManager();
            Patient patient;
            try {
                patient = em.getReference(Patient.class, id);
                patient.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patient with id " + id + " no longer exists.", enfe);
            }
            em.remove(patient);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public List<Patient> findPatientEntities() {
        return findPatientEntities(true, -1, -1);
    }

    public List<Patient> findPatientEntities(int maxResults, int firstResult) {
        return findPatientEntities(false, maxResults, firstResult);
    }

    private List<Patient> findPatientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patient.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Patient findPatient(Long id) {
        EntityManager em = getEntityManager();
        try {
            System.out.println("Working harder...");
            System.out.println("log patient " + em.find(Patient.class, id).toString());
            return em.find(Patient.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patient> rt = cq.from(Patient.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
