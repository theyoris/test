import api.entities.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test για την κλάση Car
 */
public class CarTest {

    private Car car;

    /**
     * Δημιουργούμε ένα Car πριν κάθε test
     */
    @BeforeEach
    public void setUp() {
        car = new Car(1, "ΑΒΥ1231", "Toyota", "Corolla", 2018, "Μπλε", true);
    }

    @Test
    public void testGetBrand() {
        Assertions.assertEquals("Toyota", car.getBrand());
    }

    @Test
    public void testIsAvailable() {
        Assertions.assertTrue(car.isAvailable());
        car.setAvailable(false);
        Assertions.assertFalse(car.isAvailable());
    }

    @Test
    public void testGetLicensePlate() {
        Assertions.assertEquals("ΑΒΥ1231", car.getLicensePlate());
    }
}
