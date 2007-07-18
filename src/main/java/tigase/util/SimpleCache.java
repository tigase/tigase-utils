/*
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2007 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */
package tigase.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Describe class SimpleCache here.
 *
 *
 * Created: Sun Nov 26 19:55:22 2006
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class SimpleCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private int maxCacheSize = 1000;

	public SimpleCache(int maxSize) {
		super(10, 0.75f, true);
		maxCacheSize = maxSize;
	}

	public V remove(Object key) {
		V val = super.remove(key);
		String strk = key.toString();
		Iterator<K> ks = keySet().iterator();
		while (ks.hasNext()) {
			String k = ks.next().toString();
			if (k.startsWith(strk)) {
				ks.remove();
			} // end of if (k.startsWith(strk))
		} // end of while (ks.hasNext())
		return val;
	}

	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > maxCacheSize;
	}

} // SimpleCache