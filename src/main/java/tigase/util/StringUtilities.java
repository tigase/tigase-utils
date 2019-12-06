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
package tigase.util;

import java.util.Arrays;

/**
 * Class with string utilities, mostly helping with canonical representation of String
 *
 * @author wojtek
 */
public class StringUtilities {

	private static final char[] WHITE_CHARS = {'\t', '\n', '\r', ' '};

	public enum JUSTIFY {
		LEFT,
		CENTRE,
		RIGHT
	}

	public static String convertNonPrintableCharactersToLiterals(final String input) {
		return convertNonPrintableCharactersToLiterals(input, false);
	}

	public static String convertNonPrintableCharactersToLiterals(final String input, boolean maintainWhitespace) {
		StringBuilder output = new StringBuilder();

		for (char c : input.toCharArray()) {
			switch (Character.getType(c)) {
				case Character.PRIVATE_USE:
				case Character.FORMAT:
				case Character.UNASSIGNED:
				case Character.SURROGATE:
					output.append("\\u").append(String.format("%04X", (int) c));
					break;
				case Character.CONTROL:
					if (maintainWhitespace && Arrays.binarySearch(WHITE_CHARS, c) >= 0) {
						output.append(c);
					} else {
						output.append("\\u").append(String.format("%04X", (int) c));
					}
					break;
				default:
					output.append(c);
					break;
			}
		}
		return output.toString();
	}

	/**
	 * Concatenate all elements of input array inserting separator between each
	 *
	 * @param arr an array to be concatenated
	 * @param separator to be inserted between each element of array
	 *
	 * @return string representation of the array
	 */
	public static String intArrayToString(int[] arr, String separator) {
		if (arr == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			buf.append(arr[i]);
		}
		return buf.toString();
	}

	/**
	 * Process all strings of an array using .intern()
	 *
	 * @param in array of Strings to be interned
	 *
	 * @return array of interned string
	 */
	public static String[] internStringArray(String[] in) {
		String[] result = null;
		if (in != null) {
			result = new String[in.length];
			for (int i = 0; i < result.length; ++i) {
				result[i] = in[i].intern();
			}
		}
		return result;
	}

	public static StringBuilder padString(StringBuilder sb, String text, int width) {
		return padString(sb, text, JUSTIFY.LEFT, width, ' ', null, null);
	}

	public static StringBuilder padString(StringBuilder sb, String text, int width, String leftBracket,
										  String rightBracket) {
		return padString(sb, text, JUSTIFY.LEFT, width, ' ', leftBracket, rightBracket);
	}

	public static StringBuilder padString(StringBuilder sb, String text, JUSTIFY justify, int width, char padChar,
										  String leftBracket, String rightBracket) {
		if (leftBracket != null && !leftBracket.isEmpty()) {
			sb.append(leftBracket);
		}
		int textLength = text.length();
		int padSize = width - textLength;
		switch (justify) {
			case LEFT:
				sb.append(text);
				while ((padSize--) > 0) {
					sb.append(padChar);
				}
				break;
			case RIGHT:
				while ((padSize--) > 0) {
					sb.append(padChar);
				}
				sb.append(text);
				break;
			case CENTRE:
				int leftPad = (int) Math.floor(padSize / 2.0);
				while ((leftPad--) > 0) {
					sb.append(padChar);
				}
				sb.append(text);
				int rightPad = (int) Math.ceil(padSize / 2.0);
				while ((rightPad--) > 0) {
					sb.append(padChar);
				}
				break;
		}
		if (rightBracket != null && !rightBracket.isEmpty()) {
			sb.append(rightBracket);
		}

		return sb;
	}

	public static StringBuilder padStringToColumn(StringBuilder sb, String text, JUSTIFY justify, int column,
												  char padChar, String leftBracket, String rightBracket) {
		return padString(sb, text, justify, column - sb.length(), padChar, leftBracket, rightBracket);
	}

	/**
	 * Concatenate all elements of input array inserting separator between each
	 *
	 * @param arr an array to be concatenated
	 * @param separator to be inserted between each element of array
	 *
	 * @return string representation of the array
	 */
	public static String stringArrayToString(String[] arr, String separator) {
		if (arr == null) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			buf.append(arr[i]);
		}
		return buf.toString();
	}

	/**
	 * Split string into an Array of Strings using provided splitter, output array is interned
	 *
	 * @param in String to be splited
	 * @param splitter delimiter of items
	 *
	 * @return Arrays of interned Strings
	 */
	public static String[] stringToArrayOfString(String in, String splitter) {
		String[] result = null;
		if (in != null) {
			result = in.split(splitter);
			for (int i = 0; i < result.length; ++i) {
				result[i] = result[i].intern();
			}
		}
		return result;
	}

}
