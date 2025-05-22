import api.entities.Employee;
import api.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test για την κλάση EmployeeService
 */
public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private Employee employee;

    /**
     * Δημιουργούμε ένα EmployeeService και έναν Employee πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService();
        employee = new Employee("employee_1", "mypassword123");
    }

    @Test
    public void testAddAndFindByUsername() {
        employeeService.addEmployee(employee);
        Employee found = employeeService.findByUsername("employee_1");
        Assertions.assertEquals(employee, found);
    }

    @Test
    public void testFindByUsernameNotFound() {
        Employee found = employeeService.findByUsername("random_username");
        Assertions.assertNull(found);
    }
}