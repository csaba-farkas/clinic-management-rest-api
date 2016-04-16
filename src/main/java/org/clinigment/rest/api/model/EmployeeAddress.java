
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
@Table(name = "employee_address")
@XmlRootElement
public class EmployeeAddress implements Serializable {
    
    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Long empId;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private Employee employee;
    
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

    public EmployeeAddress(Employee employee, String addressLine1, String addressLine2, String addressLine3, String cityTown, String county, String country) {
        this.empId = employee.getId();
        this.employee = employee;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.cityTown = cityTown;
        this.county = county;
        this.country = country;
    }

    public EmployeeAddress() {
        //Empty constructor for JPA
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @XmlTransient
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        System.out.println("Log in setEmployee: " + employee);
        this.empId = employee.getId();
        this.employee = employee;
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
        return "EmployeeAddress{" + "empId=" + empId + ", employee=" + employee + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3=" + addressLine3 + ", cityTown=" + cityTown + ", county=" + county + ", country=" + country + '}';
    }

    
}
