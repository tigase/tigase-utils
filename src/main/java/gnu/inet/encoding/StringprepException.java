/**
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
package gnu.inet.encoding;

/**
 * Exception handling for StringPrep class.
 */
public class StringprepException
		extends Exception {

	private static final long serialVersionUID = 1L;
	public static String BIDI_BOTHRAL = "Contains both R and AL code points.";
	public static String BIDI_LTRAL = "Leading and trailing code points not both R or AL.";
	public static String CONTAINS_PROHIBITED = "Contains prohibited code points.";
	public static String CONTAINS_UNASSIGNED = "Contains unassigned code points.";

	public StringprepException(String m) {
		super(m);
	}
}