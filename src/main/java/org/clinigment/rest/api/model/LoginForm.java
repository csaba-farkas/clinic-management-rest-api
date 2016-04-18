
package org.clinigment.rest.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author csaba
 */
@Entity
@Table(name="system_user")
@XmlRootElement
public class LoginForm {
    
    private String username;
    private String password;
    
    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm{" + "username=" + username + ", password=" + password + '}';
    }
    
    
}
