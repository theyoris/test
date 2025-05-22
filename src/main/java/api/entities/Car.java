package api.entities;

import java.io.Serializable;

/**
 * Η κλάση Car αναπαριστά ένα αυτοκίνητο
 * που μπορεί να ενοικιαστεί.
 */
public class Car implements Serializable {

    /** Μοναδικός κωδικός του αυτοκινήτου */
    private int id;

    /** Πινακίδα κυκλοφορίας */
    private String licensePlate;

    /** Μάρκα του αυτοκινήτου */
    private String brand;

    /** Μοντέλο του αυτοκινήτου */
    private String model;

    /** Έτος κατασκευής του αυτοκινήτου */
    private int year;

    /** Χρώμα του αυτοκινήτου */
    private String color;

    /** Διαθεσιμότητα του αυτοκινήτου */
    private boolean isAvailable;

    /**
     * Constructor της κλάσης Car.
     * @param id μοναδικός κωδικός
     * @param licensePlate πινακίδα
     * @param brand μάρκα
     * @param model μοντέλο
     * @param year έτος κατασκευής
     * @param color χρώμα
     * @param isAvailable διαθεσιμότητα
     */
    public Car(int id, String licensePlate, String brand, String model, int year, String color, boolean isAvailable) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isAvailable = isAvailable;
    }

    /**
     * Επιστρέφει τον μοναδικό κωδικό του αυτοκινήτου
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Επιστρέφει την πινακίδα του αυτοκινήτου
     * @return licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Επιστρέφει τη μάρκα του αυτοκινήτου
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Επιστρέφει το μοντέλο του αυτοκινήτου
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * Επιστρέφει το έτος κατασκευής του αυτοκινήτου
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Επιστρέφει το χρώμα του αυτοκινήτου
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Επιστρέφει true αν το αυτοκίνητο είναι διαθέσιμο, αλλιώς false
     * @return isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Ορίζει αν το αυτοκίνητο είναι διαθέσιμο ή όχι
     * @param available διαθεσιμότητα
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Επιστρέφει αναπαράσταση του αντικειμένου Car ως string
     * @return string
     */
    @Override
    public String toString() {
        return id + "," + licensePlate + "," + brand + "," + model
                + "," + year + "," + color + ","
                + (isAvailable ? "Διαθέσιμο" : "Ενοικιασμένο");
    }
}