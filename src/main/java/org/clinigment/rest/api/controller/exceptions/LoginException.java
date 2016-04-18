
package org.clinigment.rest.api.controller.exceptions;

/**
 *
 * @author csaba
 */
public class LoginException extends Exception {
    
    public LoginException() {
        super("Login failed! Unkown username/password");
    }
}
