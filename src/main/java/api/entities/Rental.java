package api.entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Η κλάση Rental αναπαριστά μία ενοικίαση αυτοκινήτου από έναν πελάτη.
 */
public class Rental implements Serializable {

    /** Μοναδικός κωδικός ενοικίασης */
    private int rentalId;

    /** Το ενοικιαζόμενο αυτοκίνητο */
    private Car car;

    /** Ο πελάτης που νοικιάζει το αυτοκίνητο */
    private Customer customer;

    /** Ημερομηνία έναρξης της ενοικίασης */
    private LocalDate startDate;

    /** Ημερομηνία λήξης της ενοικίασης */
    private LocalDate endDate;

    /** Υπάλληλος που καταχώρησε την ενοικίαση */
    private Employee employee;

    /** Κατάσταση της ενοικίασης **/
    private boolean active;


    /**
     * Constructor της κλάσης Rental.
     * @param rentalId rentalId
     * @param car car
     * @param customer customer
     * @param startDate startDate
     * @param endDate endDate
     * @param employee employee
     */
    public Rental(int rentalId, Car car, Customer customer, LocalDate startDate, LocalDate endDate, Employee employee) {
        this.rentalId = rentalId;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.active = true;       // πάντα true στην αρχή
    }

    /**
     * Επιστρέφει τον κωδικό ενοικίασης
     * @return rentalId
     */
    public int getRentalId() {
        return rentalId;
    }

    /**
     * Επιστρέφει το αυτοκίνητο της ενοικίασης
     * @return car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Επιστρέφει τον πελάτη της ενοικίασης
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Επιστρέφει την ημερομηνία έναρξης
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Επιστρέφει την ημερομηνία λήξης
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Επιστρέφει τον υπάλληλο που καταχώρησε την ενοικίαση
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Επιστρέφει αν η ενοικίαση είναι ακόμα ενεργή ή έχει ολοκληρωθεί
     *
     * @return active true αν είναι ενεργή, false αν έχει επιστραφεί το αυτοκίνητο
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Αλλάζει την κατάσταση της ενοικίασης σε ενεργή ή μη
     *
     * @param active η νέα τιμή (true για ενεργή, false για ολοκληρωμένη)
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Επιστρέφει αναπαράσταση του αντικειμένου Rental ως string
     * @return string
     */
    @Override
    public String toString() {
        return rentalId + "," + car.getId() + "," + customer.getAfm() + "," +
                startDate + "," + endDate + "," + employee.getUsername() + "," + active;
    }
}
