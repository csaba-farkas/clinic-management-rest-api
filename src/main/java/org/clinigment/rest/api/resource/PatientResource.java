
package org.clinigment.rest.api.resource;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.naming.InitialContext;
import org.clinigment.rest.api.model.Patient;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.clinigment.rest.api.controller.PatientController;

/**
 *
 * @author csaba
 */
@Path("patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientResource {

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private PatientController getController() {
        try {
            UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            return new PatientController(utx, getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public PatientResource() {
    }

    @POST
    public Response create(Patient entity) {
        try {
            Date now = Calendar.getInstance().getTime();
            entity.setCreatedAt(new Timestamp(now.getTime()));
            getController().create(entity);
            return Response.created(URI.create(entity.getId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified("Patient with PPS number entered, already exists.").build();
        }
    }

    @PUT
    @Path("{id}")
    public Response edit(Patient entity, @PathParam("id") Long id) {
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
    public Patient find(@PathParam("id") Long id) {
        return getController().findPatient(id);
    }

    @GET
    public List<Patient> findAll() {
        return getController().findPatientEntities();
    }

    @GET
    @Path("{max}/{first}")
    public List<Patient> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return getController().findPatientEntities(max, first);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String count() {
        return String.valueOf(getController().getPatientCount());
    }
    
}
