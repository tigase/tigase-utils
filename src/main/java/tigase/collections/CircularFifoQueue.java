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
package tigase.collections;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class CircularFifoQueue<E>
		extends AbstractQueue<E> {

	private final Optional<Consumer<E>> consumer;
	private final Queue<E> queue;
	private int limit;

	public CircularFifoQueue(final int maxEntries, Consumer<E> overflowConsumer) {
		this.limit = maxEntries;
		this.queue = new LinkedBlockingQueue<>();
		this.consumer = Optional.ofNullable(overflowConsumer);
	}

	@Override
	public boolean add(E e) {
		boolean result = queue.add(e);
		result |= pruneQueue();
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean result = queue.addAll(c);
		result |= pruneQueue();
		return result;
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	public int limit() {
		return limit;
	}

	@Override
	public boolean offer(E element) {
		boolean result = queue.offer(element);
		result |= pruneQueue();
		return result;
	}

	@Override
	public E peek() {
		return queue.peek();
	}

	@Override
	public E poll() {
		return queue.poll();
	}

	public boolean setLimit(int limit) {
		this.limit = limit;
		return pruneQueue();
	}

	@Override
	public int size() {
		return queue.size();
	}

	private boolean pruneQueue() {
		if (size() <= limit) {
			return false;
		} else {
			while (size() > limit) {
				E tmp = queue.poll();
				consumer.ifPresent(c -> c.accept(tmp));
			}
			return true;
		}
	}
}


