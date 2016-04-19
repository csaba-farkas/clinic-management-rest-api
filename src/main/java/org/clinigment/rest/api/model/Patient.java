
package org.clinigment.rest.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.clinigment.rest.api.model.adapters.LocalDateAdapter;
import org.clinigment.rest.api.model.converters.LocalDateAttributeConverter;
import org.clinigment.rest.api.model.enums.Gender;

/**
 *
 * @author csaba
 */
@Entity
@Table(name="patient")
@XmlRootElement
public class Patient implements Serializable {

    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID", updatable = false, nullable = false)
    private Long id;
    
    @Column(name = "TITLE", length = 5)
    private String title;
    
    @Basic(optional = false)
    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;
    
    @Column(name = "MIDDLE_NAME", length = 100)
    private String middleName;
   
    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;
   
    @Column(name = "PPS_NUMBER", length = 100, nullable = false, unique = true)
    private String ppsNumber;
   
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = false, length = 6)
    private Gender gender;
    
    @Column(name = "EMAIL", length = 255)
    private String email;
    
    @Column(name = "MOBILE_PHONE", nullable = false, length = 50)
    private String mobilePhone;
    
    @Column(name = "HOME_PHONE", length = 50)
    private String homePhone;
        
    @Column(name = "NEXT_OF_KIN_NAME", nullable = false, length = 200)
    private String nextOfKinName;
    
    @Column(name = "NEXT_OF_KIN_CONTACT", nullable = false, length = 100)
    private String nextOfKinContact;
   
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Allergy> allergyCollection;
    
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private PatientAddress patientAddress;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointmentCollection;
            
    public Patient() {
        //Empty constructor for JPA
    }

    public Patient(Long id) {
        this.id = id;
    }

    public Patient(Long id, 
            String title, 
            String firstName, 
            String middleName, 
            String lastName, 
            String ppsNumber, 
            LocalDate dateOfBirth, 
            Gender gender, 
            String email, 
            String mobilePhone, 
            String homePhone, 
            String nextOfKinName, 
            String nextOfKinContact, 
            Timestamp createdAt, 
            Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.ppsNumber = ppsNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinContact = nextOfKinContact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        
        //Create new patient address object
        this.patientAddress = new PatientAddress();
        
        //Collections
        this.allergyCollection = new ArrayList<>();
        this.appointmentCollection = new ArrayList<>();
    }

    /**
     * Getter of id property.
     * 
     * @return patient id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter of id property.
     * 
     * @param id patient's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter of title property.
     * 
     * @return patient's title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        this.ppsNumber = ppsNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public PatientAddress getAddress() {
        return patientAddress;
    }

    public void setAddress(PatientAddress address) {
        this.patientAddress = address;
    }

    public String getNextOfKinName() {
        return nextOfKinName;
    }

    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }

    public String getNextOfKinContact() {
        return nextOfKinContact;
    }

    public void setNextOfKinContact(String nextOfKinContact) {
        this.nextOfKinContact = nextOfKinContact;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Allergy> getAllergyCollection() {
        return allergyCollection;
    }

    public void setAllergyCollection(List<Allergy> allergyCollection) {
        this.allergyCollection = allergyCollection;
    }

    public PatientAddress getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(PatientAddress patientAddress) {
        this.patientAddress = patientAddress;
    }

    @XmlTransient
    public List<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(List<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.ppsNumber);
        return hash;
    }

    /**
     * When comparing the equality of two Patient objects, their PPS number
     * property is the base of the comparison. Apart from the id (which doesn't exist
     * before they are written into the database), only the PPS number is unique for
     * every patient.
     * 
     * @param obj Object to compare to
     * @return true if two objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.ppsNumber, other.ppsNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", title=" + title + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", ppsNumber=" + ppsNumber + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", email=" + email + ", mobilePhone=" + mobilePhone + ", homePhone=" + homePhone + ", nextOfKinName=" + nextOfKinName + ", nextOfKinContact=" + nextOfKinContact + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", allergyCollection=" + allergyCollection + ", patientAddress=" + patientAddress + ", appointmentCollection=" + appointmentCollection + '}';
    }

       
       
}