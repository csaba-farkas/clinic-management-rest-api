
package org.clinigment.rest.api.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

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
    
    
}
