package tigase.collections;

import java.util.Map;

public interface BidiMap<K, V> extends Map<K, V> {

	K getKey(Object value);

	K removeValue(Object value);

}
