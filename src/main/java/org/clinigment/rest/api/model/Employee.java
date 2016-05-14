
package org.clinigment.rest.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.clinigment.rest.api.model.adapters.LocalDateAdapter;
import org.clinigment.rest.api.model.converters.LocalDateAttributeConverter;
import org.clinigment.rest.api.model.enums.EmpRole;

/**
 *
 * @author csaba
 */

@Entity
@Table(name="employee")
@XmlRootElement
public class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", updatable = false, nullable = false)
    @XmlID
    private Long id;
    
    @Column(name = "TITLE", length = 5)
    private String title;
   
    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;
    
    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;
    
    @Column(name = "MIDDLE_NAME", length = 100)
    private String middleName;
    
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfBirth;
    
    @Column(name = "PPS_NUMBER", length = 100, nullable = false, unique = true)
    private String ppsNumber;
    
    @Column(name = "EMPLOYED_SINCE", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate employedSince;
    
    @Column(name = "EMPLOYED_UNTIL")
    @Convert(converter = LocalDateAttributeConverter.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate employedUntil;
    
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmpRole role;
      
    @Column(name = "MOBILE_PHONE", length = 100, nullable = false)
    private String mobilePhone;
    
    @Column(name = "HOME_PHONE", length = 100)
    private String homePhone;
    
    @Column(name = "EMAIL", length = 100)
    private String email;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeAddress employeeAddress;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserAccount userAccount;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "appEMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID", nullable = true, updatable = true, insertable = true)
    private List<Appointment> appointments;
    
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;
    
    public Employee() {
        //Empty constructor for JPA
    }
    
    public Employee(Long id) {
        this.id = id;
    }

    public Employee(Long employeeId, 
            String title, 
            String firstName, 
            String lastName, 
            String middleName, 
            LocalDate dateOfBirth, 
            String ppsNumber, 
            LocalDate employedSince, 
            LocalDate employedUntil, 
            EmpRole role, 
            String mobilePhone, 
            String homePhone, 
            String email, 
            UserAccount userAccount,
            Timestamp createdAt, 
            Timestamp updatedAt) {
        this.id = employeeId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.ppsNumber = ppsNumber;
        this.employedSince = employedSince;
        this.employedUntil = employedUntil;
        this.role = role;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
        this.employeeAddress = new EmployeeAddress();
        this.userAccount = userAccount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.appointments = new ArrayList<>();
    }

    @XmlID
    public Long getId() {
        return id;
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        this.ppsNumber = ppsNumber;
    }

    public LocalDate getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(LocalDate employedSince) {
        this.employedSince = employedSince;
    }

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public void setEmployedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
    }

    public EmpRole getRole() {
        return role;
    }

    public void setRole(EmpRole role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeAddress getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(EmployeeAddress employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    @XmlTransient
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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

    @XmlTransient
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    //Same as Patient, PPS number is unique in employee table as well.
    //So I used PPS number for hashing and for equals functions.
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.ppsNumber);
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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.ppsNumber, other.ppsNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", dateOfBirth=" + dateOfBirth + ", ppsNumber=" + ppsNumber + ", employedSince=" + employedSince + ", employedUntil=" + employedUntil + ", role=" + role + ", mobilePhone=" + mobilePhone + ", homePhone=" + homePhone + ", email=" + email + ", employeeAddress=" + employeeAddress + ", userAccount=" + userAccount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

     
    /**
     * 
     * @param newEmployee
     */
    
    public void update(Employee newEmployee) {
       this.title = newEmployee.getTitle();
       this.firstName = newEmployee.getFirstName();
       this.lastName = newEmployee.getLastName();
       this.middleName = newEmployee.getMiddleName();
       this.dateOfBirth = newEmployee.getDateOfBirth();
       this.ppsNumber = newEmployee.getPpsNumber();
       this.employedSince = newEmployee.getEmployedSince();
       this.employedUntil = newEmployee.getEmployedUntil();
       this.role = newEmployee.getRole();
       this.mobilePhone = newEmployee.getMobilePhone();
       this.homePhone = newEmployee.getHomePhone();
       this.email = newEmployee.getEmail();
       this.employeeAddress.update(newEmployee.getEmployeeAddress());
       this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    
    
    
    
    
    
    
    
    
    
    
    
}
