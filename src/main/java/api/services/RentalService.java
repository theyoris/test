package api.services;

import api.entities.Car;
import api.entities.Customer;
import api.entities.Employee;
import api.entities.Rental;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Διαχειρίζεται τις ενοικιάσεις των αυτοκινήτων
 */
public class RentalService {

    /** Όλες οι ενοικιάσεις που έχουν γίνει */
    private List<Rental> rentals;

    /** Το αρχείο όπου αποθηκεύονται οι ενοικιάσεις */
    private final String rentalFilePath = "data/rentals.txt";

    /** Εξαρτήσεις από άλλες υπηρεσίες για να βρίσκουμε τα αντίστοιχα αντικείμενα */
    private final CarService carService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    /** Μετρητής για επόμενο κωδικό ενοικίασης */
    private int nextRentalId = 1;

    /**
     * Constructor που παίρνει τα services που χρειάζεται για να βρίσκει car και customer
     * @param carService carService
     * @param customerService customerService
     */
    public RentalService(CarService carService, CustomerService customerService, EmployeeService employeeService) {
        this.carService = carService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.rentals = new ArrayList<>();
        loadRentalsFromFile();
    }

    /**
     * Επιστρέφει όλες τις ενοικιάσεις
     * @return rentals
     */
    public List<Rental> getAllRentals() {
        return rentals;
    }

    /**
     * Δημιουργεί μια νέα ενοικίαση και αποθηκεύει την αλλαγή
     * @param car car
     * @param customer customer
     * @param startDate startDate
     * @param endDate endDate
     * @param employee employee
     */
    public void rentCar(Car car, Customer customer, LocalDate startDate, LocalDate endDate, Employee employee) {
        Rental rental = new Rental(nextRentalId++, car, customer, startDate, endDate, employee);
        car.setAvailable(false); // ενοικιάστηκε
        rentals.add(rental);

        saveRentalsToFile();

        carService.saveCarsToFile(); // για να ενημερωθεί η διαθεσιμότητα του οχήματος
    }

    /**
     * Επιστρέφει ένα αυτοκίνητο (το κάνει πάλι διαθέσιμο)
     * @param rental rental
     */
    public void returnCar(Rental rental) {
        rental.setActive(false); // ολοκληρώθηκε
        rental.getCar().setAvailable(true);
        saveRentalsToFile();
        carService.saveCarsToFile();
    }

    /**
     * Επιστρέφει όλες τις ενοικιάσεις για έναν πελάτη
     * @param afm afm
     * @return customerRentals
     */
    public List<Rental> getRentalsByCustomerAfm(String afm) {
        List<Rental> customerRentals = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getCustomer().getAfm().equals(afm)) {
                customerRentals.add(r);
            }
        }
        return customerRentals;
    }

    /**
     * Επιστρέφει όλες τις ενοικιάσεις για ένα αυτοκίνητο
     * @param plate plate
     * @return carRentals
     */
    public List<Rental> getRentalsByCarPlate(String plate) {
        List<Rental> carRentals = new ArrayList<>();

        for (Rental r : rentals) {
            if (r.getCar().getLicensePlate().equalsIgnoreCase(plate)) {
                carRentals.add(r);
            }
        }
        return carRentals;
    }

    /**
     * Αποθηκεύει όλες τις ενοικιάσεις στο αρχείο rentals.txt
     */
    public void saveRentalsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rentalFilePath))) {
            for (Rental rental : rentals) {
                writer.write(rental.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των ενοικιάσεων: " + e.getMessage());
        }
    }

    /**
     * Φορτώνει τις ενοικιάσεις από το αρχείο rentals.txt
     */
    public void loadRentalsFromFile() {
        File file = new File(rentalFilePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    int carId = Integer.parseInt(parts[1]);
                    String afm = parts[2];
                    LocalDate start = LocalDate.parse(parts[3]);
                    LocalDate end = LocalDate.parse(parts[4]);
                    String username = parts[5];
                    boolean active = Boolean.parseBoolean(parts[6]);

                    Car car = carService.findCarById(carId);
                    Customer customer = customerService.findCustomerByAfm(afm);
                    Employee employee = employeeService.findByUsername(username);

                    if (car != null && customer != null) {
                        Rental rental = new Rental(id, car, customer, start, end, employee);
                        rental.setActive(active);
                        rentals.add(rental);
                        if (id >= nextRentalId) {
                            nextRentalId = id + 1;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση των ενοικιάσεων: " + e.getMessage());
        }
    }
}
