package dyall.controller;

import dyall.entity.Officebalance;
import dyall.controller.util.JsfUtil;
import dyall.controller.util.JsfUtil.PersistAction;
import dyall.facade.OfficebalanceFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("officebalanceController")
@SessionScoped
public class OfficebalanceController implements Serializable {

    @EJB
    private dyall.facade.OfficebalanceFacade ejbFacade;
    private List<Officebalance> items = null;
    private Officebalance selected;

    public OfficebalanceController() {
    }

    public Officebalance getSelected() {
        return selected;
    }

    public void setSelected(Officebalance selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OfficebalanceFacade getFacade() {
        return ejbFacade;
    }

    public Officebalance prepareCreate() {
        selected = new Officebalance();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OfficebalanceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OfficebalanceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OfficebalanceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Officebalance> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Officebalance getOfficebalance(java.util.Date id) {
        return getFacade().find(id);
    }

    public List<Officebalance> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Officebalance> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Officebalance.class)
    public static class OfficebalanceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OfficebalanceController controller = (OfficebalanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "officebalanceController");
            return controller.getOfficebalance(getKey(value));
        }

        java.util.Date getKey(String value) {
            java.util.Date key;
            key = java.sql.Date.valueOf(value);
            return key;
        }

        String getStringKey(java.util.Date value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Officebalance) {
                Officebalance o = (Officebalance) object;
                return getStringKey(o.getBalanceDate());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Officebalance.class.getName()});
                return null;
            }
        }

    }

}
