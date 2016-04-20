
package org.clinigment.rest.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author csaba
 */
@Entity
@Table(name = "treatment")
@XmlRootElement
public class Treatment implements Serializable {
    
    private final static long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TREATMENT_ID")
    @XmlID
    private Long id;
    
    @Column(name = "TREATMENT_NAME")
    private String name;
    
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;
    
    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL)
    private List<CompletedTreatment> completedTreatments;

    public Treatment() {
    }

    public Treatment(Long id) {
        this.id = id;
    }

    public Treatment(Long id, String name) {
        this.id = id;
        this.name = name;
        this.completedTreatments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public List<CompletedTreatment> getCompletedTreatments() {
        return completedTreatments;
    }

    public void setCompletedTreatments(List<CompletedTreatment> completedTreatments) {
        this.completedTreatments = completedTreatments;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Treatment other = (Treatment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Treatment{" + "id=" + id + ", name=" + name + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", completedTreatments=" + completedTreatments + '}';
    } 
}
