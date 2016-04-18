
package org.clinigment.rest.api.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author csaba
 */
public class LoginController {
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;
    
    public LoginController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
