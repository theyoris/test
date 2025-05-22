package api.entities;

import java.io.Serializable;

/**
 * Η κλάση Customer αναπαριστά έναν πελάτη που μπορεί να νοικιάσει αυτοκίνητα.
 */
public class Customer implements Serializable {

    /** Αριθμός Φορολογικού Μητρώου (μοναδικός) */
    private final String afm;

    /** Ονοματεπώνυμο πελάτη */
    private final String fullName;

    /** Τηλέφωνο επικοινωνίας */
    private final String phoneNumber;

    /**
     * Constructor της κλάσης Customer.
     * @param afm afm
     * @param fullName fullName
     * @param phoneNumber phoneNumber
     */
    public Customer(String afm, String fullName, String phoneNumber) {
        this.afm = afm;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Επιστρέφει το ΑΦΜ του πελάτη
     * @return afm
     */
    public String getAfm() {
        return afm;
    }

    /**
     * Επιστρέφει το ονοματεπώνυμο του πελάτη
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Επιστρέφει το τηλέφωνο του πελάτη
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Επιστρέφει αναπαράσταση του αντικειμένου Customer ως string
     * @return string
     */
    @Override
    public String toString() {
        return afm + "," + fullName + "," + phoneNumber;
    }
}

