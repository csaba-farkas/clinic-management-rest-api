
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.clinigment.rest.api.controller.AppointmentController;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.model.Appointment;

/**
 *
 * @author csaba
 */
@Path("appointments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentResource {
    
    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private AppointmentController getController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new AppointmentController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public AppointmentResource() {
    }
    
    @POST
    public Response create(Appointment entity) {
        try {
            Date now = Calendar.getInstance().getTime();
            entity.setCreatedAt(new Timestamp(now.getTime()));
            getController().create(entity);
            return Response.created(URI.create(entity.getId().toString()))
                    .entity(entity)
                    .build();
        } catch (NonexistentEntityException nex) {
            return Response.notModified(nex.getMessage()).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    @GET
    public List<Appointment> getAll() {
        return getController().findAllAppointments();
    }
    
    @GET
    @Path("id")
    public Appointment getById(@PathParam("id") Long id) {
        return getController().findAppointment(id);
    }
}
