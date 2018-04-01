/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nigel
 */
@Entity
@Table(name = "FREQUENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Frequency.findAll", query = "SELECT f FROM Frequency f")
    , @NamedQuery(name = "Frequency.findById", query = "SELECT f FROM Frequency f WHERE f.id = :id")
    , @NamedQuery(name = "Frequency.findByDescription", query = "SELECT f FROM Frequency f WHERE f.description = :description")})
public class Frequency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "frequencyId")
    private Collection<Nominalitem> nominalitemCollection;

    public Frequency() {
    }

    public Frequency(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Nominalitem> getNominalitemCollection() {
        return nominalitemCollection;
    }

    public void setNominalitemCollection(Collection<Nominalitem> nominalitemCollection) {
        this.nominalitemCollection = nominalitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Frequency)) {
            return false;
        }
        Frequency other = (Frequency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
