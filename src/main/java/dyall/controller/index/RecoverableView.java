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
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("recoverableView")
@SessionScoped
public class RecoverableView implements Serializable {
     
    private List<RecoverableDisplay> recoverableList;
    
    @Inject
    private RecoverableService service;
 
    @PostConstruct
    public void init() {
        setRecoverableList(service.createRecoverableCategories());
    }
     
    public void setService(RecoverableService service) {
        this.service = service;
    }

    /**
     * @return the recoverableList
     */
    public List<RecoverableDisplay> getRecoverableList() {
        return recoverableList;
    }

    /**
     * @param recoverableList the recoverableList to set
     */
    public void setRecoverableList(List<RecoverableDisplay> recoverableList) {
        this.recoverableList = recoverableList;
    }


}