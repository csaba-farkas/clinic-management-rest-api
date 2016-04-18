
package org.clinigment.rest.api.model.messagebodyreaders;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import org.clinigment.rest.api.model.LoginForm;

/**
 *
 * @author csaba
 */
@Provider
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class LoginFormMessageBodyReader implements MessageBodyReader<LoginForm>{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return LoginForm.class == type;
    }

    @Override
    public LoginForm readFrom(Class<LoginForm> type, 
            Type genericType, 
            Annotation[] annotations, 
            MediaType mediaType, 
            MultivaluedMap<String, String> httpHeaders, 
            InputStream entityStream) throws IOException, WebApplicationException {
        
        LoginForm loginForm = new LoginForm();
        JsonParser parser = Json.createParser(entityStream);
        
        while(parser.hasNext()) {
            switch(parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch(key) {
                        case "username":
                            loginForm.setUsername(parser.getString());
                            break;
                        case "password":
                            loginForm.setPassword(parser.getString());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return loginForm;
    }
}
