
package org.clinigment.rest.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author csaba
 */


@Entity
@Table(name="treatment_record")
@XmlRootElement
public class TreatmentRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "RECORD_ID", nullable = false, updatable = false)
    private Long id;
    
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "PATIENT_ID")
    private Long patientId;
    
    @ManyToOne
    private Patient patient;
    
    
}
