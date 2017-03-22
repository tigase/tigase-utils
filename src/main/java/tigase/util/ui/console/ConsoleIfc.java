package tigase.util.ui.console;

public interface ConsoleIfc {

	public String readLine();

	public String readLine(String label);

	public char[] readPassword(String label);

	public void writeLine(String format, Object... args);

	public void writeLine(Object obj);
}
