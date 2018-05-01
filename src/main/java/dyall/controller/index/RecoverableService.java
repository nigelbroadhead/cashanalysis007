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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named("recoverableService")
@ApplicationScoped
public class RecoverableService {

    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    private List<RecoverableDisplay> list1;

    List<RecoverableDisplay> createRecoverableCategories() {
        List<Recoverable> list = em.createQuery("SELECT r FROM Recoverable r").getResultList();
        setList1(new ArrayList<RecoverableDisplay>());
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                RecoverableDisplay e = new RecoverableDisplay();
                String name = list.get(i).getItemName();
                e.setRecoverableName(name);
                Recoverable recoverable = list.get(i);
                e.setRecoverableAmount(createRecoverableValue(recoverable));
                getList1().add(i, e);
            }
        }
        return getList1();
    }

    private BigDecimal createRecoverableValue(Recoverable recoverable) {

        Query qMinus = em.createQuery("SELECT SUM(r.amount) FROM Recoverablevalue r WHERE r.sign = :sign AND r.recoverableId = :recoverableId");
        qMinus.setParameter("sign", "-");
        qMinus.setParameter("recoverableId", recoverable);
        BigDecimal resultMinus = (BigDecimal) qMinus.getSingleResult();

        Query qPlus = em.createQuery("SELECT SUM(r.amount) FROM Recoverablevalue r WHERE r.sign = :sign AND r.recoverableId = :recoverableId");
        qPlus.setParameter("sign", "+");
        qPlus.setParameter("recoverableId", recoverable);
        BigDecimal resultPlus = (BigDecimal) qPlus.getSingleResult();
//        System.out.println("result = " + result);
//        String recoverableValue = "2000.00";
        BigDecimal result = resultMinus.subtract(resultPlus);
        return result;
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
     * @return the list1
     */
    public List<RecoverableDisplay> getList1() {
        return list1;
    }

    /**
     * @param list1 the list1 to set
     */
    public void setList1(List<RecoverableDisplay> list1) {
        this.list1 = list1;
    }
}
