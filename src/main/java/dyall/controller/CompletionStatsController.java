package dyall.controller;

/**
 *
 * @author nigel
 */
import dyall.entity.CompletionStats;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named
@SessionScoped
public class CompletionStatsController implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private ScheduleEvent event = new DefaultScheduleEvent();

    @Inject
    IndexController indexController;

    public CompletionStatsController() {

    }

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                Date startDate;
                Date endDate;
                int runningTotal = 0;
                int completionNumber;
                String eventString = null;
                List<Object> completionsList = em
                        .createQuery("SELECT c FROM CompletionStats c WHERE c.completionDate >= :start AND c.completionDate <= :end ORDER BY c.completionDate ASC")
                        .setParameter("start", start)
                        .setParameter("end", end)
                        .getResultList();
                for (int i = 0; i < completionsList.size(); i++) {
                    CompletionStats completionStat = (CompletionStats) completionsList.get(i);
                    completionNumber = completionStat.getCompletionNumber();
                    startDate = completionStat.getCompletionDate();
                    endDate = startDate;
                    if (runningTotal == 0) {
                        runningTotal = completionNumber;
                    } else {
                        runningTotal = runningTotal + completionNumber;
                    }
                    if (i == completionsList.size() - 1) {
                        eventString = String.format(Integer.toString(completionNumber) + "%n%nPeriod total: " + Integer.toString(runningTotal));
                    } else {
                        eventString = Integer.toString(completionNumber);
                    }
                    addEvent(new DefaultScheduleEvent(eventString, startDate, endDate));
                }
            }
        };
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        CompletionStats cs = new CompletionStats();
        cs.setCompletionNumber(Integer.parseInt(event.getTitle()));
        cs.setCompletionDate(event.getStartDate());
        if (event.getId() == null) {
            eventModel.addEvent(event);
            persist(cs);
        } else {
            eventModel.updateEvent(event);
            merge(cs);
        }
        event = new DefaultScheduleEvent();
//        Recalculate completions
        indexController.calculateValueOfCompletionsToday();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        CompletionStats cs = new CompletionStats();
        cs.setCompletionNumber(Integer.parseInt(event.getTitle()));
        cs.setCompletionDate(event.getStartDate());
        eventModel.updateEvent(event);
        remove(cs);

        event = new DefaultScheduleEvent();
    }
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public void merge(Object object) {
        try {
            utx.begin();
            em.merge(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    public void remove(Object object) {
        try {
            utx.begin();
            em.remove(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
