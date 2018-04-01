/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nigel
 */
@Entity
@Table(name = "RECOVERABLEVALUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recoverablevalue.findAll", query = "SELECT r FROM Recoverablevalue r")
    , @NamedQuery(name = "Recoverablevalue.findById", query = "SELECT r FROM Recoverablevalue r WHERE r.id = :id")
    , @NamedQuery(name = "Recoverablevalue.findByAmount", query = "SELECT r FROM Recoverablevalue r WHERE r.amount = :amount")
    , @NamedQuery(name = "Recoverablevalue.findByItemDate", query = "SELECT r FROM Recoverablevalue r WHERE r.itemDate = :itemDate")
    , @NamedQuery(name = "Recoverablevalue.findBySign", query = "SELECT r FROM Recoverablevalue r WHERE r.sign = :sign")})
public class Recoverablevalue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "ITEM_DATE")
    @Temporal(TemporalType.DATE)
    private Date itemDate;
    @Size(max = 2)
    @Column(name = "SIGN")
    private String sign;
    @JoinColumn(name = "RECOVERABLE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Recoverable recoverableId;

    public Recoverablevalue() {
    }

    public Recoverablevalue(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getItemDate() {
        return itemDate;
    }

    public void setItemDate(Date itemDate) {
        this.itemDate = itemDate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Recoverable getRecoverableId() {
        return recoverableId;
    }

    public void setRecoverableId(Recoverable recoverableId) {
        this.recoverableId = recoverableId;
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
        if (!(object instanceof Recoverablevalue)) {
            return false;
        }
        Recoverablevalue other = (Recoverablevalue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.Recoverablevalue[ id=" + id + " ]";
    }
    
}
