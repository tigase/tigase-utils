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
 * Exception handling for IDNA class.
 */
public class IDNAException
		extends Exception {

	private static final long serialVersionUID = 1L;
	public static String CONTAINS_ACE_PREFIX = "ACE prefix (xn--) not allowed.";
	public static String CONTAINS_HYPHEN = "Leading or trailing hyphen not allowed.";
	public static String CONTAINS_NON_LDH = "Contains non-LDH characters.";
	public static String TOO_LONG = "String too long.";

	public IDNAException(String m) {
		super(m);
	}

	public IDNAException(StringprepException e) {
		super(e);
	}

	public IDNAException(PunycodeException e) {
		super(e);
	}
}
