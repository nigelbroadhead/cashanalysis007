/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "COMPLETION_STATS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompletionStats.findAll", query = "SELECT c FROM CompletionStats c")
    , @NamedQuery(name = "CompletionStats.findByCompletionDate", query = "SELECT c FROM CompletionStats c WHERE c.completionDate = :completionDate")
    , @NamedQuery(name = "CompletionStats.findByCompletionNumber", query = "SELECT c FROM CompletionStats c WHERE c.completionNumber = :completionNumber")})
public class CompletionStats implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPLETION_DATE")
    @Temporal(TemporalType.DATE)
    private Date completionDate;
    @Column(name = "COMPLETION_NUMBER")
    private Integer completionNumber;

    public CompletionStats() {
    }

    public CompletionStats(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getCompletionNumber() {
        return completionNumber;
    }

    public void setCompletionNumber(Integer completionNumber) {
        this.completionNumber = completionNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (completionDate != null ? completionDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompletionStats)) {
            return false;
        }
        CompletionStats other = (CompletionStats) object;
        if ((this.completionDate == null && other.completionDate != null) || (this.completionDate != null && !this.completionDate.equals(other.completionDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dyall.entity.CompletionStats[ completionDate=" + completionDate + " ]";
    }
    
}
