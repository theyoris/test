import api.entities.Customer;
import api.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test για την κλάση CustomerService
 */
public class CustomerServiceTest {

    private CustomerService customerService;
    private Customer customer;

    /**
     * Δημιουργούμε ένα CustomerService και έναν Customer πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        customerService = new CustomerService();
        customerService.getAllCustomers().clear();
        customer = new Customer("999888777", "Αντώνης Νικολόπουλος", "6933344556");
    }

    @Test
    public void testAddAndFindCustomerByAfm() {
        customerService.addCustomer(customer);
        Customer found = customerService.findCustomerByAfm("999888777");
        Assertions.assertEquals(customer, found);
    }

    @Test
    public void testSearchCustomers() {
        customerService.addCustomer(customer);
        List<Customer> results = customerService.searchCustomers("999888777", null, null);
        Assertions.assertTrue(results.contains(customer));
    }
}