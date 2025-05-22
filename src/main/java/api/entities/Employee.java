package api.entities;

import java.io.Serializable;

/**
 * Η κλάση Employee αναπαριστά έναν υπάλληλο της εταιρίας ενοικίασης.
 */
public class Employee implements Serializable {

    /** Όνομα χρήστη (username) */
    private final String username;

    /** Κωδικός πρόσβασης (password) */
    private final String password;

    /**
     * Constructor της κλάσης Employee.
     * @param username username
     * @param password password
     */
    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Επιστρέφει το όνομα χρήστη
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Επιστρέφει τον κωδικό πρόσβασης
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Επιστρέφει αναπαράσταση του υπαλλήλου ως string
     * @return string
     */
    @Override
    public String toString() {
        return username + "," + password;
    }
}
