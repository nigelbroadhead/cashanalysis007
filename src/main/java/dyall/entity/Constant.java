/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CONSTANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Constant.findAll", query = "SELECT c FROM Constant c")
    , @NamedQuery(name = "Constant.findById", query = "SELECT c FROM Constant c WHERE c.id = :id")
    , @NamedQuery(name = "Constant.findByItemdescription", query = "SELECT c FROM Constant c WHERE c.itemdescription = :itemdescription")
    , @NamedQuery(name = "Constant.findByItemvalue", query = "SELECT c FROM Constant c WHERE c.itemvalue = :itemvalue")})
public class Constant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 25)
    @Column(name = "ITEMDESCRIPTION")
    private String itemdescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ITEMVALUE")
    private BigDecimal itemvalue;

    public Constant() {
    }

    public Constant(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public BigDecimal getItemvalue() {
        return itemvalue;
    }

    public void setItemvalue(BigDecimal itemvalue) {
        this.itemvalue = itemvalue;
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
        if (!(object instanceof Constant)) {
            return false;
        }
        Constant other = (Constant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.Constant[ id=" + id + " ]";
    }
    
}
