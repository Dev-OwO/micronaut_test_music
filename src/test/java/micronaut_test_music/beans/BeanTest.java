package micronaut_test_music.beans;

import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import micronaut_test_music.bean.Vehicle;

public class BeanTest {

	@Test
	public void contextTest() {
		try(ApplicationContext context = ApplicationContext.run()) {
			Vehicle vehicle = context.getBean(Vehicle.class);
			System.out.println(vehicle.start());
		}
	}
}
