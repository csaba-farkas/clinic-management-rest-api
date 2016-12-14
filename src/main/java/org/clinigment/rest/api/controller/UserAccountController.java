
package org.clinigment.rest.api.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.RollbackFailureException;
import org.clinigment.rest.api.model.UserAccount;

/**
 *
 * @author csaba
 */
public class UserAccountController {
    
    private UserTransaction userTransaction = null;
    private EntityManagerFactory emFactory = null;
    
    public UserAccountController(UserTransaction userTransaction, EntityManagerFactory emFactory) {
        this.userTransaction = userTransaction;
        this.emFactory = emFactory;
    }
    
    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    public void create(UserAccount userAccount) throws RollbackFailureException, Exception {
        EntityManager entityManager = null;
        try {
            userTransaction.begin();
            entityManager = getEntityManager();
            
            entityManager.persist(userAccount);
            
            userTransaction.commit();
        } catch (Exception ex) {
            
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    
    
}
