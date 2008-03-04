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
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Describe class SimpleCache here.
 *
 *
 * Created: Sun Nov 26 19:55:22 2006
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class SimpleCache<K, V> implements Map<K, V> {

	private static final long serialVersionUID = 1L;

	private SizedCache<K, CacheObject<V>> cache = null;

	private long cache_time = 1000;

	public SimpleCache(int maxSize, long time) {
		cache_time = time;
		cache = new SizedCache<K, CacheObject<V>>(maxSize);
	}

	public V get(Object key) {
		CacheObject<V> cob = cache.get(key);
		if ((cob != null) && (cob.time+cache_time >= System.currentTimeMillis())) {
			return cob.data;
		} else {
			return null;
		}
	}

  public V put(K key, V value) {
		V result = get(key);
		CacheObject<V> cob = new CacheObject<V>();
		cob.time = System.currentTimeMillis();
		cob.data = value;
		cache.put(key, cob);
		return result;
	}

	public void clear() {
		cache.clear();
	}

	public boolean containsKey(Object key) {
		return cache.containsKey(key);
	}

	public boolean containsValue(Object value) {
		if (value==null) {
			for (CacheObject<V> v: cache.values())
				if (v.data==null)
					return true;
		} else {
			for (CacheObject<V> v: cache.values())
				if (value.equals(v.data))
					return true;
		}
		return false;
	}

	protected Map<K, V> dataMap() {
		Set<Map.Entry<K, CacheObject<V>>> cache_res = cache.entrySet();
		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, CacheObject<V>> entry: cache_res) {
			result.put(entry.getKey(), entry.getValue().data);
		}
		return result;
	}

	public Set<Map.Entry<K,V>> entrySet() {
		return dataMap().entrySet();
	}

	public boolean equals(Object o) {
		return dataMap().equals(o);
	}

	public int hashCode() {
		return dataMap().hashCode();
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

	public Set<K>	keySet() {
		return cache.keySet();
	}

	public void putAll(Map<? extends K,? extends V> m) {
		for (Map.Entry<? extends K,? extends V> entry: m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public V remove(Object key) {
		CacheObject<V> cache_res = cache.remove(key);
		if (cache_res != null) {
			return cache_res.data;
		}
		return null;
	}

	public int size() {
		return cache.size();
	}

	public Collection<V> values() {
		return dataMap().values();
	}

// 	public V remove(Object key) {
// 		CacheObject<V> val = super.remove(key);
// 		String strk = key.toString();
// 		Iterator<K> ks = keySet().iterator();
// 		while (ks.hasNext()) {
// 			String k = ks.next().toString();
// 			if (k.startsWith(strk)) {
// 				ks.remove();
// 			} // end of if (k.startsWith(strk))
// 		} // end of while (ks.hasNext())
// 		return val.data;
// 	}

} // SimpleCache
