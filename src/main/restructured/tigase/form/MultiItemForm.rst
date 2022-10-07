.. java:import:: tigase.xml Element

.. java:import:: java.util ArrayList

.. java:import:: java.util Collections

.. java:import:: java.util Iterator

.. java:import:: java.util List

.. java:import:: java.util.logging Level

.. java:import:: java.util.logging Logger

MultiItemForm
=============

.. java:package:: tigase.form
   :noindex:

.. java:type:: public class MultiItemForm extends Form

   :author: Wojciech Kapcia

Fields
------
log
^^^

.. java:field:: protected static final Logger log
   :outertype: MultiItemForm

Constructors
------------
MultiItemForm
^^^^^^^^^^^^^

.. java:constructor:: public MultiItemForm()
   :outertype: MultiItemForm

MultiItemForm
^^^^^^^^^^^^^

.. java:constructor:: public MultiItemForm(String title)
   :outertype: MultiItemForm

MultiItemForm
^^^^^^^^^^^^^

.. java:constructor:: public MultiItemForm(Element form)
   :outertype: MultiItemForm

MultiItemForm
^^^^^^^^^^^^^

.. java:constructor:: public MultiItemForm(Form form)
   :outertype: MultiItemForm

Methods
-------
addItem
^^^^^^^

.. java:method:: public void addItem(Fields i)
   :outertype: MultiItemForm

getAllItems
^^^^^^^^^^^

.. java:method:: public List<Fields> getAllItems()
   :outertype: MultiItemForm

getElement
^^^^^^^^^^

.. java:method:: @Override public Element getElement()
   :outertype: MultiItemForm

main
^^^^

.. java:method:: public static void main(String[] args)
   :outertype: MultiItemForm

setReported
^^^^^^^^^^^

.. java:method:: public void setReported(List<Field> fields)
   :outertype: MultiItemForm

