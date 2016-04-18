
package org.clinigment.rest.api.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import org.clinigment.rest.api.controller.exceptions.LoginException;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.model.LoginForm;
import org.clinigment.rest.api.model.UserAccount;

/**
 *
 * @author csaba
 */
public class LoginController {
    
    private UserTransaction userTransaction = null;
    private EntityManagerFactory emFactory = null;
    
    public LoginController(UserTransaction userTransaction, EntityManagerFactory emFactory) {
        this.userTransaction = userTransaction;
        this.emFactory = emFactory;
    }
    
    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    
    public void login(LoginForm loginForm) throws LoginException {
        
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        List<UserAccount> resultList = emFactory.createEntityManager().createNativeQuery("SELECT * FROM system_user WHERE USERNAME = '" + loginForm.getUsername() + "';", UserAccount.class).getResultList();
        
        if(resultList.size() == 0) {
            throw new LoginException();
        }
        
        //List can contain maximum of 1 element
        UserAccount userAccount = resultList.get(0);
        
        //Compare passwords
        if(!userAccount.getPassword().equals(password)) {
            throw new LoginException();
        }
    }
}
