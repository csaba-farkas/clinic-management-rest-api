
package org.clinigment.rest.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author csaba
 */
@Entity
@Table(name = "patient_address")
@XmlRootElement
public class PatientAddress implements Serializable {
    
    @Id
    @Column(name = "PATIENT_ID", nullable = false)
    private Long patientId;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name = "PATIENT_ID", referencedColumnName = "PATIENT_ID")
    private Patient patient;
    
    @Column(name = "ADDRESS_LINE1", nullable = false, length = 255)
    private String addressLine1;
    
    @Column(name = "ADDRESS_LINE2", length = 255)
    private String addressLine2;
    
    @Column(name = "ADDRESS_LINE3", length = 255)
    private String addressLine3;
    
    @Column(name = "CITY_TOWN", length = 100, nullable = false)
    private String cityTown;
    
    @Column(name = "COUNTY", length = 50)
    private String county;
    
    @Column(name = "COUNTRY", length = 100)
    private String country;

    public PatientAddress(Patient patient, String addressLine1, String addressLine2, String addressLine3, String cityTown, String county, String country) {
        this.patientId = patient.getId();
        this.patient = patient;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.cityTown = cityTown;
        this.county = county;
        this.country = country;
    }

    public PatientAddress() {
        //Empty constructor for JPA
    }

    @XmlTransient
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @XmlTransient
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patientId = patient.getId();
        this.patient = patient;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "PatientAddress{" + "patientId=" + patientId + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3=" + addressLine3 + ", cityTown=" + cityTown + ", county=" + county + ", country=" + country + '}';
    }
}
