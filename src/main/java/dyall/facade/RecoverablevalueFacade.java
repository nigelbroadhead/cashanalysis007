/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.facade;

import dyall.entity.Recoverablevalue;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nigel
 */
@Stateless
public class RecoverablevalueFacade extends AbstractFacade<Recoverablevalue> {

    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecoverablevalueFacade() {
        super(Recoverablevalue.class);
    }
    
}
