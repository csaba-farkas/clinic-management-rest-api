
package org.clinigment.rest.api.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.controller.exceptions.RollbackFailureException;
import org.clinigment.rest.api.model.Employee;
import org.clinigment.rest.api.model.EmployeeAddress;
import org.clinigment.rest.api.model.UserAccount;

/**
 *
 * @author csaba
 */
public class EmployeeController implements Serializable {
    
    private UserTransaction userTransaction = null;
    private EntityManagerFactory emFactory = null;
    
    public EmployeeController(UserTransaction userTransaction, EntityManagerFactory emFactory) {
        this.userTransaction = userTransaction;
        this.emFactory = emFactory;
    }
    
    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    public void create(Employee employee) throws RollbackFailureException, Exception {
        EntityManager em = null;
        System.out.println("Log " + employee);
        try {
            userTransaction.begin();
            em = getEntityManager();
            
            //Address - detach address to avoid nullpointer exception on patient id and store it temp
            EmployeeAddress tempAddress = null;
            System.out.println("log address: " + employee.getEmployeeAddress());
            if(employee.getEmployeeAddress() != null) {
                tempAddress = employee.getEmployeeAddress();
                
                employee.setEmployeeAddress(null);
            }
            
            em.persist(employee);
            
            em.flush();
            Long empId = employee.getId();
            System.out.println("Log in create: " + empId);
            
            //Modify address with employee (now with id)
            tempAddress.setEmployee(employee);
            employee.setEmployeeAddress(tempAddress);
                       
            //Update patient with changes
            em.merge(employee);
            
            //Commit transaction
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
    
    public void edit(Employee employee) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            userTransaction.begin();
            em = getEntityManager();
            employee = em.merge(employee);
            userTransaction.commit();
        } catch (Exception ex) {
            try {
                userTransaction.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = employee.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
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
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            em.remove(employee);
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
    
    public Employee findEmployee(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Employee> findAllEmployees() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    //####################################################
    //#########     User Accounts       ##################
    //####################################################
    public UserAccount findUserAccount(Long employeeId) {
        return findEmployee(employeeId).getUserAccount();
        
    }

    public void createUserAccount(Long employeeId, UserAccount userAccount) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            userTransaction.begin();
            em = getEntityManager();
            
            Employee emp = em.find(Employee.class, employeeId);
            System.out.println("Log " + userAccount);
            userAccount.setEmployee(emp);
            userAccount.updateUsername();
            emp.setUserAccount(userAccount);
            em.merge(emp);
                       
            //Commit transaction
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
}
