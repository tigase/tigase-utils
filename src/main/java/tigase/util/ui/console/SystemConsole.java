package tigase.util.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Simple SystemConsole class handling user input/output
 * from command-line, including labels and secure password reading
 */
public class SystemConsole
		implements ConsoleIfc {

	private final BufferedReader in;
	private final PrintWriter out;

	public SystemConsole() {
		if (System.console() != null) {
			in = null;
			out = System.console().writer();
		} else {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out, true);
		}
	}

	public String readLine() {
		if (in == null) {
			return System.console().readLine();
		}
		try {
			return in.readLine();
		} catch (IOException ex) {
			return null;
		}
	}

	public String readLine(String label) {
		if (in == null) {
			return System.console().readLine("%s", label);
		}
		try {
			out.printf("%s", label);
			return in.readLine();
		} catch (IOException ex) {
			return null;
		}
	}

	public char[] readPassword(String label) {
		if (in == null) {
			return System.console().readPassword("%s", label);
		}
		String line = readLine(label);
		return line.toCharArray();
	}

	public void writeLine(String format, Object... args) {
		out.printf(format + "\n", args);
	}

	public void writeLine(Object obj) {
		out.println(obj);
	}
}
