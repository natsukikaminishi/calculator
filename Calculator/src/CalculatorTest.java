import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class CalculatorTest {
	ByteArrayOutputStream out;
	
	void in(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
	}
	
	void out() {
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}
	@Test
	void testMain() {
		in("5 + 1");
		out() ;
		Calculator.main(null);
		assertEquals("6.0", out.toString());
	}

}
