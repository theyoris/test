package api.authentication;

import api.entities.Employee;
import api.services.EmployeeService;

import java.io.*;
import java.util.List;

/**
 * Κλάση που διαχειρίζεται τη διαδικασία του login
 */
public class LoginManager {

    /** Το αρχείο που περιέχει τους χρήστες */
    private final String userFilePath = "data/users.txt";

    /** Το service με όλους τους υπαλλήλους */
    private final EmployeeService employeeService;

    /**
     * Constructor που παίρνει το EmployeeService
     * @param employeeService employeeService
     */
    public LoginManager(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Αν δεν υπάρχει το αρχείο users.txt, δημιουργεί τους 2 default χρήστες της εκφώνησης (admin1 και admin2)
     */
    public void initializeDefaultUsers() {
        File file = new File(userFilePath);

        File directory = new File("data");

        if (!directory.exists()) {
            directory.mkdir();
        }

        if (file.exists()) {
            // Το αρχείο υπάρχει ήδη, δεν κάνουμε τίποτα
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Δημιουργούμε τα 2 default accounts
            // με hashed κωδικό
            String admin1Password = "password1";
            String admin2Password = "password2";

            writer.write("admin1," + admin1Password);
            writer.newLine();

            writer.write("admin2," + admin2Password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την αρχικοποίηση των default users: " + e.getMessage());
        }
    }

    /**
     * Διαβάζει τους χρήστες από το users.txt και τους προσθέτει στο EmployeeService
     */
    public void loadUsersFromFile() {
        File file = new File(userFilePath);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    employeeService.addEmployee(new Employee(username, password));
                }
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα στην ανάγνωση των χρηστών: " + e.getMessage());
        }
    }

    /**
     * Ελέγχει αν τα στοιχεία login είναι σωστά
     * @param username username
     * @param password password
     * @return result
     */
    public boolean checkLogin(String username, String password) {
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee emp : employees) {

            if (emp.getUsername().equals(username)) {
                return emp.getPassword().equals(password);
            }
        }
        return false;
    }

    /**
     * Επιστρέφει τον υπάλληλο που έχει το συγκεκριμένο username
     *
     * @param username το username που ψάχνουμε
     * @return emp ο υπάλληλος αν βρεθεί, αλλιώς null
     */
    public Employee findByUsername(String username) {
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee emp : employees) {
            if (emp.getUsername().equals(username)) {
                return emp;
            }
        }

        return null;
    }

}
