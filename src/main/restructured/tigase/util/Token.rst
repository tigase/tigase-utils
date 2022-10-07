.. java:import:: tigase.xmpp.jid BareJID

.. java:import:: java.nio.charset Charset

.. java:import:: java.security MessageDigest

.. java:import:: java.security SecureRandom

.. java:import:: java.util Date

.. java:import:: java.util Random

Token
=====

.. java:package:: tigase.util
   :noindex:

.. java:type:: public class Token

   Created by bmalkow on 21.04.2017.

Methods
-------
copy
^^^^

.. java:method:: static String copy(byte[] buff, int offset, int len)
   :outertype: Token

create
^^^^^^

.. java:method:: public static Token create(BareJID jid)
   :outertype: Token

create
^^^^^^

.. java:method:: public static Token create(BareJID jid, Date timestamp, String random)
   :outertype: Token

getBuff
^^^^^^^

.. java:method:: protected byte[] getBuff()
   :outertype: Token

getEncoded
^^^^^^^^^^

.. java:method:: public String getEncoded()
   :outertype: Token

getHash
^^^^^^^

.. java:method:: public String getHash()
   :outertype: Token

getJid
^^^^^^

.. java:method:: public BareJID getJid()
   :outertype: Token

getRandom
^^^^^^^^^

.. java:method:: public String getRandom()
   :outertype: Token

getTimestamp
^^^^^^^^^^^^

.. java:method:: public Date getTimestamp()
   :outertype: Token

nullPos
^^^^^^^

.. java:method:: static int nullPos(byte[] buff, int from)
   :outertype: Token

parse
^^^^^

.. java:method:: public static Token parse(String encodedToken)
   :outertype: Token

