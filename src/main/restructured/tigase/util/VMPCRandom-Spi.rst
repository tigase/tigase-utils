.. java:import:: java.security SecureRandom

.. java:import:: java.security SecureRandomSpi

VMPCRandom.Spi
==============

.. java:package:: tigase.util
   :noindex:

.. java:type:: public static class Spi extends SecureRandomSpi
   :outertype: VMPCRandom

Constructors
------------
Spi
^^^

.. java:constructor:: public Spi()
   :outertype: VMPCRandom.Spi

Methods
-------
engineGenerateSeed
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override protected byte[] engineGenerateSeed(int numBytes)
   :outertype: VMPCRandom.Spi

engineNextBytes
^^^^^^^^^^^^^^^

.. java:method:: @Override protected void engineNextBytes(byte[] bytes)
   :outertype: VMPCRandom.Spi

engineSetSeed
^^^^^^^^^^^^^

.. java:method:: @Override protected void engineSetSeed(byte[] seed)
   :outertype: VMPCRandom.Spi

