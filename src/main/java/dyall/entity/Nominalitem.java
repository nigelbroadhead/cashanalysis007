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
@Table(name = "NOMINALITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nominalitem.findAll", query = "SELECT n FROM Nominalitem n")
    , @NamedQuery(name = "Nominalitem.findById", query = "SELECT n FROM Nominalitem n WHERE n.id = :id")
    , @NamedQuery(name = "Nominalitem.findByAmount", query = "SELECT n FROM Nominalitem n WHERE n.amount = :amount")
    , @NamedQuery(name = "Nominalitem.findByDescription", query = "SELECT n FROM Nominalitem n WHERE n.description = :description")
    , @NamedQuery(name = "Nominalitem.findByFinishDate", query = "SELECT n FROM Nominalitem n WHERE n.finishDate = :finishDate")
    , @NamedQuery(name = "Nominalitem.findByMovable", query = "SELECT n FROM Nominalitem n WHERE n.movable = :movable")
    , @NamedQuery(name = "Nominalitem.findBySign", query = "SELECT n FROM Nominalitem n WHERE n.sign = :sign")
    , @NamedQuery(name = "Nominalitem.findByStartDate", query = "SELECT n FROM Nominalitem n WHERE n.startDate = :startDate")})
public class Nominalitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FINISH_DATE")
    @Temporal(TemporalType.DATE)
    private Date finishDate;
    @Column(name = "MOVABLE")
    private Boolean movable;
    @Size(max = 2)
    @Column(name = "SIGN")
    private String sign;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @JoinColumn(name = "FREQUENCY_ID", referencedColumnName = "ID")
    @ManyToOne
    private Frequency frequencyId;

    public Nominalitem() {
    }

    public Nominalitem(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean getMovable() {
        return movable;
    }

    public void setMovable(Boolean movable) {
        this.movable = movable;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Frequency getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Frequency frequencyId) {
        this.frequencyId = frequencyId;
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
        if (!(object instanceof Nominalitem)) {
            return false;
        }
        Nominalitem other = (Nominalitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.Nominalitem[ id=" + id + " ]";
    }
    
}
