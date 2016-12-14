
package org.clinigment.rest.api.resource;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.ws.rs.BeanParam;
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
import org.clinigment.rest.api.controller.AppointmentController;
import org.clinigment.rest.api.controller.exceptions.NonexistentEntityException;
import org.clinigment.rest.api.model.Appointment;
import org.clinigment.rest.api.resource.beans.AppointmentFilterBean;

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
    
    @PUT
    @Path("{id}")
    public Response edit(Appointment entity, @PathParam("id") Long id) {
        try {
            Date now = Calendar.getInstance().getTime();
            entity.setUpdatedAt(new Timestamp(now.getTime()));
            getController().edit(entity);
            return Response.ok().entity(getController().findAppointment(id)).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    @GET
    public List<Appointment> getAllByDateAndEmployee(@BeanParam AppointmentFilterBean bean) {
        if(bean.getYear() > 0) {
            if(bean.getMonth() < 12 && bean.getMonth() > 0) {
                if(bean.getDay() < 31 && bean.getDay() > 0) {
                    LocalDate date = LocalDate.of(bean.getYear(), bean.getMonth(), bean.getDay());
                    return getController().findAppointmentsByDateAndDoctorId(date, bean.getDoctorId());
                }
            }
        }
        return getController().findAllAppointments();
    }
    
    @GET
    @Path("id")
    public Appointment getById(@PathParam("id") Long id) {
        return getController().findAppointment(id);
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
}
