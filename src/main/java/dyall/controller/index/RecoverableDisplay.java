/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.controller.index;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author nigel
 */
public class RecoverableDisplay implements Serializable {
    
    private String recoverableName;
    private BigDecimal recoverableAmount;
    private String recoverablesideNote;

    public RecoverableDisplay() {
    }

    /**
     * @return the recoverableName
     */
    public String getRecoverableName() {
        return recoverableName;
    }

    /**
     * @param recoverableName the recoverableName to set
     */
    public void setRecoverableName(String recoverableName) {
        this.recoverableName = recoverableName;
    }

     /**
     * @return the recoverablesideNote
     */
    public String getRecoverablesideNote() {
        return recoverablesideNote;
    }

    /**
     * @param recoverablesideNote the recoverablesideNote to set
     */
    public void setRecoverablesideNote(String recoverablesideNote) {
        this.recoverablesideNote = recoverablesideNote;
    }

    /**
     * @return the recoverableAmount
     */
    public BigDecimal getRecoverableAmount() {
        return recoverableAmount;
    }

    /**
     * @param recoverableAmount the recoverableAmount to set
     */
    public void setRecoverableAmount(BigDecimal recoverableAmount) {
        this.recoverableAmount = recoverableAmount;
    }
    
    
    
    
}
