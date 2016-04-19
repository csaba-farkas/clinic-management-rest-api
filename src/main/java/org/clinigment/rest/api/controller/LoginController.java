
package org.clinigment.rest.api.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.clinigment.rest.api.controller.exceptions.LoginException;
import org.clinigment.rest.api.model.LoginForm;
import org.clinigment.rest.api.model.UserAccount;

/**
 *
 * @author csaba
 */
public class LoginController {
    
    private EntityManagerFactory emFactory = null;
    
    public LoginController(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }
    
    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    
    public void login(LoginForm loginForm) throws LoginException {
        
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        List<UserAccount> resultList = emFactory
                .createEntityManager()
                .createNativeQuery("SELECT * FROM system_user WHERE USERNAME = '" + username + "';", UserAccount.class)
                .getResultList();
        
        if(resultList.isEmpty()) {
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
