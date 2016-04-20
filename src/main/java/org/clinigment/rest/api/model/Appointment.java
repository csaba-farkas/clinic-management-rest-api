
package org.clinigment.rest.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.clinigment.rest.api.model.adapters.LocalDateAdapter;
import org.clinigment.rest.api.model.adapters.LocalDateTimeAdapter;
import org.clinigment.rest.api.model.converters.LocalDateAttributeConverter;
import org.clinigment.rest.api.model.converters.LocalDateTimeAttributeConverter;

/**
 *
 * @author csaba
 */

@Entity
@Table(name = "appointment")
@XmlRootElement
public class Appointment implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPOINTMENT_ID", updatable = false, nullable = false)
    @XmlID
    private Long id;
    
    @Column(name = "DATE", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    
    @Column(name = "START_TIME", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;
    
    @Column(name = "END_TIME", nullable = false)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime endTime;
    
    @Column(name = "PATIENT_ID", updatable = false)
    private Long patientId;
    
    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "PATIENT_ID", insertable = false, updatable = false)
    private Patient patient;
    
    @Column(name = "PATIENT_NAME")
    private String patientName;
    
    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "DOCTOR_ID", updatable = false)
    private Long doctorId;
    
    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", referencedColumnName = "EMPLOYEE_ID", insertable = false, updatable = false)
    private Employee employee;
    
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;
    
    public Appointment() {
        //Empty constructor for JPA
    }
    
    public Appointment(Long id) {
        this.id = id;
    }

    public Appointment(Long id, 
            LocalDate date, 
            LocalDateTime startTime, 
            LocalDateTime endTime, 
            Long patientId, 
            Patient patient, 
            String patientName, 
            String contactNumber, 
            String description, 
            Long doctorId, 
            Employee employee) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientId = patientId;
        this.patient = patient;
        this.patientName = patientName;
        this.contactNumber = contactNumber;
        this.description = description;
        this.doctorId = doctorId;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patientId = patient.getId();
        this.patient = patient;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.doctorId = employee.getId();
        this.employee = employee;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

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
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", patientId=" + patientId + ", patient=" + patient + ", patientName=" + patientName + ", contactNumber=" + contactNumber + ", description=" + description + ", doctorId=" + doctorId + ", employee=" + employee + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
    
    
}
