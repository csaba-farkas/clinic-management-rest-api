
package org.clinigment.rest.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author csaba
 */
@Entity
@Table(name = "allergy")
@XmlRootElement
public class Allergy implements Serializable {
    
    
    
    @Id
    @Column(name = "pPATIENT_ID")
    @XmlID
    private Long patientId;
    
    @Id
    @Column(name = "ALLERGY_TYPE")
    private String allergyType;

    public Allergy(Long patientId, String allergyType) {
        this.patientId = patientId;
        this.allergyType = allergyType;
    }
    
    
    public Allergy() { 
        //Empty constructor for JPA    
    }

    public Long getPatientId() {
        return patientId;
    }
    
    public void setPatientId(Long patientId) {
        //patient Id can only be set if it is null (ie. at creation)
        if(this.patientId == null && patientId != null) {
            this.patientId = patientId;
        }
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    @Override
    public String toString() {
        return "Allergy{" + "patientId=" + patientId + ", allergyType=" + allergyType + '}';
    }
    
    
    
    
}
