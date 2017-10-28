/*
 * SystemConsole.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */

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
