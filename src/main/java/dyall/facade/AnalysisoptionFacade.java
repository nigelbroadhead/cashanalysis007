/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.facade;

import dyall.entity.Analysisoption;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nigel
 */
@Stateless
public class AnalysisoptionFacade extends AbstractFacade<Analysisoption> {

    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalysisoptionFacade() {
        super(Analysisoption.class);
    }
    
}
