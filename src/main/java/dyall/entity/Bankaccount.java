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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "BANKACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bankaccount.findAll", query = "SELECT b FROM Bankaccount b")
    , @NamedQuery(name = "Bankaccount.findById", query = "SELECT b FROM Bankaccount b WHERE b.id = :id")
    , @NamedQuery(name = "Bankaccount.findByAccountNumber", query = "SELECT b FROM Bankaccount b WHERE b.accountNumber = :accountNumber")
    , @NamedQuery(name = "Bankaccount.findBySortcode", query = "SELECT b FROM Bankaccount b WHERE b.sortcode = :sortcode")})
public class Bankaccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 8)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 6)
    @Column(name = "SORTCODE")
    private String sortcode;
    @OneToMany(mappedBy = "bankaccountId")
    private Collection<Officebalance> officebalanceCollection;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "ID")
    @ManyToOne
    private Bank bankId;

    public Bankaccount() {
    }

    public Bankaccount(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    @XmlTransient
    public Collection<Officebalance> getOfficebalanceCollection() {
        return officebalanceCollection;
    }

    public void setOfficebalanceCollection(Collection<Officebalance> officebalanceCollection) {
        this.officebalanceCollection = officebalanceCollection;
    }

    public Bank getBankId() {
        return bankId;
    }

    public void setBankId(Bank bankId) {
        this.bankId = bankId;
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
        if (!(object instanceof Bankaccount)) {
            return false;
        }
        Bankaccount other = (Bankaccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return accountNumber;
    }
    
}
