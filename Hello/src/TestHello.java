import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestHello {
	Hello h = new Hello();

	@Test
	public void testPrintHello() {
		assertEquals("Hello World!" ,h.printHello());
	}
}