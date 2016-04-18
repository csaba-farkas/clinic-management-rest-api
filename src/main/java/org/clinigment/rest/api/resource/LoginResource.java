
package org.clinigment.rest.api.resource;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.clinigment.rest.api.controller.LoginController;
import org.clinigment.rest.api.controller.exceptions.LoginException;
import org.clinigment.rest.api.model.LoginForm;
import org.clinigment.rest.api.model.UnauthorizedEntity;

/**
 *
 * @author csaba
 */

@Path("login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    
    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private LoginController getController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new LoginController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public LoginResource() {
        
    }
    
    @POST
    public Response login(LoginForm loginForm) {
        try {
            getController().login(loginForm);
        } catch (LoginException ex) {
            return Response.status(Status.UNAUTHORIZED)
                    .entity(new UnauthorizedEntity(ex.getMessage()))
                    .build();
        }
        return Response.ok().build();
    }
}
