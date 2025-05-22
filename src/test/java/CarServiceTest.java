import api.entities.Car;
import api.services.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test για την κλάση CarService
 */
public class CarServiceTest {

    private CarService carService;
    private Car car;

    /**
     * Δημιουργούμε ένα CarService και ένα Car πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        carService = new CarService();
        carService.getAllCars().clear();
        car = new Car(7, "ΥΡΝ5721", "Mazda", "CX-5", 2019, "Λευκό", true);
    }


    @Test
    public void testAddAndFindCarById() {
        carService.addCar(car);
        Car found = carService.findCarById(7);
        Assertions.assertEquals(car, found);
    }

    @Test
    public void testFindCarByPlate() {
        carService.addCar(car);
        Car found = carService.findCarByPlate("ΥΡΝ5721");
        Assertions.assertEquals(car, found);
    }

    @Test
    public void testGetAvailableCars() {
        carService.addCar(car);
        List<Car> available = carService.getAvailableCars();
        Assertions.assertTrue(available.contains(car));

        car.setAvailable(false);
        carService.updateCar(car);
        available = carService.getAvailableCars();
        Assertions.assertFalse(available.contains(car));
    }
}
