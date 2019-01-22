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
package tigase.util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * In a few cases classes have to be kept in <code>Set</code>. This <code>Comparator</code> implementation has been
 * created to return proper value for <code>compare</code> method and to make it possible to store classes in any
 * <code>Set</code>.
 * <br>
 * <p> Created: Sat Oct  9 22:27:54 2004 </p>
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */

public class ClassComparator
		implements Comparator<Class>, Serializable {

	private static final long serialVersionUID = 1L;

	// Implementation of java.util.Comparator

	/**
	 * Method <code>compare</code> is used to perform
	 *
	 * @param c1 an <code>Object</code> value
	 * @param c2 an <code>Object</code> value
	 *
	 * @return an <code>int</code> value
	 */
	//  @Override
	public int compare(Class c1, Class c2) {
		return c1.getName().compareTo(c2.getName());
	}

}// ClassComparator