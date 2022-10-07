.. java:import:: java.util.concurrent LinkedBlockingQueue

.. java:import:: java.util.function Consumer

CircularFifoQueue
=================

.. java:package:: tigase.collections
   :noindex:

.. java:type:: public class CircularFifoQueue<E> extends AbstractQueue<E>

Constructors
------------
CircularFifoQueue
^^^^^^^^^^^^^^^^^

.. java:constructor:: public CircularFifoQueue(int maxEntries, Consumer<E> overflowConsumer)
   :outertype: CircularFifoQueue

Methods
-------
add
^^^

.. java:method:: @Override public boolean add(E e)
   :outertype: CircularFifoQueue

addAll
^^^^^^

.. java:method:: @Override public boolean addAll(Collection<? extends E> c)
   :outertype: CircularFifoQueue

iterator
^^^^^^^^

.. java:method:: @Override public Iterator<E> iterator()
   :outertype: CircularFifoQueue

limit
^^^^^

.. java:method:: public int limit()
   :outertype: CircularFifoQueue

offer
^^^^^

.. java:method:: @Override public boolean offer(E element)
   :outertype: CircularFifoQueue

peek
^^^^

.. java:method:: @Override public E peek()
   :outertype: CircularFifoQueue

poll
^^^^

.. java:method:: @Override public E poll()
   :outertype: CircularFifoQueue

setLimit
^^^^^^^^

.. java:method:: public boolean setLimit(int limit)
   :outertype: CircularFifoQueue

size
^^^^

.. java:method:: @Override public int size()
   :outertype: CircularFifoQueue

