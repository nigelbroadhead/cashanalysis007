/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nigel
 */
@Entity
@Table(name = "OFFICEBALANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Officebalance.findAll", query = "SELECT o FROM Officebalance o")
    , @NamedQuery(name = "Officebalance.findByBalanceDate", query = "SELECT o FROM Officebalance o WHERE o.balanceDate = :balanceDate")
    , @NamedQuery(name = "Officebalance.findByBalance", query = "SELECT o FROM Officebalance o WHERE o.balance = :balance")})
public class Officebalance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE_DATE")
    @Temporal(TemporalType.DATE)
    private Date balanceDate = Calendar.getInstance().getTime();
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @JoinColumn(name = "BANKACCOUNT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Bankaccount bankaccountId;

    public Officebalance() {
    }

    public Officebalance(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Bankaccount getBankaccountId() {
        return bankaccountId;
    }

    public void setBankaccountId(Bankaccount bankaccountId) {
        this.bankaccountId = bankaccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (balanceDate != null ? balanceDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Officebalance)) {
            return false;
        }
        Officebalance other = (Officebalance) object;
        if ((this.balanceDate == null && other.balanceDate != null) || (this.balanceDate != null && !this.balanceDate.equals(other.balanceDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.Officebalance[ balanceDate=" + balanceDate + " ]";
    }
    
}
