/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dyall.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nigel
 */
@ApplicationScoped
@Named("indexController")
public class IndexController implements Serializable {

    @PersistenceContext(unitName = "dyall_cashanalysis007_war_1.0PU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    private String todaysDate;

    private Date startDate;
    private BigDecimal averageValue;
    private BigDecimal valueOfCompletionsForSingleDay;
    private Date endDate;
    private String sideNoteToValueOfCompletionsForSingleDay;

    private List<String> analysisoptions;
    private String selectedAnalysisOption;

    private String valueOfCompletionsUnderAnalysis;
    private String sideNoteToValueOfCompletionsUnderAnalysis;

    private String chosenAnalysisOption;

    private String valueOfCompletionsToday;
    private String sideNoteToValueOfCompletionsToday;

    private boolean startDateDisabled;
    private boolean endDateDisabled;

    private String bankBalance;
    private String sideNoteToBankBalance;

//    public IndexController() {
//        initialiseValueOfCompletionsToday();
//    }
////        this.setValueOfCompletionsToday(valueOfCompletionsToday);
//    //        }
//    public void startup(@Observes @Initialized(ApplicationScoped.class) Object context) {
//        // ...
//    }
    @PostConstruct
    public void init() {
        this.resetView();
    }

    public void dateForHeader() {
//        set date for form heading
        Date date = Calendar.getInstance().getTime();
        String dateStr = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(date);
        this.setTodaysDate(dateStr);
    }

    public void calculateValueOfCompletionsToday() {

//        logic for value calculation
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
//        save current start and end dates
        Date saveStartDate = this.getStartDate();
        Date saveEndDate = this.getEndDate();
        Date inst = today.getTime();
        this.setStartDate(inst);
        this.setEndDate(this.getStartDate());
        Integer numberOfCompletions = this.calculateNumberOfCompletionsInPeriod();
//        System.out.println("numberOfCompletions = " + numberOfCompletions);
        BigDecimal bdNumberOfCompletions = BigDecimal.ZERO;
        if (numberOfCompletions != null && numberOfCompletions > 0) {
            bdNumberOfCompletions = new BigDecimal(Integer.toString(numberOfCompletions));
        }

//        if ("Single Day".equals(getChosenAnalysisOption())) {
        if (bdNumberOfCompletions != BigDecimal.ZERO) {
//                BigDecimal bdNumberOfCompletions = new BigDecimal(Integer.toString(numberOfCompletions));
//            EntityManager entityManager;
            BigDecimal value = this.getAverageValue().multiply(bdNumberOfCompletions);
            this.setValueOfCompletionsToday(NumberFormat.getCurrencyInstance().format(value));
            this.setSideNoteToValueOfCompletionsToday("(" + Integer.toString(numberOfCompletions) + " completion(s) at an average of " + NumberFormat.getCurrencyInstance().format(getAverageValue()) + ")");
        } else {
            this.setValueOfCompletionsToday(NumberFormat.getCurrencyInstance().format(BigDecimal.ZERO));
            this.setSideNoteToValueOfCompletionsToday("(Zero completions)");
        }

//       restore start and end dates
        this.setStartDate(saveStartDate);
        this.setEndDate(saveEndDate);
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
//        if (startDate == null) {
//            startDate = new Date();
//        }
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     */
    public void calculateValueOfCompletions() {

//        this.resetView();
//        this.setValueOfCompletionsUnderAnalysis(BigDecimal.ZERO);
        Date inst = null;
        Date saveStartDate = null;
        if ("Single Day".equals(getChosenAnalysisOption())) {
            this.setEndDate(this.getStartDate());
        }
        if ("Current Week".equals(getChosenAnalysisOption())) {
            Calendar today = Calendar.getInstance();
//            today.set(Calendar.HOUR_OF_DAY, 0);
//            today.setTime(date);
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
//            int day = today.get(Calendar.DAY_OF_WEEK);
//            switch (day) {
//                case Calendar.SATURDAY:
//                    today.add(Calendar.DATE, 2);
//                    break;
//                case Calendar.SUNDAY:
//                    today.add(Calendar.DATE, 1);
//                    break;
//                default:
//                    break;
//            }
            inst = today.getTime();
        }

//keep ifs in this order
        if ("Moving Week".equals(getChosenAnalysisOption())) {
            this.setEndDate(this.oneWeekForward(6));
        }

//        Calculate number of completions in period
        if ("Current Week".equals(getChosenAnalysisOption())) {
            saveStartDate = this.getStartDate();
            this.setStartDate(inst);
        }

        Integer numberOfCompletions = this.calculateNumberOfCompletionsInPeriod();

        if ("Current Week".equals(getChosenAnalysisOption())) {
            this.setStartDate(saveStartDate);
        }

        BigDecimal bdNumberOfCompletions = BigDecimal.ZERO;
        if (numberOfCompletions
                != null) {
            bdNumberOfCompletions = new BigDecimal(Integer.toString(numberOfCompletions));
        }

        if ("Single Day".equals(getChosenAnalysisOption())) {
            if (bdNumberOfCompletions != BigDecimal.ZERO) {
//                BigDecimal bdNumberOfCompletions = new BigDecimal(Integer.toString(numberOfCompletions));
                BigDecimal value = this.getAverageValue().multiply(bdNumberOfCompletions);
                this.setValueOfCompletionsUnderAnalysis(NumberFormat.getCurrencyInstance().format(value));

//                    this.setValueOfCompletionsForSingleDay(this.getAverageValue().multiply(bdNumberOfCompletions));
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(" + Integer.toString(numberOfCompletions) + " completion(s) at an average of " + NumberFormat.getCurrencyInstance().format(getAverageValue()) + ")");
            } else {
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(Zero completions at an average of " + NumberFormat.getCurrencyInstance().format(getAverageValue()) + ")");
            }
        }

        if ("Moving Week".equals(getChosenAnalysisOption())) {
//            this.setEndDate(this.oneWeekForward());
            if (numberOfCompletions != null) {
                BigDecimal bdSevenDayPercentageIncrement = new BigDecimal(Integer.toString((int) getEm().createQuery("SELECT i.percentage FROM Increment i WHERE i.numberOfDays = 5").getSingleResult()));
                BigDecimal bdAdjustedNumberOfCompletions = (bdNumberOfCompletions.multiply(bdSevenDayPercentageIncrement).divide(new BigDecimal(Integer.toString(100)))).add(bdNumberOfCompletions);
                BigDecimal value = this.getAverageValue().multiply(bdAdjustedNumberOfCompletions);
                this.setValueOfCompletionsUnderAnalysis(NumberFormat.getCurrencyInstance().format(value));
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(" + bdNumberOfCompletions.toString() + " completion(s) diarised for week from and including date at average " + NumberFormat.getCurrencyInstance().format(this.getAverageValue()) + ", incremented by " + bdSevenDayPercentageIncrement.toString() + "%)");
            } else {
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(Zero completions)");
            }
        }

        if ("Current Week".equals(getChosenAnalysisOption())) {
            long diff = this.getEndDate().getTime() - inst.getTime();
            //diff in days
            long days = (diff / (24 * 60 * 60 * 1000)) - 1;
//            if (days > 0 && days < 6) {
            if (numberOfCompletions != null) {
                BigDecimal bdPercentageIncrement = new BigDecimal(Integer.toString((int) getEm().createQuery("SELECT i.percentage FROM Increment i WHERE i.numberOfDays = " + days).getSingleResult()));
//                    System.out.println("bdPercentageIncrement = " + bdPercentageIncrement);
                BigDecimal bdAdjustedNumberOfCompletions = (bdNumberOfCompletions.multiply(bdPercentageIncrement).divide(new BigDecimal(Integer.toString(100)))).add(bdNumberOfCompletions);
                BigDecimal value = this.getAverageValue().multiply(bdAdjustedNumberOfCompletions);
                this.setValueOfCompletionsUnderAnalysis(NumberFormat.getCurrencyInstance().format(value));
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(" + bdNumberOfCompletions.toString() + " completion(s) in remaining " + String.valueOf(days) + " working day(s) at average " + NumberFormat.getCurrencyInstance().format(this.getAverageValue()) + ", incremented by " + bdPercentageIncrement.toString() + "%)");
            } else {
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(Zero completions)");
            }
//            } else {
//                this.setValueOfCompletionsUnderAnalysis(null);
//                this.setSideNoteToValueOfCompletionsUnderAnalysis("Invalid date choice.  Tapering week must contain current date.");
//
//            }
//            System.out.println("days = " + days);
        }

        if ("Choose Date Range...".equals(getChosenAnalysisOption())) {
            if (bdNumberOfCompletions != BigDecimal.ZERO) {
                BigDecimal value = this.getAverageValue().multiply(bdNumberOfCompletions);
                this.setValueOfCompletionsUnderAnalysis(NumberFormat.getCurrencyInstance().format(value));
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(" + Integer.toString(numberOfCompletions) + " completion(s) at an average of " + NumberFormat.getCurrencyInstance().format(getAverageValue()) + ")");
            } else {
                this.setSideNoteToValueOfCompletionsUnderAnalysis("(Zero completions at an average of " + NumberFormat.getCurrencyInstance().format(getAverageValue()) + ")");
            }
        }
//        return NumberFormat.getCurrencyInstance().format(this.getValueOfCompletionsUnderAnalysis());
    }

    /**
     * @param valueOfCompletionsForSingleDay the valueOfCompletionsForSingleDay
     * to set
     */
    public void setValueOfCompletionsForSingleDay(BigDecimal valueOfCompletionsForSingleDay) {
        this.valueOfCompletionsForSingleDay = valueOfCompletionsForSingleDay;
    }

    /**
     * @return the valueOfCompletionsForSingleDay
     */
    public BigDecimal getValueOfCompletionsForSingleDay() {
        return valueOfCompletionsForSingleDay;
    }

    public void persist(Object object) {
        try {
            utx.begin();
            getEm().persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the analysisoptions
     */
    public List<String> getAnalysisoptions() {
        if (analysisoptions == null || analysisoptions.isEmpty()) {
            analysisoptions = new ArrayList<>();
            analysisoptions.add("Single Day");
            analysisoptions.add("Current Week");
            analysisoptions.add("Moving Week");
            analysisoptions.add("Choose Date Range...");
        }
        return analysisoptions;
    }

    /**
     * @param analysisoptions the analysisoptions to set
     */
    public void setAnalysisoptions(List<String> analysisoptions) {
        this.analysisoptions = analysisoptions;
    }

    public void doAnalysis() {
        if (null != getSelectedAnalysisOption()) {
            switch (getSelectedAnalysisOption()) {
                case "Single Day":
                    this.resetView();
                    this.setChosenAnalysisOption("Single Day");
                    this.setStartDateDisabled(false);
//                    this.setEndDateDisabled(true);
//                    this.calculateValueOfCompletions();
                    break;
                case "Current Week":
//                    this.taperingWeek();
                    this.resetView();
                    this.setChosenAnalysisOption("Current Week");
//                    this.setStartDateDisabled(false);
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    this.setStartDate(c.getTime());
                    this.setEndDate(this.oneWeekForward(6));
                    break;
                case "Moving Week":
//                    this.movingWeek();
                    this.resetView();
                    this.setChosenAnalysisOption("Moving Week");
                    this.setStartDateDisabled(false);
//                    this.calculateValueOfCompletions();
                    break;
                case "Choose Date Range...":
                    this.resetView();
                    this.setChosenAnalysisOption("Choose Date Range...");
                    this.setStartDateDisabled(false);
                    this.setEndDateDisabled(false);
                    break;
                case "reset":
                    this.resetView();
                    break;
                default:
                    this.resetView();
                    break;
            }
        }
    }

    public void onDateSelected() {
        if (null != getChosenAnalysisOption()) {
            switch (getChosenAnalysisOption()) {
                case "Single Day":
                    this.calculateValueOfCompletions();
                    break;
                case "Current Week":
                    this.calculateValueOfCompletions();
                    break;
                case "Moving Week":
                    this.calculateValueOfCompletions();
                    break;
                case "Choose Date Range...":
                    this.calculateValueOfCompletions();
                    break;
                default:
                    this.resetView();
                    break;
            }
        }
    }

//    private void singleDay() {
//        this.setChosenAnalysisOption("Single Day");
//        this.setValueOfCompletionsUnderAnalysis(this.calculateValueOfCompletions());
//        this.setSideNoteToValueOfCompletionsUnderAnalysis(this.getSideNoteToValueOfCompletionsForSingleDay());
//    }
//    private void taperingWeek() {
//        this.setValueOfCompletionsUnderAnalysis(null);
//        this.setSideNoteToValueOfCompletionsUnderAnalysis("Code to be written");
//    }
//    private void movingWeek() {
//        this.setChosenAnalysisOption("Moving Week");
//        this.setValueOfCompletionsUnderAnalysis(this.calculateValueOfCompletions());
//        this.setValueOfCompletionsUnderAnalysis(null);
//        this.setSideNoteToValueOfCompletionsUnderAnalysis(null);
//        this.setEndDate(this.oneWeekForward());
//        this.setValueOfCompletionsForSingleDay(BigDecimal.ZERO);
////        Calculate number of completions in period
//        Integer numberOfCompletions = this.calculateNumberOfCompletionsInPeriod();
//        if (numberOfCompletions != null) {
//            BigDecimal bdNumberOfCompletions = new BigDecimal(Integer.toString(numberOfCompletions));
//            BigDecimal bdSevenDayPercentageIncrement = new BigDecimal(Integer.toString((int) em.createQuery("SELECT i.percentage FROM Increment i WHERE i.numberOfDays = 5").getSingleResult()));
//            BigDecimal bdAdjustedNumberOfCompletions = (bdNumberOfCompletions.multiply(bdSevenDayPercentageIncrement).divide(new BigDecimal(Integer.toString(100)))).add(bdNumberOfCompletions);
//            BigDecimal value = this.getAverageValue().multiply(bdAdjustedNumberOfCompletions);
//            this.setValueOfCompletionsUnderAnalysis(NumberFormat.getCurrencyInstance().format(value));
//            this.setSideNoteToValueOfCompletionsUnderAnalysis("(" + bdNumberOfCompletions.toString() + " completion(s) diarised for week from and including date at average " + NumberFormat.getCurrencyInstance().format(this.getAverageValue()) + ", incremented by " + bdSevenDayPercentageIncrement.toString() + "%)");
//        } else {
//            this.setSideNoteToValueOfCompletionsForSingleDay("(Zero completions)");
//        }
//    }
//    public void view() {
//        Map<String, Object> options = new HashMap<>();
//        options.put("modal", true);
//        options.put("width", 925);
//        options.put("height", 340);
//        options.put("contentWidth", "100%");
//        options.put("contentHeight", "100%");
//        options.put("headerElement", "customheader");
//        RequestContext.getCurrentInstance().openDialog("dialog1", options, null);
//    }
//    public void click() {
//        RequestContext.getCurrentInstance().closeDialog("dialog1");
//        System.out.println("this.getStartDate() = " + this.getStartDate());
//        System.out.println("this.getEndDate() = " + this.getEndDate());
//        Integer numberOfCompletions = this.calculateNumberOfCompletionsInPeriod();
//        if (numberOfCompletions != null) {
//            BigDecimal bdNumberOfCompletions = new BigDecimal(Integer.toString(numberOfCompletions));
//            BigDecimal bdSevenDayPercentageIncrement = new BigDecimal(Integer.toString((int) em.createQuery("SELECT i.percentage FROM Increment i WHERE i.numberOfDays = 5").getSingleResult()));
//            BigDecimal bdAdjustedNumberOfCompletions = (bdNumberOfCompletions.multiply(bdSevenDayPercentageIncrement).divide(new BigDecimal(Integer.toString(100)))).add(bdNumberOfCompletions);
//            BigDecimal value = this.getAverageValue().multiply(bdAdjustedNumberOfCompletions);
//            this.setValueOfCompletionsUnderAnalysis(NumberFormat.getCurrencyInstance().format(value));
//            this.setSideNoteToValueOfCompletionsUnderAnalysis("(" + bdNumberOfCompletions.toString() + " completion(s) diarised for week from and including date at average " + NumberFormat.getCurrencyInstance().format(this.getAverageValue()) + ", incremented by " + bdSevenDayPercentageIncrement.toString() + "%)");
//        } else {
//            this.setSideNoteToValueOfCompletionsForSingleDay("(Zero completions)");
//        }
//        FacesContext context = FacesContext.getCurrentInstance();
//        String viewId = context.getViewRoot().getViewId();
//        ViewHandler handler = context.getApplication().getViewHandler();
//        UIViewRoot root = handler.createView(context, viewId);
//        root.setViewId(viewId);
//        context.setViewRoot(root);
//    }
    /**
     * @return the selectedAnalysisOption
     */
    public String getSelectedAnalysisOption() {
        return selectedAnalysisOption;
    }

    /**
     * @param selectedAnalysisOption the selectedAnalysisOption to set
     */
    public void setSelectedAnalysisOption(String selectedAnalysisOption) {
        this.selectedAnalysisOption = selectedAnalysisOption;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the averageValue
     */
    private BigDecimal getAverageValue() {
        if (averageValue == null || averageValue == BigDecimal.ZERO) {
            averageValue = (BigDecimal) getEm().createQuery("SELECT c.itemvalue FROM Constant c WHERE c.itemdescription = 'Average profit cost      '")
                    .getSingleResult();
        }
        return averageValue;
    }

    /**
     * @param averageValue the averageValue to set
     */
    public void setAverageValue(BigDecimal averageValue) {
        this.averageValue = averageValue;
    }

    /**
     * @return the sideNoteToValueOfCompletionsForSingleDay
     */
    public String getSideNoteToValueOfCompletionsForSingleDay() {
        return sideNoteToValueOfCompletionsForSingleDay;
    }

    /**
     * @param sideNoteToValueOfCompletionsForSingleDay the
     * sideNoteToValueOfCompletionsForSingleDay to set
     */
    public void setSideNoteToValueOfCompletionsForSingleDay(String sideNoteToValueOfCompletionsForSingleDay) {
        this.sideNoteToValueOfCompletionsForSingleDay = sideNoteToValueOfCompletionsForSingleDay;
    }

    /**
     * @return the sideNoteToValueOfCompletionsUnderAnalysis
     */
    public String getSideNoteToValueOfCompletionsUnderAnalysis() {
        return sideNoteToValueOfCompletionsUnderAnalysis;
    }

    /**
     * @param sideNoteToValueOfCompletionsUnderAnalysis the
     * sideNoteToValueOfCompletionsUnderAnalysis to set
     */
    public void setSideNoteToValueOfCompletionsUnderAnalysis(String sideNoteToValueOfCompletionsUnderAnalysis) {
        this.sideNoteToValueOfCompletionsUnderAnalysis = sideNoteToValueOfCompletionsUnderAnalysis;
    }

    private void resetView() {
        //        reset display
//        this.setSelectedAnalysisOption(null);
        this.setStartDateDisabled(true);
        this.setEndDateDisabled(true);
        this.dateForHeader();
        this.setBankBalance(null);
        this.setSideNoteToBankBalance(null);
        this.populateBankBalance();
        this.calculateValueOfCompletionsToday();
        this.setValueOfCompletionsForSingleDay(null);
        this.setValueOfCompletionsUnderAnalysis(null);
        this.setSideNoteToValueOfCompletionsUnderAnalysis(null);
        this.setSideNoteToValueOfCompletionsForSingleDay(null);
        this.setChosenAnalysisOption(null);
//        Put these lines last because code called earlier in this method populates startDate and EndDate
        this.setStartDate(null);
        this.setEndDate(null);
    }

    private Integer calculateNumberOfCompletionsInPeriod() {
        Integer numberOfCompletions = null;
        if (this.getAverageValue() != BigDecimal.ZERO) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String strStartDate = df.format(this.getStartDate());
            String strEndDate = df.format(this.getEndDate());
            numberOfCompletions = (Integer) getEm().createNativeQuery("SELECT SUM(completion_Number) FROM Completion_Stats WHERE DATE(completion_Date) BETWEEN DATE('" + strStartDate + "') AND DATE('" + strEndDate + "')").getSingleResult();
            if (numberOfCompletions == null) {
                numberOfCompletions = 0;
            }
        }
        return numberOfCompletions;
    }

    private Date oneWeekForward(int numberOfDays) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dt = sdf.format(this.getStartDate());  // Start date
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dt));
            c.add(Calendar.DATE, numberOfDays);  // number of days to add
            dt = sdf.format(c.getTime());  // dt is now the new date}
            return sdf.parse(dt);

        } catch (ParseException ex) {
            Logger.getLogger(IndexController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the chosenAnalysisOption
     */
    public String getChosenAnalysisOption() {
        return chosenAnalysisOption;
    }

    /**
     * @param chosenAnalysisOption the chosenAnalysisOption to set
     */
    public void setChosenAnalysisOption(String chosenAnalysisOption) {
        this.chosenAnalysisOption = chosenAnalysisOption;
    }

    /**
     * @return the sideNoteToValueOfCompletionsToday
     */
    public String getSideNoteToValueOfCompletionsToday() {
        return sideNoteToValueOfCompletionsToday;
    }

    /**
     * @param sideNoteToValueOfCompletionsToday the
     * sideNoteToValueOfCompletionsToday to set
     */
    public void setSideNoteToValueOfCompletionsToday(String sideNoteToValueOfCompletionsToday) {
        this.sideNoteToValueOfCompletionsToday = sideNoteToValueOfCompletionsToday;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the valueOfCompletionsUnderAnalysis
     */
    public String getValueOfCompletionsUnderAnalysis() {
        return valueOfCompletionsUnderAnalysis;
    }

    /**
     * @param valueOfCompletionsUnderAnalysis the
     * valueOfCompletionsUnderAnalysis to set
     */
    public void setValueOfCompletionsUnderAnalysis(String valueOfCompletionsUnderAnalysis) {
        this.valueOfCompletionsUnderAnalysis = valueOfCompletionsUnderAnalysis;
    }

    /**
     * @return the valueOfCompletionsToday
     */
    public String getValueOfCompletionsToday() {
        return valueOfCompletionsToday;
    }

    /**
     * @param valueOfCompletionsToday the valueOfCompletionsToday to set
     */
    public void setValueOfCompletionsToday(String valueOfCompletionsToday) {
        this.valueOfCompletionsToday = valueOfCompletionsToday;
    }

    /**
     * @return the todaysDate
     */
    public String getTodaysDate() {
        return todaysDate;
    }

    /**
     * @param todaysDate the todaysDate to set
     */
    public void setTodaysDate(String todaysDate) {
        this.todaysDate = todaysDate;
    }

    /**
     * @return the startDateDisabled
     */
    public boolean isStartDateDisabled() {
        return startDateDisabled;
    }

    /**
     * @param startDateDisabled the startDateDisabled to set
     */
    public void setStartDateDisabled(boolean startDateDisabled) {
        this.startDateDisabled = startDateDisabled;
    }

    /**
     * @return the endDateDisabled
     */
    public boolean isEndDateDisabled() {
        return endDateDisabled;
    }

    /**
     * @param endDateDisabled the endDateDisabled to set
     */
    public void setEndDateDisabled(boolean endDateDisabled) {
        this.endDateDisabled = endDateDisabled;
    }

    public void populateBankBalance() {
        
        this.setSideNoteToBankBalance(null);
        this.setBankBalance(null);
        Date date = new Date();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Query query = getEm().createNativeQuery("SELECT BALANCE FROM OFFICEBALANCE WHERE BANKACCOUNT_ID = " + 1 + " AND BALANCE_DATE = '" + today + "'");
        try {
            BigDecimal result = (BigDecimal) query.getSingleResult();
            this.setBankBalance(NumberFormat.getCurrencyInstance().format(result));
        } catch (Exception e) {
            if (e.getCause() == null) {
                this.setSideNoteToBankBalance("Please set today's Office Account bank balance");
            }
        }
    }

    /**
     * @return the sideNoteToBankBalance
     */
    public String getSideNoteToBankBalance() {
        return sideNoteToBankBalance;
    }

    /**
     * @param sideNoteToBankBalance the sideNoteToBankBalance to set
     */
    public void setSideNoteToBankBalance(String sideNoteToBankBalance) {
        this.sideNoteToBankBalance = sideNoteToBankBalance;
    }

    /**
     * @return the bankBalance
     */
    public String getBankBalance() {
        return bankBalance;
    }

    /**
     * @param bankBalance the bankBalance to set
     */
    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

}
