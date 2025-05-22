package tests;

import api.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Test για την κλάση Rental
 */
public class RentalTest {

    private Rental rental;
    private Car car;
    private Customer customer;
    private Employee employee;

    /**
     * Δημιουργούμε μια Rental πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        car = new Car(2, "ΙΚΧ3482", "Honda", "Civic", 2017, "Κόκκινο", true);
        customer = new Customer("987654321", "Ιωάννης Βαγενάς", "6945123456");
        employee = new Employee("admin1", "password1");
        rental = new Rental(5, car, customer, LocalDate.now(), LocalDate.now().plusDays(7), employee);
    }

    @Test
    public void testGetRentalId() {
        Assertions.assertEquals(5, rental.getRentalId());
    }

    @Test
    public void testGetCar() {
        Assertions.assertEquals(car, rental.getCar());
    }

    @Test
    public void testGetCustomer() {
        Assertions.assertEquals(customer, rental.getCustomer());
    }

    @Test
    public void testGetEmployee() {
        Assertions.assertEquals(employee, rental.getEmployee());
    }

    @Test
    public void testIsActive() {
        Assertions.assertTrue(rental.isActive());
        rental.setActive(false);
        Assertions.assertFalse(rental.isActive());
    }
}
