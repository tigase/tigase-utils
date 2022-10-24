.. java:import:: java.util Collections

.. java:import:: java.util List

.. java:import:: java.util Optional

.. java:import:: java.util Properties

.. java:import:: java.util.function Supplier

Task.Builder
============

.. java:package:: tigase.util.ui.console
   :noindex:

.. java:type:: public static class Builder
   :outertype: Task

Constructors
------------
Builder
^^^^^^^

.. java:constructor:: public Builder()
   :outertype: Task.Builder

Methods
-------
additionalParameterSupplier
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Builder additionalParameterSupplier(Supplier<List<CommandlineParameter>> supplier)
   :outertype: Task.Builder

build
^^^^^

.. java:method:: public Task build()
   :outertype: Task.Builder

description
^^^^^^^^^^^

.. java:method:: public Builder description(String description)
   :outertype: Task.Builder

function
^^^^^^^^

.. java:method:: public Builder function(Executor<Properties> function)
   :outertype: Task.Builder

name
^^^^

.. java:method:: public Builder name(String name)
   :outertype: Task.Builder

