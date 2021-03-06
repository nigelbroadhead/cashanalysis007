/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nigel
 */
@Entity
@Table(name = "ANALYSISOPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Analysisoption.findAll", query = "SELECT a FROM Analysisoption a")
    , @NamedQuery(name = "Analysisoption.findById", query = "SELECT a FROM Analysisoption a WHERE a.id = :id")
    , @NamedQuery(name = "Analysisoption.findByDescription", query = "SELECT a FROM Analysisoption a WHERE a.description = :description")})
public class Analysisoption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    public Analysisoption() {
    }

    public Analysisoption(Long id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Analysisoption)) {
            return false;
        }
        Analysisoption other = (Analysisoption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.Analysisoption[ id=" + id + " ]";
    }
    
}
