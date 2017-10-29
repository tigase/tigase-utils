/*
 * ElementCriteria.java
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

//~--- non-JDK imports --------------------------------------------------------

import tigase.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

//~--- JDK imports ------------------------------------------------------------

/**
 * <p> Created: 2007-06-19 20:34:57 </p>
 *
 * @author bmalkow
 * @version $Rev$
 */
public class ElementCriteria
		implements Criteria {

	private Map<String, String> attrs = new TreeMap<String, String>();
	private String cdata = null;
	private String name;
	private Criteria nextCriteria;

	//~--- constructors ---------------------------------------------------------

	/**
	 * Method description
	 *
	 * @return
	 */
	public static final ElementCriteria empty() {
		return new ElementCriteria(null, null, null, null);
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * Method description
	 *
	 * @param name
	 *
	 * @return
	 */
	public static final ElementCriteria name(String name) {
		return new ElementCriteria(name, null, null, null);
	}

	/**
	 * Method description
	 *
	 * @param name
	 * @param xmlns
	 *
	 * @return
	 */
	public static final ElementCriteria name(String name, String xmlns) {
		return new ElementCriteria(name, null, new String[]{"xmlns"}, new String[]{xmlns});
	}

	/**
	 * Method description
	 *
	 * @param name
	 * @param cdata
	 * @param attNames
	 * @param attValues
	 *
	 * @return
	 */
	public static final ElementCriteria name(String name, String cdata, String[] attNames, String[] attValues) {
		return new ElementCriteria(name, cdata, attNames, attValues);
	}

	/**
	 * Method description
	 *
	 * @param name
	 * @param attNames
	 * @param attValues
	 *
	 * @return
	 */
	public static final ElementCriteria name(String name, String[] attNames, String[] attValues) {
		return new ElementCriteria(name, null, attNames, attValues);
	}

	/**
	 * Method description
	 *
	 * @param name
	 * @param type
	 *
	 * @return
	 */
	public static final ElementCriteria nameType(String name, String type) {
		return new ElementCriteria(name, null, new String[]{"type"}, new String[]{type});
	}

	/**
	 * Method description
	 *
	 * @param xmlns
	 *
	 * @return
	 */
	public static final ElementCriteria xmlns(String xmlns) {
		return new ElementCriteria(null, null, new String[]{"xmlns"}, new String[]{xmlns});
	}

	/**
	 * Constructs ...
	 *
	 * @param name
	 * @param cdata
	 * @param attname
	 * @param attValue
	 */
	public ElementCriteria(String name, String cdata, String[] attname, String[] attValue) {
		this.cdata = cdata;
		this.name = name;
		if ((attname != null) && (attValue != null)) {
			for (int i = 0; i < attname.length; i++) {
				attrs.put(attname[i], attValue[i]);
			}
		}
	}

	/**
	 * Method description
	 *
	 * @param criteria
	 *
	 * @return
	 */
	public Criteria add(Criteria criteria) {
		if (this.nextCriteria == null) {
			this.nextCriteria = criteria;
		} else {
			Criteria c = this.nextCriteria;

			c.add(criteria);
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tigase.criteria.Criteria#match(tigase.xml.Element)
	 */

	/**
	 * Method description
	 *
	 * @param element
	 *
	 * @return
	 */
	public boolean match(Element element) {
		if ((name != null) && (name != element.getName())) {
			return false;
		}
		if ((cdata != null) && ((element.getCData() == null) || !cdata.equals(element.getCData()))) {
			return false;
		}

		boolean result = true;

		for (Entry<String, String> entry : this.attrs.entrySet()) {
			String x = element.getAttributeStaticStr(entry.getKey());

			if ((x == null) || !x.equals(entry.getValue())) {
				result = false;

				break;
			}
		}
		if (this.nextCriteria != null) {
			List<Element> children = element.getChildren();
			boolean subres = false;

			if (children != null) {
				for (Element sub : children) {
					if (this.nextCriteria.match(sub)) {
						subres = true;

						break;
					}
				}
			}
			result &= subres;
		}

		return result;
	}
}

//~ Formatted in Tigase Code Convention on 13/02/20
