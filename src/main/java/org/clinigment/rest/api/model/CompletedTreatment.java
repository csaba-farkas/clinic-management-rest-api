
package org.clinigment.rest.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author csaba
 */

@Entity
@Table(name = "completed_treatment")
@XmlRootElement
public class CompletedTreatment implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMP_TREATMENT_ID")
    private Long id;
        
    @Column(name = "TREATMENT_ID", nullable = false, updatable =false)
    private Long treatmentId;
    
    @ManyToOne
    @JoinColumn(name = "TREATMENT_ID", referencedColumnName = "TREATMENT_ID", insertable = false, nullable = false, updatable =false)
    private Treatment treatment;
    
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
    
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;

    public CompletedTreatment() {
    }

    public CompletedTreatment(Long id) {
        this.id = id;
    }

    public CompletedTreatment(Long id, Long appointmentRecordId, Long treatmentId, AppointmentRecord appointmentRecord, Treatment treatment, int quantity, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.treatmentId = treatmentId;
        this.treatment = treatment;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    @XmlTransient
    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.treatmentId);
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
        final CompletedTreatment other = (CompletedTreatment) obj;
        
        if (!Objects.equals(this.treatmentId, other.treatmentId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompletedTreatment{treatmentId=" + treatmentId + ", treatment=" + treatment + ", quantity=" + quantity + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }    
}
