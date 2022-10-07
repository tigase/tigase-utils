.. java:import:: tigase.xml Element

.. java:import:: java.util LinkedList

.. java:import:: java.util List

Field
=====

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class Field implements Comparable<Field>

   Created: 2007-05-27 10:56:06

   :author: bmalkow

Constructors
------------
Field
^^^^^

.. java:constructor:: public Field(Element fieldElement)
   :outertype: Field

Methods
-------
cloneShalow
^^^^^^^^^^^

.. java:method:: public Field cloneShalow()
   :outertype: Field

compareTo
^^^^^^^^^

.. java:method:: @Override public int compareTo(Field o)
   :outertype: Field

fieldBoolean
^^^^^^^^^^^^

.. java:method:: public static Field fieldBoolean(String var, Boolean value, String label)
   :outertype: Field

fieldFixed
^^^^^^^^^^

.. java:method:: public static Field fieldFixed(String value)
   :outertype: Field

fieldHidden
^^^^^^^^^^^

.. java:method:: public static Field fieldHidden(String var, String value)
   :outertype: Field

fieldJidMulti
^^^^^^^^^^^^^

.. java:method:: public static Field fieldJidMulti(String var, String[] values, String label)
   :outertype: Field

fieldJidSingle
^^^^^^^^^^^^^^

.. java:method:: public static Field fieldJidSingle(String var, String value, String label)
   :outertype: Field

fieldListMulti
^^^^^^^^^^^^^^

.. java:method:: public static Field fieldListMulti(String var, String[] values, String label, String[] optionsLabel, String[] optionsValue)
   :outertype: Field

fieldListSingle
^^^^^^^^^^^^^^^

.. java:method:: public static Field fieldListSingle(String var, String value, String label, String[] optionsLabel, String[] optionsValue)
   :outertype: Field

fieldTextMulti
^^^^^^^^^^^^^^

.. java:method:: public static Field fieldTextMulti(String var, String value, String label)
   :outertype: Field

fieldTextMulti
^^^^^^^^^^^^^^

.. java:method:: public static Field fieldTextMulti(String var, String[] values, String label)
   :outertype: Field

fieldTextPrivate
^^^^^^^^^^^^^^^^

.. java:method:: public static Field fieldTextPrivate(String var, String value, String label)
   :outertype: Field

fieldTextSingle
^^^^^^^^^^^^^^^

.. java:method:: public static Field fieldTextSingle(String var, String value, String label)
   :outertype: Field

getAsBoolean
^^^^^^^^^^^^

.. java:method:: public static Boolean getAsBoolean(Field f)
   :outertype: Field

getDescription
^^^^^^^^^^^^^^

.. java:method:: public String getDescription()
   :outertype: Field

getElement
^^^^^^^^^^

.. java:method:: public Element getElement()
   :outertype: Field

getElement
^^^^^^^^^^

.. java:method:: public Element getElement(boolean type, boolean label)
   :outertype: Field

getLabel
^^^^^^^^

.. java:method:: public String getLabel()
   :outertype: Field

getOptionLabels
^^^^^^^^^^^^^^^

.. java:method:: public String[] getOptionLabels()
   :outertype: Field

getOptionValues
^^^^^^^^^^^^^^^

.. java:method:: public String[] getOptionValues()
   :outertype: Field

getType
^^^^^^^

.. java:method:: public FieldType getType()
   :outertype: Field

getValue
^^^^^^^^

.. java:method:: public String getValue()
   :outertype: Field

getValues
^^^^^^^^^

.. java:method:: public String[] getValues()
   :outertype: Field

getVar
^^^^^^

.. java:method:: public String getVar()
   :outertype: Field

isRequired
^^^^^^^^^^

.. java:method:: public boolean isRequired()
   :outertype: Field

main
^^^^

.. java:method:: public static void main(String[] args)
   :outertype: Field

setDescription
^^^^^^^^^^^^^^

.. java:method:: public void setDescription(String description)
   :outertype: Field

setLabel
^^^^^^^^

.. java:method:: public void setLabel(String label)
   :outertype: Field

setOptionLabels
^^^^^^^^^^^^^^^

.. java:method:: public void setOptionLabels(String[] optionLabels)
   :outertype: Field

setOptionValues
^^^^^^^^^^^^^^^

.. java:method:: public void setOptionValues(String[] optionValues)
   :outertype: Field

setRequired
^^^^^^^^^^^

.. java:method:: public void setRequired(boolean required)
   :outertype: Field

setType
^^^^^^^

.. java:method:: public void setType(FieldType type)
   :outertype: Field

setValues
^^^^^^^^^

.. java:method:: public void setValues(String[] values)
   :outertype: Field

setVar
^^^^^^

.. java:method:: public void setVar(String var)
   :outertype: Field

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Field

