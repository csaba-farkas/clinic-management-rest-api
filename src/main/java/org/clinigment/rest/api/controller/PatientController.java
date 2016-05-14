
package org.clinigment.rest.api.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.controller.exceptions.RollbackFailureException;
import org.clinigment.rest.api.model.Allergy;
import org.clinigment.rest.api.model.Appointment;
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

    /**
     * Create new patient record
     * @param patient
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void create(Patient patient) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            String errorMessage = validatePatientObject(patient);
            
            
            if(!errorMessage.isEmpty()) {
                throw new Exception(errorMessage);
            }
            utx.begin();
            em = getEntityManager();
            
            //Address - detach address to avoid nullpointer exception on patient id and store it temp
            PatientAddress tempAddress = new PatientAddress();
            System.out.println("log address: " + patient.getPatientAddress());
            if(patient.getPatientAddress() != null) {
                tempAddress = patient.getPatientAddress();
                
                patient.setPatientAddress(null);
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
            patient.setPatientAddress(tempAddress);
            
            //Modify allergy types with patient (now with id)
            for(int i = 0; i < bufferedAllergyCollection.size(); i++) {
                bufferedAllergyCollection.get(i).setPatientId(patient.getId());
            }
            patient.setAllergyCollection(bufferedAllergyCollection);
            
            //Update patient with changes
            em.merge(patient);
            
            //Commit transaction
            utx.commit();
        } catch (Exception ex) {
            try {
                if(!ex.getMessage().isEmpty()) {
                    throw ex;
                }
                utx.rollback();
            } catch (Exception re) {
                if(!re.getMessage().isEmpty()) {
                    throw re;
                }
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Update and existing patient record.
     * 
     * @param newPatient is a Patient object with 'new' details
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(Patient newPatient) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            String errorMessage = validatePatientObject(newPatient);
            
            if(!errorMessage.isEmpty()) {
                throw new Exception(errorMessage);
            }
            utx.begin();
            em = getEntityManager();
            
            //Find patient-to-update in database
            Patient oldPatient = em.find(Patient.class, newPatient.getId());
            //Call update method of patient entity class to update patient
            if(oldPatient == null) {
                throw new NonexistentEntityException("The patient with id " + newPatient.getId() + " no longer exists.");
            }
            oldPatient.update(newPatient);
            
            
            utx.commit();
        } catch (Exception ex) {
            
            try {
                if(!ex.getMessage().isEmpty()) {
                    throw ex;
                }
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("The patient with PPS number " + newPatient.getPpsNumber() + " already exists.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = newPatient.getId();
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
            Patient patient = em.getReference(Patient.class, id);
            if(patient == null) {
                throw new NonexistentEntityException("The patient with id " + id + " no longer exists.");
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
            
            List<Patient> allPatients = q.getResultList();
            for(Patient patient : allPatients) {
                //"SELECT i FROM " + getEntityClass().getSimpleName() + " i WHERE i.id IN :ids", entityClass
                
                TypedQuery<Appointment> query = em
                                                .createNamedQuery("Appointment.findByPatientId", Appointment.class)
                                                .setParameter("patientId", patient.getId());
                List<Appointment> results = query.getResultList();
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Patient findPatient(Long id) {
        EntityManager em = getEntityManager();
        try {
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

    /**
     * Check if patient is valid and has all the required fields
     * Required fields:
     * 1. First name
     * 2. Last name
     * 3. PPS number
     * 4. DOB
     * 5. Gender
     * 6. Mobile phone
     * 7. Next of kin name
     * 8. Next of kin contact number
     * 9. Patient Address:
     *      a. Address line 1
     *      b. City/Town
     * @param patient
     * @return error message
     */
    private String validatePatientObject(Patient patient) throws Exception {
        String errorMessage = "";
        if(patient.getFirstName() == null || patient.getFirstName().isEmpty()) {
                errorMessage += "First name is required";
            }
            if(patient.getLastName() == null || patient.getLastName().isEmpty()) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Last name is required";
            }
            if(patient.getPpsNumber() == null || patient.getPpsNumber().isEmpty()) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "PPS number is required";
            }
            if(patient.getDateOfBirth() == null) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Date of birth is required";
            }
            if(patient.getGender() == null) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Gender is required";
            }
            if(patient.getMobilePhone() == null || patient.getMobilePhone().isEmpty()) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Mobile phone number is required";
            }
            if(patient.getNextOfKinName() == null || patient.getNextOfKinName().isEmpty()) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Next of kin name is required";
            }
            if(patient.getPatientAddress() == null) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Patient address is required";
            }
            if(patient.getPatientAddress().getAddressLine1() == null || patient.getPatientAddress().getAddressLine1().isEmpty()) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "Address line 1 is required";
            }
            if(patient.getPatientAddress().getCityTown() == null || patient.getPatientAddress().getCityTown().isEmpty()) {
                if(!errorMessage.isEmpty()) {
                    errorMessage += "+";
                }
                errorMessage += "City/Town is required";
            }
        
            
            return errorMessage;
    }
    
}
