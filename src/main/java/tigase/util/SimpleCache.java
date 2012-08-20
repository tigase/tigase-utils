/*
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2012 "Artur Hefczyc" <artur.hefczyc@tigase.org>
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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

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

  /**
   * Variable <code>log</code> is a class logger.
   */
  private static final Logger log = Logger.getLogger("tigase.util.SimpleCache");

	private static final long serialVersionUID = 1L;

	private SizedCache<K, CacheObject<V>> cache = null;

	private long cache_time = 1000;
	protected boolean cache_off = false;

	public SimpleCache(int maxSize, long time) {
		cache_time = time;
		// A quick way to switch all the cache off in Tigase.
		// Set the property: tigase.cache=false
		String cache_on = System.getProperty("tigase.cache");
		if (cache_on == null || cache_on.equals("true")
			|| cache_on.equals("1") || cache_on.equals("yes")
			|| cache_on.equals("on")) {
			cache_off = false;
			cache = new SizedCache<K, CacheObject<V>>(maxSize);
		} else {
			cache_off = true;
			log.warning("Tigase cache turned off.");
		}
	}

	public void removeOld(){
		if (cache_off) { return; }
		Iterator<Entry<K, CacheObject<V>>> iterator = cache.entrySet().iterator();
		while (iterator.hasNext()) {
			CacheObject<V> cob = iterator.next().getValue();
			if (cob.time + cache_time < System.currentTimeMillis()) {
				iterator.remove();
			}
		}
	}
	
	public V get(Object key) {
		if (cache_off) { return null; }

		CacheObject<V> cob = cache.get(key);
		if ((cob != null) && (cob.time+cache_time >= System.currentTimeMillis())) {
			return cob.data;
		} else {
			return null;
		}
	}

  public V put(K key, V value) {
		if (cache_off) { return null; }

		V result = get(key);
		CacheObject<V> cob = new CacheObject<V>();
		cob.time = System.currentTimeMillis();
		cob.data = value;
		cache.put(key, cob);
		return result;
	}

	public void clear() {
		if (cache_off) { return; }

		cache.clear();
	}

	public boolean containsKey(Object key) {
		if (cache_off) { return false; }

		return cache.containsKey(key);
	}

	public boolean containsValue(Object value) {
		if (cache_off) { return false; }

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
		if (cache_off) { return null; }

		Set<Map.Entry<K, CacheObject<V>>> cache_res = cache.entrySet();
		LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, CacheObject<V>> entry: cache_res) {
			result.put(entry.getKey(), entry.getValue().data);
		}
		return result;
	}

	public Set<Map.Entry<K,V>> entrySet() {
		if (cache_off) { return null; }

		return dataMap().entrySet();
	}

	public boolean equals(Object o) {
		if (cache_off) { return false; }

		return dataMap().equals(o);
	}

	public int hashCode() {
		if (cache_off) { return 0; }

		return dataMap().hashCode();
	}

	public boolean isEmpty() {
		if (cache_off) { return true; }

		return cache.isEmpty();
	}

	public Set<K>	keySet() {
		if (cache_off) { return null; }

		return cache.keySet();
	}

	public void putAll(Map<? extends K,? extends V> m) {
		if (cache_off) { return; }

		for (Map.Entry<? extends K,? extends V> entry: m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public V remove(Object key) {
		if (cache_off) { return null; }

		CacheObject<V> cache_res = cache.remove(key);
		if (cache_res != null) {
			return cache_res.data;
		}
		return null;
	}

	public int size() {
		if (cache_off) { return 0; }

		return cache.size();
	}

	public Collection<V> values() {
		if (cache_off) { return null; }

		return dataMap().values();
	}

} // SimpleCache
