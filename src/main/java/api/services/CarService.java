package api.services;

import api.entities.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Διαχειρίζεται τα αυτοκίνητα της εφαρμογής
 */
public class CarService {

    /** Η λίστα με όλα τα αυτοκίνητα */
    private final List<Car> cars;

    /** Το αρχείο που αποθηκεύουμε τα δεδομένα των αυτοκινήτων */
    private final String carFilePath = "data/cars.txt";

    /**
     * Constructor που φορτώνει τα αυτοκίνητα είτε από το αρχείο, είτε από το αρχικό csv
     */
    public CarService() {
        cars = new ArrayList<>();
        loadCarsFromFile();
    }

    /**
     * Επιστρέφει τη λίστα με όλα τα αυτοκίνητα
     * @return cars
     */
    public List<Car> getAllCars() {
        return cars;
    }

    /**
     * Επιστρέφει τα διαθέσιμα αυτοκίνητα
     * @return availableCars
     */
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    /**
     * Προσθέτει ένα καινούργιο αυτοκίνητο και το αποθηκεύει
     * @param car car
     */
    public void addCar(Car car) {
        cars.add(car);
        saveCarsToFile();
    }

    /**
     * Ενημερώνει ένα αυτοκίνητο με νέα στοιχεία
     *
     * @param updatedCar updatedCar
     */
    public void updateCar(Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {

            if (cars.get(i).getId() == updatedCar.getId()) {
                cars.set(i, updatedCar);
                saveCarsToFile();

                return;
            }
        }
    }

    /**
     * Αναζητά αυτοκίνητα με βάση κάποια κριτήρια
     * @param brand brand (ή null αν δεν χρησιμοποιείται)
     * @param model model (ή null)
     * @param plate licensePlate (ή null)
     * @param color color (ή null)
     * @param isAvailable isAvailable (ή null)
     * @return results
     */
    public List<Car> searchCars(String brand, String model, String plate, String color, Boolean isAvailable) {
        List<Car> results = new ArrayList<>();

        for (Car car : cars) {
            if (brand != null && !car.getBrand().equalsIgnoreCase(brand)) continue;
            if (model != null && !car.getModel().equalsIgnoreCase(model)) continue;
            if (plate != null && !car.getLicensePlate().equalsIgnoreCase(plate)) continue;
            if (color != null && !car.getColor().equalsIgnoreCase(color)) continue;
            if (isAvailable != null && car.isAvailable() != isAvailable) continue;
            results.add(car);
        }
        return results;
    }

    /**
     * Βρίσκει ένα αυτοκίνητο με βάση την πινακίδα
     * @param licensePlate licensePlate
     * @return car
     */
    public Car findCarByPlate(String licensePlate) {
        for (Car car : cars) {
            if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return car;
            }
        }
        return null;
    }

    /**
     * Βρίσκει ένα αυτοκίνητο με βάση το id του
     * @param id id
     * @return car
     */
    public Car findCarById(int id) {
        for (Car car : cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    /**
     * Αποθηκεύει όλα τα αυτοκίνητα στο αρχείο cars.txt
     */
    public void saveCarsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(carFilePath))) {
            for (Car car : cars) {
                writer.write(car.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των αυτοκινήτων: " + e.getMessage());
        }
    }

    /**
     * Φορτώνει τα αυτοκίνητα από το cars.txt ή από το αρχικό CSV
     */
    public void loadCarsFromFile() {
        File file = new File(carFilePath);

        if (!file.exists()) {
            loadInitialCarsFromCSV();
            saveCarsToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(carFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String plate = parts[1];
                    String brand = parts[2];
                    String model = parts[3];
                    int year = Integer.parseInt(parts[4]);
                    String color = parts[5];
                    boolean available = parts[6].equalsIgnoreCase("Διαθέσιμο");

                    Car car = new Car(id, plate, brand, model, year, color, available);
                    cars.add(car);
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση του cars.txt: " + e.getMessage());
        }
    }

    /**
     * Φορτώνει τα αρχικά αυτοκίνητα από το αρχείο initial_cars.csv
     */
    public void loadInitialCarsFromCSV() {
        /** Το csv αρχείο με τα αρχικά αυτοκίνητα */
        String initialFilePath = "data/vehicles_with_plates.csv";

        File file = new File(initialFilePath);

        if (!file.exists()) {
            System.out.println("Δεν βρέθηκε το αρχείο αρχικοποίησης: " + initialFilePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // εδώ αγνοούμε τις επικεφαλίδες
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String plate = parts[1];
                    String brand = parts[2];
                    String model = parts[3];
                    int year = Integer.parseInt(parts[4]);
                    String color = parts[5];
                    boolean available = parts[6].equalsIgnoreCase("Διαθέσιμο");

                    Car car = new Car(id, plate, brand, model, year, color, available);
                    cars.add(car);
                }
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση του αρχείου CSV: " + e.getMessage());
        }
    }
}
