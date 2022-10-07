.. java:import:: java.io IOException

.. java:import:: java.nio ByteBuffer

.. java:import:: java.nio CharBuffer

.. java:import:: java.nio.charset CharacterCodingException

.. java:import:: java.nio.charset Charset

.. java:import:: java.nio.charset CharsetDecoder

.. java:import:: java.nio.charset CharsetEncoder

.. java:import:: java.util Arrays

.. java:import:: java.util Random

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

.. java:import:: java.util.zip DataFormatException

.. java:import:: java.util.zip Deflater

.. java:import:: java.util.zip Inflater

ZLibWrapper
===========

.. java:package:: tigase.util
   :noindex:

.. java:type:: public class ZLibWrapper

   This is a warpper for java.util.zip package and Deflater/Inflater classes specifically. This implementation allows for easy interaction between Deflater/Inflater and java.nio API which operates on ByteBuffer data. It also does some tricky stuff to flush Deflater without reseting it and allow a better compression ration on the data.

   There are a few convenience methods allowing to directly compress String to ByteBuffer and other way around - from ByteBuffer to String decompression. For these methods data are assumed to be UTF-8 character String.  Created: Jul 30, 2009 11:46:55 AM

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

Fields
------
COMPRESSED_BUFF_SIZE
^^^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int COMPRESSED_BUFF_SIZE
   :outertype: ZLibWrapper

DECOMPRESSED_BUFF_SIZE
^^^^^^^^^^^^^^^^^^^^^^

.. java:field:: public static final int DECOMPRESSED_BUFF_SIZE
   :outertype: ZLibWrapper

Constructors
------------
ZLibWrapper
^^^^^^^^^^^

.. java:constructor:: public ZLibWrapper()
   :outertype: ZLibWrapper

ZLibWrapper
^^^^^^^^^^^

.. java:constructor:: public ZLibWrapper(int level)
   :outertype: ZLibWrapper

ZLibWrapper
^^^^^^^^^^^

.. java:constructor:: public ZLibWrapper(int level, int comp_buff_size)
   :outertype: ZLibWrapper

Methods
-------
averageCompressionRate
^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public float averageCompressionRate()
   :outertype: ZLibWrapper

averageDecompressionRate
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public float averageDecompressionRate()
   :outertype: ZLibWrapper

compress
^^^^^^^^

.. java:method:: public ByteBuffer compress(ByteBuffer input)
   :outertype: ZLibWrapper

compress
^^^^^^^^

.. java:method:: public ByteBuffer compress(String input) throws CharacterCodingException
   :outertype: ZLibWrapper

decompress
^^^^^^^^^^

.. java:method:: public ByteBuffer decompress(ByteBuffer input) throws IOException
   :outertype: ZLibWrapper

decompressToString
^^^^^^^^^^^^^^^^^^

.. java:method:: public String decompressToString(ByteBuffer input) throws CharacterCodingException, IOException
   :outertype: ZLibWrapper

end
^^^

.. java:method:: public void end()
   :outertype: ZLibWrapper

lastCompressionRate
^^^^^^^^^^^^^^^^^^^

.. java:method:: public float lastCompressionRate()
   :outertype: ZLibWrapper

lastDecompressionRate
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public float lastDecompressionRate()
   :outertype: ZLibWrapper

main
^^^^

.. java:method:: public static void main(String[] args) throws Exception
   :outertype: ZLibWrapper

setIOListener
^^^^^^^^^^^^^

.. java:method:: public void setIOListener(IOListener listener)
   :outertype: ZLibWrapper

