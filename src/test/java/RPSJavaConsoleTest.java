import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RPSJavaConsoleTest {

	@ParameterizedTest
	@ValueSource(strings = { "ra", "a", "ble was", "s " })
	void invalidInputTerminatesPlay(String input) {
		// Create a ByteArrayOutputStream to capture the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		// Redirect standard output to the PrintStream
		PrintStream oldOut = System.out;
		System.setOut(ps);

		// Set-up test stdin
		InputStream oldIn = System.in;
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		RPSJavaConsole.main(null);

		System.setIn(oldIn);
		System.setOut(oldOut);

		assertEquals("Invalid Input! Terminating Play...", baos.toString());
	}

}
