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
@Table(name = "RECOVERABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recoverable.findAll", query = "SELECT r FROM Recoverable r")
    , @NamedQuery(name = "Recoverable.findById", query = "SELECT r FROM Recoverable r WHERE r.id = :id")
    , @NamedQuery(name = "Recoverable.findByItemName", query = "SELECT r FROM Recoverable r WHERE r.itemName = :itemName")})
public class Recoverable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 50)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @OneToMany(mappedBy = "recoverableId")
    private Collection<Recoverablevalue> recoverablevalueCollection;

    public Recoverable() {
    }

    public Recoverable(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @XmlTransient
    public Collection<Recoverablevalue> getRecoverablevalueCollection() {
        return recoverablevalueCollection;
    }

    public void setRecoverablevalueCollection(Collection<Recoverablevalue> recoverablevalueCollection) {
        this.recoverablevalueCollection = recoverablevalueCollection;
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
        if (!(object instanceof Recoverable)) {
            return false;
        }
        Recoverable other = (Recoverable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemName;
    }
    
}
