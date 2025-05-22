import api.authentication.LoginManager;
import api.services.CustomerService;
import api.services.RentalService;
import gui.LoginFrame;
import api.services.CarService;
import api.services.EmployeeService;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        CarService carService = new CarService();
        CustomerService customerService = new CustomerService();
        LoginManager loginManager = new LoginManager(employeeService);

        loginManager.initializeDefaultUsers();
        loginManager.loadUsersFromFile();

        RentalService rentalService = new RentalService(carService, customerService, employeeService);

        new LoginFrame(loginManager, carService, customerService, rentalService).setVisible(true);

    }
}
