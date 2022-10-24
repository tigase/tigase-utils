.. java:import:: tigase.xml Element

.. java:import:: java.util List

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

Form
====

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class Form extends AbstractForm

   Created: 2007-05-27 11:41:02

   :author: bmalkow

Fields
------
fields
^^^^^^

.. java:field:: protected Fields fields
   :outertype: Form

log
^^^

.. java:field:: protected static final Logger log
   :outertype: Form

Constructors
------------
Form
^^^^

.. java:constructor:: public Form(Element form)
   :outertype: Form

Form
^^^^

.. java:constructor:: public Form(String type, String title, String instruction)
   :outertype: Form

Methods
-------
addField
^^^^^^^^

.. java:method:: public void addField(Field field)
   :outertype: Form

clear
^^^^^

.. java:method:: public void clear()
   :outertype: Form

copyValuesFrom
^^^^^^^^^^^^^^

.. java:method:: public void copyValuesFrom(Element form)
   :outertype: Form

copyValuesFrom
^^^^^^^^^^^^^^

.. java:method:: public void copyValuesFrom(Form form)
   :outertype: Form

get
^^^

.. java:method:: public Field get(String var)
   :outertype: Form

getAllFields
^^^^^^^^^^^^

.. java:method:: public List<Field> getAllFields()
   :outertype: Form

getAsBoolean
^^^^^^^^^^^^

.. java:method:: public Boolean getAsBoolean(String var)
   :outertype: Form

getAsInteger
^^^^^^^^^^^^

.. java:method:: public Integer getAsInteger(String var)
   :outertype: Form

getAsLong
^^^^^^^^^

.. java:method:: public Long getAsLong(String var)
   :outertype: Form

getAsString
^^^^^^^^^^^

.. java:method:: public String getAsString(String var)
   :outertype: Form

getAsStrings
^^^^^^^^^^^^

.. java:method:: public String[] getAsStrings(String var)
   :outertype: Form

getElement
^^^^^^^^^^

.. java:method:: @Override public Element getElement()
   :outertype: Form

getInstruction
^^^^^^^^^^^^^^

.. java:method:: @Override public String getInstruction()
   :outertype: Form

getTitle
^^^^^^^^

.. java:method:: @Override public String getTitle()
   :outertype: Form

getType
^^^^^^^

.. java:method:: @Override public String getType()
   :outertype: Form

getTypeOrElse
^^^^^^^^^^^^^

.. java:method:: @Override public String getTypeOrElse(String defValue)
   :outertype: Form

is
^^

.. java:method:: public boolean is(String var)
   :outertype: Form

isType
^^^^^^

.. java:method:: @Override public boolean isType(String type)
   :outertype: Form

removeField
^^^^^^^^^^^

.. java:method:: public void removeField(String var)
   :outertype: Form

setInstruction
^^^^^^^^^^^^^^

.. java:method:: @Override public void setInstruction(String instruction)
   :outertype: Form

setTitle
^^^^^^^^

.. java:method:: @Override public void setTitle(String title)
   :outertype: Form

setType
^^^^^^^

.. java:method:: @Override public void setType(String type)
   :outertype: Form

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: Form

