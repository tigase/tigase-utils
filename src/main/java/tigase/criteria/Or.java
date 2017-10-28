/*
 * Or.java
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
package tigase.criteria;

import tigase.xml.Element;

/**
 * 
 * <p>
 * Created: 2007-06-20 09:32:29
 * </p>
 * 
 * @author bmalkow
 * @version $Rev$
 */
public class Or implements Criteria {

	private Criteria[] crits;

	public Or(Criteria... criteria) {
		this.crits = criteria;
	}

	public Criteria add(Criteria criteria) {
		throw new RuntimeException("Or.add() is not implemented!");
	}

	public boolean match(Element element) {
		for (Criteria c : this.crits) {
			if (c.match(element)) {
				return true;
			}
		}
		return false;
	}

}