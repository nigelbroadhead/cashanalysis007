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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nigel
 */
@Entity
@Table(name = "INCREMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Increment.findAll", query = "SELECT i FROM Increment i")
    , @NamedQuery(name = "Increment.findById", query = "SELECT i FROM Increment i WHERE i.id = :id")
    , @NamedQuery(name = "Increment.findByNumberOfDays", query = "SELECT i FROM Increment i WHERE i.numberOfDays = :numberOfDays")
    , @NamedQuery(name = "Increment.findByPercentage", query = "SELECT i FROM Increment i WHERE i.percentage = :percentage")})
public class Increment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NUMBER_OF_DAYS")
    private Integer numberOfDays;
    @Column(name = "PERCENTAGE")
    private Integer percentage;

    public Increment() {
    }

    public Increment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
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
        if (!(object instanceof Increment)) {
            return false;
        }
        Increment other = (Increment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.Increment[ id=" + id + " ]";
    }
    
}
