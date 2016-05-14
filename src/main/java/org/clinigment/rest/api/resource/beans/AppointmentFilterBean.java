
package org.clinigment.rest.api.resource.beans;

import javax.ws.rs.QueryParam;

/**
 *
 * @author csaba
 */
public class AppointmentFilterBean {
    
    private @QueryParam("year") int year;
    private @QueryParam("month") int month;
    private @QueryParam("day") int day;
    private @QueryParam("doctorId") Long doctorId;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    
    
    
}
