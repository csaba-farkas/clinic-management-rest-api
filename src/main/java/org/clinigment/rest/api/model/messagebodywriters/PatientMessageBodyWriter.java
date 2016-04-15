
package org.clinigment.rest.api.model.messagebodywriters;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.clinigment.rest.api.model.Patient;
import org.clinigment.rest.api.model.UnauthorizedEntity;

/**
 *
 * @author csaba
 */
public class PatientMessageBodyWriter implements MessageBodyWriter<Patient>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Patient.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Patient t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //Deprecated
        return -1;
    }

    @Override
    public void writeTo(Patient patient, 
            Class<?> type, Type genericType, 
            Annotation[] annotations, 
            MediaType mediaType, 
            MultivaluedMap<String, Object> httpHeaders, 
            OutputStream entityStream) throws IOException, WebApplicationException {
        
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("patientId", patient.getId())
                .add("title", patient.getTitle())
                .add("firstName", patient.getFirstName())
                .add("lastName", patient.getLastName())
                .add("middleName", patient.getMiddleName())
                .add("dateOfBirth", patient.getDateOfBirth().toString())
                .add("ppsNumber", patient.getPpsNumber()).build();
            
            DataOutputStream dos = new DataOutputStream(entityStream);
            dos.writeBytes(jsonObject.toString());
        
    }
    
}
