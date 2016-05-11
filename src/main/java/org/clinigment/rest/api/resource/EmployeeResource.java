
package org.clinigment.rest.api.resource;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.clinigment.rest.api.controller.EmployeeController;
import org.clinigment.rest.api.model.Employee;
import org.clinigment.rest.api.model.UserAccount;

/**
 *
 * @author csaba
 */
@Path("employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    
    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private EmployeeController getController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new EmployeeController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public EmployeeResource() {
    }
    
    @POST
    public Response create(Employee employee) {
        try {
            Date now = Calendar.getInstance().getTime();
            employee.setCreatedAt(new Timestamp(now.getTime()));
            getController().create(employee);
            return Response.created(URI.create(employee.getId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified("Employee with PPS number entered, already exists.").build();
        }
    }

    @PUT
    @Path("{id}")
    public Response edit(Employee entity, @PathParam("id") Long id) {
        try {
            Date now = Calendar.getInstance().getTime();
            entity.setUpdatedAt(new Timestamp(now.getTime()));
            getController().edit(entity);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        try {
            getController().destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    public Employee find(@PathParam("id") Long employeeId) {
        return getController().findEmployee(employeeId);
    }

    @GET
    public List<Employee> findAll() {
        return getController().findAllEmployees();
    }
   
    //#################################################
    //#####         User account resources      #######
    //#################################################
    @GET
    @Path("{id}/useraccounts")
    public UserAccount findUserAccount(@PathParam("id") Long employeeId) {
        return getController().findUserAccount(employeeId);
    }
    
    @POST
    @Path("{id}/useraccounts")
    public Response createUserAccount(@PathParam("id") Long employeeId, UserAccount userAccount) {
        try {
            Date now = Calendar.getInstance().getTime();
            userAccount.setCreatedAt(new Timestamp(now.getTime()));
            getController().createUserAccount(employeeId, userAccount);
            return Response.created(null).build();
        } catch(Exception ex) {
            return Response.notModified("Employee already has a user account.").build();
        }
    }
    
}
