/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
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

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class CircularFifoQueueTest {

	@Test
	public void testQueue() {

		final AtomicInteger overflownElements = new AtomicInteger(0);

		final Consumer<Integer> integerConsumer = (overflow) -> overflownElements.incrementAndGet();

		CircularFifoQueue<Integer> q = new CircularFifoQueue<>(3, integerConsumer);

		q.offer(1);
		Assert.assertEquals(1, q.size());
		Assert.assertEquals(0, overflownElements.get());
		Assert.assertEquals(1, (int) q.peek());

		q.offer(2);
		Assert.assertEquals(2, q.size());
		Assert.assertEquals(0, overflownElements.get());
		Assert.assertEquals(1, (int) q.peek());

		q.offer(3);
		Assert.assertEquals(3, q.size());
		Assert.assertEquals(0, overflownElements.get());
		Assert.assertEquals(1, (int) q.peek());

		q.offer(4);
		Assert.assertEquals(3, q.size());
		Assert.assertEquals(1, overflownElements.get());
		Assert.assertEquals(2, (int) q.peek());


		overflownElements.set(0);

		boolean modified = q.setLimit(1);
		Assert.assertTrue(modified);
		Assert.assertEquals(1, q.size());
		Assert.assertEquals(2, overflownElements.get());
		Assert.assertEquals(4, (int) q.peek());

		overflownElements.set(0);
		modified = q.setLimit(3);
		Assert.assertFalse(modified);

		q.addAll(Arrays.asList(5,6,7,8,9,10));
		Assert.assertEquals(4, overflownElements.get());
		Assert.assertEquals(8, (int) q.peek());
	}

	@Test
	public void testNullConsumer() {
		final int length = 2;
		final CircularFifoQueue<Integer> numbers = new CircularFifoQueue<>(length, null);
		numbers.offer(1);
		numbers.offer(2);
		numbers.offer(3);
		numbers.offer(4);
		Assert.assertEquals(length, numbers.size());
	}

}