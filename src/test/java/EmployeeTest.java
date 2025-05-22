import api.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test για την κλάση Employee
 */
public class EmployeeTest {

    private Employee employee;

    /**
     * Δημιουργούμε έναν Employee πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        employee = new Employee("admin", "pass");
    }

    @Test
    public void testGetUsername() {
        Assertions.assertEquals("admin", employee.getUsername());
    }

    @Test
    public void testGetPassword() {
        Assertions.assertEquals("pass", employee.getPassword());
    }
}
