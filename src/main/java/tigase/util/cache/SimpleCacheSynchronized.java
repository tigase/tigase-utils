/*
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
package tigase.util.cache;

public class SimpleCacheSynchronized<K, V> {

	private final SimpleCache<K,V> cache;

	public SimpleCacheSynchronized(int maxSize, long time) {
		this.cache = new SimpleCache<>(maxSize, time);
	}

	public V get(K key) {
		if (cache.cache_off) {
			return null;
		}
		synchronized (cache) {
			return cache.get(key);
		}
	}

	public V put(K key, V value) {
		if (cache.cache_off) {
			return null;
		}
		synchronized (cache) {
			return cache.put(key, value);
		}
	}

	public V remove(K key) {
		if (cache.cache_off) {
			return null;
		}
		synchronized (cache) {
			return cache.remove(key);
		}
	}
	
}
