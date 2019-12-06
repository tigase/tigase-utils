/*
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
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

/**
 * Codes to control colours in compatible terminals,
 * based on https://en.wikipedia.org/wiki/ANSI_escape_code#3/4_bit
 */
public enum AnsiColor {
	RESET("\033[0m"),

	// Regular
	BLACK("\033[0;30m"),
	RED("\033[0;31m"),
	GREEN("\033[0;32m"),
	YELLOW("\033[0;33m"),
	BLUE("\033[0;34m"),
	MAGENTA("\033[0;35m"),
	CYAN("\033[0;36m"),
	WHITE("\033[0;37m"),

	// Bright
	BLACK_BRIGHT("\033[0;90m"),
	RED_BRIGHT("\033[0;91m"),
	GREEN_BRIGHT("\033[0;92m"),
	YELLOW_BRIGHT("\033[0;93m"),
	BLUE_BRIGHT("\033[0;94m"),
	MAGENTA_BRIGHT("\033[0;95m"),
	CYAN_BRIGHT("\033[0;96m"),
	WHITE_BRIGHT("\033[0;97m"),

	// Bold
	BLACK_BOLD("\033[1;30m"),
	RED_BOLD("\033[1;31m"),
	GREEN_BOLD("\033[1;32m"),
	YELLOW_BOLD("\033[1;33m"),
	BLUE_BOLD("\033[1;34m"),
	MAGENTA_BOLD("\033[1;35m"),
	CYAN_BOLD("\033[1;36m"),
	WHITE_BOLD("\033[1;37m"),

	// Bold High Intensity
	BLACK_BOLD_BRIGHT("\033[1;90m"),
	RED_BOLD_BRIGHT("\033[1;91m"),
	GREEN_BOLD_BRIGHT("\033[1;92m"),
	YELLOW_BOLD_BRIGHT("\033[1;93m"),
	BLUE_BOLD_BRIGHT("\033[1;94m"),
	MAGENTA_BOLD_BRIGHT("\033[1;95m"),
	CYAN_BOLD_BRIGHT("\033[1;96m"),
	WHITE_BOLD_BRIGHT("\033[1;97m");

	private static boolean isCompatible = !System.getProperty("os.name").toLowerCase().contains("win");
	private final String code;

	public static boolean isCompatible() {
		return isCompatible;
	}

	AnsiColor(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
