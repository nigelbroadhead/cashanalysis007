/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.controller.index;

import dyall.entity.Recoverable;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nigel
 */
@SessionScoped
@Named("recoverableLogic")
public class RecoverableLogic implements Serializable {

//    Enable access to data
    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    private List recoverablesList;
    private String netValueOfRecoverables;

    public void listRecoverableCategories() {
        this.setRecoverablesList(em.createQuery("SELECT r FROM Recoverable r").getResultList());
        for (int i = 0; i < getRecoverablesList().size(); i++) {
            Recoverable recoverable = (Recoverable) getRecoverablesList().get(i);
            System.out.println("recoverable.getItemName() = " + recoverable.getItemName());
        }
    }

    /**
     * @return the netValueOfRecoverables
     */
    public String getNetValueOfRecoverables() {
        return netValueOfRecoverables;
    }

    /**
     * @param netValueOfRecoverables the netValueOfRecoverables to set
     */
    public void setNetValueOfRecoverables(String netValueOfRecoverables) {
        this.netValueOfRecoverables = netValueOfRecoverables;
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the recoverablesList
     */
    public List getRecoverablesList() {
        return recoverablesList;
    }

    /**
     * @param recoverablesList the recoverablesList to set
     */
    public void setRecoverablesList(List recoverablesList) {
        this.recoverablesList = recoverablesList;
    }
}
