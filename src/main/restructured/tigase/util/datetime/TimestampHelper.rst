.. java:import:: java.text ParseException

.. java:import:: java.time LocalDateTime

.. java:import:: java.time ZoneId

.. java:import:: java.time ZoneOffset

.. java:import:: java.time ZonedDateTime

.. java:import:: java.time.format DateTimeFormatter

.. java:import:: java.time.format DateTimeFormatterBuilder

.. java:import:: java.time.format DateTimeParseException

.. java:import:: java.util Calendar

.. java:import:: java.util Date

.. java:import:: java.util TimeZone

TimestampHelper
===============

.. java:package:: tigase.util.datetime
   :noindex:

.. java:type:: public class TimestampHelper

   :author: andrzej

Constructors
------------
TimestampHelper
^^^^^^^^^^^^^^^

.. java:constructor:: public TimestampHelper()
   :outertype: TimestampHelper

   Creates helper configured to produce timestamps in UTC timezone.

   **See also:** :java:ref:`TimestampHelper.setUseUTC(boolean)`

TimestampHelper
^^^^^^^^^^^^^^^

.. java:constructor:: public TimestampHelper(boolean useUTC)
   :outertype: TimestampHelper

   Creates helper configured to produce timestamps in UTC timezone or local timezone.

   :param useUTC: \ ``true``\  to use UTC timezone, \ ``false``\  to use local timezone.

   **See also:** :java:ref:`TimestampHelper.setUseUTC(boolean)`

Methods
-------
format
^^^^^^

.. java:method:: public String format(Date ts)
   :outertype: TimestampHelper

formatInLegacyDelayedDelivery
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String formatInLegacyDelayedDelivery(Date date)
   :outertype: TimestampHelper

formatWithMs
^^^^^^^^^^^^

.. java:method:: public String formatWithMs(Date ts)
   :outertype: TimestampHelper

isUseUTC
^^^^^^^^

.. java:method:: public boolean isUseUTC()
   :outertype: TimestampHelper

parseTimestamp
^^^^^^^^^^^^^^

.. java:method:: public Date parseTimestamp(String tmp) throws ParseException
   :outertype: TimestampHelper

setUseUTC
^^^^^^^^^

.. java:method:: public void setUseUTC(boolean useUTC)
   :outertype: TimestampHelper

   If \ ``false``\  then generated timestamps will be in local timezone. In other case UTC will be used. \ ``true``\  by default.

   :param useUTC: \ ``true``\  to use UTC timezone, \ ``false``\  to use local timezone.

