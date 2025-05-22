package api.services;


import api.entities.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Διαχειρίζεται τους πελάτες της εφαρμογής
 */
public class CustomerService {

    /** Λίστα με όλους τους πελάτες */
    private final List<Customer> customers;

    /** Το αρχείο με τους πελάτες */
    private final String customerFilePath = "data/customers.txt";

    /**
     * Constructor που φορτώνει τους πελάτες από το αρχείο ή το csv αρχικοποίησης
     */
    public CustomerService() {
        customers = new ArrayList<>();
        loadCustomersFromFile();
    }

    /**
     * Προσθέτει έναν νέο πελάτη και τον αποθηκεύει
     * @param customer customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomersToFile();
    }

    /**
     * Επιστρέφει τη λίστα με όλους τους πελάτες
     * @return customers
     */
    public List<Customer> getAllCustomers() {
        return customers;
    }

    /**
     * Ψάχνει πελάτη με βάση το ΑΦΜ
     * @param afm afm
     * @return customer
     */
    public Customer findCustomerByAfm(String afm) {
        for (Customer customer : customers) {
            if (customer.getAfm().equals(afm)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Ενημερώνει έναν πελάτη με νέα στοιχεία
     *
     * @param updatedCustomer updatedCustomer
     */
    public void updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getAfm().equals(updatedCustomer.getAfm())) {
                customers.set(i, updatedCustomer);
                saveCustomersToFile();
                return;
            }
        }
    }

    /**
     * Αναζητά πελάτες με βάση ΑΦΜ, όνομα ή τηλέφωνο
     * @param afm afm (ή null)
     * @param fullName fullName (ή null)
     * @param phone phone (ή null)
     * @return results
     */
    public List<Customer> searchCustomers(String afm, String fullName, String phone) {
        List<Customer> results = new ArrayList<>();
        for (Customer customer : customers) {
            if (afm != null && !customer.getAfm().equals(afm)) continue;
            if (fullName != null && !customer.getFullName().equalsIgnoreCase(fullName)) continue;
            if (phone != null && !customer.getPhoneNumber().equals(phone)) continue;
            results.add(customer);
        }
        return results;
    }

    /**
     * Αποθηκεύει όλους τους πελάτες στο αρχείο .txt
     */
    public void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customerFilePath))) {
            for (Customer customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα στην αποθήκευση πελατών: " + e.getMessage());
        }
    }

    /**
     * Φορτώνει τους πελάτες από το .txt ή το csv
     */
    public void loadCustomersFromFile() {
        File file = new File(customerFilePath);
        if (!file.exists()) {
            loadInitialCustomersFromCSV();
            saveCustomersToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(customerFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String afm = parts[0];
                    String fullName = parts[1];
                    String phone = parts[2];
                    Customer customer = new Customer(afm, fullName, phone);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα στην ανάγνωση πελατών: " + e.getMessage());
        }
    }

    /**
     * Φορτώνει τους αρχικούς πελάτες από το αρχείο CSV
     */
    public void loadInitialCustomersFromCSV() {
        /** Το αρχείο csv με τους αρχικούς πελάτες */
        String initialCustomerPath = "data/initial_customers.csv";

        File file = new File(initialCustomerPath);
        if (!file.exists()) {
            System.out.println("Δεν βρέθηκε το αρχείο αρχικών πελατών.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // εδώ αγνοούμε την πρώτη γραμμή δηλαδή τις επικεφαλίδες
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length == 3) {
                    String afm = parts[0];
                    String fullName = parts[1];
                    String phone = parts[2];
                    Customer customer = new Customer(afm, fullName, phone);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα στην ανάγνωση αρχικών πελατών: " + e.getMessage());
        }
    }
}
