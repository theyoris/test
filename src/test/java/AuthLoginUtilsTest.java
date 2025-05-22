import api.authentication.LoginManager;
import api.entities.Employee;
import api.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test για τις κλάσεις CryptographyUtils και LoginManager
 */
public class AuthLoginUtilsTest {

    private EmployeeService employeeService;
    private LoginManager loginManager;

    /**
     * Αρχικοποίηση πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService();
        employeeService.addEmployee(new Employee("admin1", "password1"));
        employeeService.addEmployee(new Employee("user1", "mypass"));
        loginManager = new LoginManager(employeeService);
    }

    @Test
    public void testCheckLoginValid() {
        boolean result = loginManager.checkLogin("user1", "mypass");
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckLoginInvalidUser() {
        boolean result = loginManager.checkLogin("no_user", "password");
        Assertions.assertFalse(result);
    }

    @Test
    public void testCheckLoginWrongPassword() {
        boolean result = loginManager.checkLogin("admin1", "wrongpassword");
        Assertions.assertFalse(result);
    }

    @Test
    public void testFindByUsername() {
        Employee emp = loginManager.findByUsername("admin1");
        Assertions.assertNotNull(emp);
        Assertions.assertEquals("admin1", emp.getUsername());

        Employee notFound = loginManager.findByUsername("unknown");
        Assertions.assertNull(notFound);
    }
}