
package org.clinigment.rest.api.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.clinigment.rest.api.controller.exceptions.LoginException;
import org.clinigment.rest.api.model.Appointment;
import org.clinigment.rest.api.model.Employee;
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
    
    
    public Employee login(LoginForm loginForm) throws LoginException {
        
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        
        TypedQuery<UserAccount> query = emFactory
                                       .createEntityManager()
                                       .createNamedQuery("UserAccount.findByUsernameAndPassword", UserAccount.class)
                                       .setParameter("username", loginForm.getUsername())
                                       .setParameter("password", loginForm.getPassword());

        List<UserAccount> resultList = query.getResultList();
        
        if(resultList.isEmpty()) {
            throw new LoginException();
        }
        
        //List can contain maximum of 1 element
        UserAccount userAccount = resultList.get(0);
        
        Long empId = userAccount.getEmployeeId();
        
        Employee employee = emFactory
                            .createEntityManager()
                            .find(Employee.class, empId);
        
        if(employee == null) {
            throw new LoginException();
        }
        
        return employee;
    }
}
