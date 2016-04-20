
package org.clinigment.rest.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author csaba
 */
@Entity
@Table(name = "allergy")
@XmlRootElement
public class Allergy implements Serializable {
    
    @Id
    @Column(name = "PATIENT_ID", nullable = false)
    private Long patientId;
    
    @ManyToOne(targetEntity = Patient.class, optional = true)
    @PrimaryKeyJoinColumn(name = "PATIENT_ID", referencedColumnName = "PATIENT_ID")
    private Patient patient;
    
    @Column(name = "ALLERGY_TYPE", nullable = false)
    private String allergyType;
    
    public Allergy(Patient patient, String allergyType) {
        this.patientId = patient.getId();
        this.patient = patient;
        this.allergyType = allergyType;
    }
    
    public Allergy() { 
        //Empty constructor for JPA    
    }

    public Long getPatientId() {
        return patientId;
    }
    
    @XmlTransient
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        this.patientId = this.patient.getId();
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
