/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2014 "Tigase, Inc." <office@tigase.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License,
 * or (at your option) any later version.
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

/**
 * Class with string utilities, mostly helping with canonical representation of
 * String
 *
 * @author Wojciech Kapcia <wojciech.kapcia@tigase.org>
 */
public class StringUtilities {

	/**
	 * Split string into an Array of Strings using provided splitter, output array
	 * is interned
	 *
	 * @param in       String to be splited
	 * @param splitter delimiter of items
	 *
	 * @return Arrays of interned Strings
	 */
	public static String[] stringToArrayOfString( String in, String splitter ) {
		String[] result = null;
		if ( in != null ){
			result = in.split( splitter );
			for ( int i = 0 ; i < result.length ; ++i ) {
				result[i] = result[i].intern();
			}
		}
		return result;
	}

	/**
	 * Process all strings of an array using .intern()
	 *
	 * @param in array of Strings to be interned
	 *
	 * @return array of interned string
	 */
	public static String[] internStringArray( String[] in ) {
		String[] result = null;
		if ( in != null ){
			result = new String[ in.length ];
			for ( int i = 0 ; i < result.length ; ++i ) {
				result[i] = in[i].intern();
			}
		}
		return result;
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

}
