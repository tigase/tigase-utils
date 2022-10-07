.. java:import:: tigase.xml Element

.. java:import:: java.util List

.. java:import:: java.util.logging Logger

AbstractForm
============

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class AbstractForm

   Created: 2007-05-27 11:41:02

   :author: bmalkow

Fields
------
instruction
^^^^^^^^^^^

.. java:field:: protected String instruction
   :outertype: AbstractForm

log
^^^

.. java:field:: protected static final Logger log
   :outertype: AbstractForm

title
^^^^^

.. java:field:: protected String title
   :outertype: AbstractForm

type
^^^^

.. java:field:: protected String type
   :outertype: AbstractForm

Constructors
------------
AbstractForm
^^^^^^^^^^^^

.. java:constructor:: public AbstractForm(Element form)
   :outertype: AbstractForm

AbstractForm
^^^^^^^^^^^^

.. java:constructor:: public AbstractForm(String type, String title, String instruction)
   :outertype: AbstractForm

Methods
-------
getElement
^^^^^^^^^^

.. java:method:: public Element getElement()
   :outertype: AbstractForm

getInstruction
^^^^^^^^^^^^^^

.. java:method:: public String getInstruction()
   :outertype: AbstractForm

getTitle
^^^^^^^^

.. java:method:: public String getTitle()
   :outertype: AbstractForm

getType
^^^^^^^

.. java:method:: public String getType()
   :outertype: AbstractForm

getTypeOrElse
^^^^^^^^^^^^^

.. java:method:: public String getTypeOrElse(String defValue)
   :outertype: AbstractForm

isType
^^^^^^

.. java:method:: public boolean isType(String type)
   :outertype: AbstractForm

setInstruction
^^^^^^^^^^^^^^

.. java:method:: public void setInstruction(String instruction)
   :outertype: AbstractForm

setTitle
^^^^^^^^

.. java:method:: public void setTitle(String title)
   :outertype: AbstractForm

setType
^^^^^^^

.. java:method:: public void setType(String type)
   :outertype: AbstractForm

