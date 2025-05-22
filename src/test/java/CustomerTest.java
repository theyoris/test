import api.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test για την κλάση Customer
 */
public class CustomerTest {

    private Customer customer;

    /**
     * Δημιουργούμε έναν Customer πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        customer = new Customer("123456789", "Γιώργος Βαγενάς", "6987654321");
    }

    @Test
    public void testGetAfm() {
        Assertions.assertEquals("123456789", customer.getAfm());
    }

    @Test
    public void testGetFullName() {
        Assertions.assertEquals("Γιώργος Βαγενάς", customer.getFullName());
    }

    @Test
    public void testGetPhoneNumber() {
        Assertions.assertEquals("6987654321", customer.getPhoneNumber());
    }
}