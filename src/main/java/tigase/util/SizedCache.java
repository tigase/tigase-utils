/*  Tigase Project
 *  Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */
package tigase.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Describe class SizedCache here.
 *
 *
 * Created: Mon Mar  3 15:16:52 2008
 *
 * @param <K>
 * @param <V> 
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class SizedCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private int maxCacheSize = 1000;

	public SizedCache(int maxSize) {
		super(maxSize, 0.9f, true);
		maxCacheSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > maxCacheSize;
	}

}
