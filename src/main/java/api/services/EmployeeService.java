package api.services;

import api.entities.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Κρατάει τους υπαλλήλους της εταιρίας στη μνήμη
 */
public class EmployeeService {

    /** Η λίστα με όλους τους υπαλλήλους */
    private final List<Employee> employees;

    /**
     * Constructor που αρχικοποιεί τη λίστα
     */
    public EmployeeService() {
        employees = new ArrayList<>();
    }

    /**
     * Επιστρέφει όλους τους υπαλλήλους
     * @return employees
     */
    public List<Employee> getAllEmployees() {
        return employees;
    }

    /**
     * Βάζει έναν υπάλληλο στη λίστα
     * @param employee employee
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Βρίσκει έναν υπάλληλο με βάση το username
     * @param username username
     * @return employee
     */
    public Employee findByUsername(String username) {
        for (Employee emp : employees) {
            if (emp.getUsername().equals(username)) {
                return emp;
            }
        }
        return null;
    }
}
