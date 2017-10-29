/*
 * TwoHashBidiMapTest.java
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

package tigase.collections;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.Map.Entry;

public class TwoHashBidiMapTest
		extends TestCase {

	private BidiMap<String, Integer> map = new TwoHashBidiMap<String, Integer>();

	public void testClear() {
		map.clear();
		assertEquals(0, map.size());
	}

	public void testContainsKey() {
		assertTrue(map.containsKey("DWA"));
		map.remove("DWA");
		assertFalse(map.containsKey("DWA"));
	}

	public void testContainsValue() {
		assertTrue(map.containsValue(Integer.valueOf(2)));
		map.remove("DWA");
		assertFalse(map.containsValue(Integer.valueOf(2)));
	}

	public void testEntrySetIterator() {
		Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
		iterator.next();

		try {
			iterator.remove();
			fail("Removed? Why?");
		} catch (Exception e) {
		}
		assertEquals(8, map.size());
	}

	public void testGet() {
		assertEquals(Integer.valueOf(1), map.get("JEDEN"));
		assertEquals(Integer.valueOf(2), map.get("DWA"));
		assertEquals(Integer.valueOf(3), map.get("TRZY"));
		assertEquals(Integer.valueOf(4), map.get("CZTERY"));
		assertEquals(Integer.valueOf(5), map.get("PIEC"));
		assertEquals(Integer.valueOf(6), map.get("SZESC"));
		assertEquals(Integer.valueOf(7), map.get("SIEDEM"));
		assertEquals(Integer.valueOf(8), map.get("OSIEM"));
	}

	public void testGetKey() {
		assertEquals("JEDEN", map.getKey(Integer.valueOf(1)));
		assertEquals("DWA", map.getKey(Integer.valueOf(2)));
		assertEquals("TRZY", map.getKey(Integer.valueOf(3)));
		assertEquals("CZTERY", map.getKey(Integer.valueOf(4)));
		assertEquals("PIEC", map.getKey(Integer.valueOf(5)));
		assertEquals("SZESC", map.getKey(Integer.valueOf(6)));
		assertEquals("SIEDEM", map.getKey(Integer.valueOf(7)));
		assertEquals("OSIEM", map.getKey(Integer.valueOf(8)));
	}

	public void testPut() {
		map.put("JEDEN", Integer.valueOf(11));
		assertEquals(8, map.size());
		assertNull(map.getKey(Integer.valueOf(1)));
		assertEquals(Integer.valueOf(11), map.get("JEDEN"));
		map.put("JEDEN", Integer.valueOf(11));
	}

	public void testRemove() {
		assertEquals(Integer.valueOf(1), map.remove("JEDEN"));
		assertNull(map.get("JEDEN"));
		assertNull(map.getKey(Integer.valueOf(1)));
		assertEquals(7, map.size());
	}

	public void testRemoveValue() {
		assertEquals("DWA", map.removeValue(Integer.valueOf(2)));
		assertNull(map.get("DWA"));
		assertNull(map.getKey(Integer.valueOf(2)));
		assertEquals(7, map.size());
	}

	public void testSize() {
		assertEquals(8, map.size());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		map.clear();
		map.put("JEDEN", Integer.valueOf(1));
		map.put("DWA", Integer.valueOf(2));
		map.put("TRZY", Integer.valueOf(3));
		map.put("CZTERY", Integer.valueOf(4));
		map.put("PIEC", Integer.valueOf(5));
		map.put("SZESC", Integer.valueOf(6));
		map.put("SIEDEM", Integer.valueOf(7));
		map.put("OSIEM", Integer.valueOf(8));
	}

}
