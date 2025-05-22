import api.entities.*;
import api.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Test για την κλάση RentalService
 */
public class RentalServiceTest {

    private CarService carService;
    private CustomerService customerService;
    private EmployeeService employeeService;
    private RentalService rentalService;

    private Car car;
    private Customer customer;
    private Employee employee;

    /**
     * Δημιουργούμε ένα RentalService και αντικείμενα πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        carService = new CarService();
        customerService = new CustomerService();
        employeeService = new EmployeeService();

        carService.getAllCars().clear();
        customerService.getAllCustomers().clear();
        employeeService.getAllEmployees().clear();

        car = new Car(15, "ΛΒΣ7719", "Peugeot", "208", 2022, "Μαύρο", true);
        customer = new Customer("555444333", "Δήμητρα Περιστεριώτη", "6959876543");
        employee = new Employee("dimitra_p", "pass321");

        carService.addCar(car);
        customerService.addCustomer(customer);
        employeeService.addEmployee(employee);

        rentalService = new RentalService(carService, customerService, employeeService);
        rentalService.getAllRentals().clear();
    }

    @Test
    public void testRentAndReturnCar() {
        rentalService.rentCar(car, customer, LocalDate.now(), LocalDate.now().plusDays(3), employee);
        Assertions.assertFalse(car.isAvailable());

        List<Rental> rentals = rentalService.getAllRentals();
        Assertions.assertEquals(1, rentals.size());
        Rental rental = rentals.get(0);

        rentalService.returnCar(rental);
        Assertions.assertTrue(car.isAvailable());
        Assertions.assertFalse(rental.isActive());
    }

    @Test
    public void testGetRentalsByCustomerAfm() {
        rentalService.rentCar(car, customer, LocalDate.now(), LocalDate.now().plusDays(3), employee);
        List<Rental> results = rentalService.getRentalsByCustomerAfm("555444333");
        Assertions.assertEquals(1, results.size());
    }

    @Test
    public void testGetRentalsByCarPlate() {
        rentalService.rentCar(car, customer, LocalDate.now(), LocalDate.now().plusDays(3), employee);
        List<Rental> results = rentalService.getRentalsByCarPlate("ΛΒΣ7719");
        Assertions.assertEquals(1, results.size());
    }
}
