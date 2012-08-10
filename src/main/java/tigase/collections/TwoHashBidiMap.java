package tigase.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TwoHashBidiMap<K, V> implements BidiMap<K, V> {

	private final Map<K, V> keyValueMap = new ConcurrentHashMap<K, V>();

	private final Map<V, K> valueKeyMap = new ConcurrentHashMap<V, K>();

	@Override
	public void clear() {
		keyValueMap.clear();
		valueKeyMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return keyValueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return valueKeyMap.containsKey(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return Collections.unmodifiableSet(keyValueMap.entrySet());
	}

	@Override
	public V get(Object key) {
		if (key == null)
			return null;
		return keyValueMap.get(key);
	}

	@Override
	public K getKey(Object value) {
		return this.valueKeyMap.get(value);
	}

	@Override
	public boolean isEmpty() {
		return keyValueMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return Collections.unmodifiableSet(keyValueMap.keySet());
	}

	@Override
	public V put(K key, V value) {
		remove(key);
		removeValue(value);

		this.valueKeyMap.put(value, key);
		return this.keyValueMap.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (java.util.Map.Entry<? extends K, ? extends V> en : m.entrySet()) {
			put(en.getKey(), en.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		V v = this.keyValueMap.remove(key);
		if (v != null)
			this.valueKeyMap.remove(v);
		return v;
	}

	@Override
	public K removeValue(Object value) {
		K k = this.valueKeyMap.remove(value);
		if (k != null)
			this.keyValueMap.remove(k);
		return k;
	}

	@Override
	public int size() {
		int kvs = keyValueMap.size();
		int vks = valueKeyMap.size();
		if (vks != kvs) {
			throw new Error("Errors in " + this.getClass().getName() + ". Desyncronized!");
		}
		return kvs;
	}

	@Override
	public Collection<V> values() {
		return Collections.unmodifiableCollection(keyValueMap.values());
	}

}
