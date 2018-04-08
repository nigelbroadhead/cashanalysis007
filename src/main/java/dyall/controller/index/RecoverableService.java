/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.controller.index;

/**
 *
 * @author nigel
 */
import dyall.entity.Recoverable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("recoverableService")
@ApplicationScoped 
public class RecoverableService {    
     
    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
     
    public List<Recoverable> createRecoverableCategories() {
        List<Recoverable> list = new ArrayList<>();
        list = em.createQuery("SELECT r FROM Recoverable r").getResultList();      
        return list;
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
}