
package org.clinigment.rest.api.model.messagebodywriters;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.clinigment.rest.api.model.UnauthorizedEntity;

/**
 *
 * @author csaba
 */

@Provider
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class UnauthorizedEntityMessageBodyWriter implements MessageBodyWriter<UnauthorizedEntity>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //If type is UnauthorizedEntity, return true -> "I can write this type"
        return UnauthorizedEntity.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(UnauthorizedEntity t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //Deprecated since JPA 1.0
        return -1;
    }

    @Override
    public void writeTo(UnauthorizedEntity unauthorizedEntity, 
            Class<?> type, 
            Type genericType, 
            Annotation[] annotations, 
            MediaType mediaType, 
            MultivaluedMap<String, Object> httpHeaders, 
            OutputStream entityStream) throws IOException, WebApplicationException {
        
        try {
            
            JAXBContext jaxbContext = JAXBContext.newInstance(UnauthorizedEntity.class);
            
            //Serialize Unauthorized entity if requested Content-type = xml
            if(mediaType.toString().equals(MediaType.APPLICATION_XML)) {
                jaxbContext.createMarshaller().marshal(unauthorizedEntity, entityStream);
            }
            
            JsonObject jsonObject = Json.createObjectBuilder()
                        .add("errorCode", unauthorizedEntity.getErrorCode())
                        .add("message", unauthorizedEntity.getMessage()).build();
            
            DataOutputStream dos = new DataOutputStream(entityStream);
            dos.writeBytes(jsonObject.toString());
        } catch (JAXBException ex) {
            throw new ProcessingException("Error serializing UnauthorizedEntity to the output stream.");
        }
        
        
    }
    
}
