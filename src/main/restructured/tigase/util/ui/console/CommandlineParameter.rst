.. java:import:: java.util.function Function

CommandlineParameter
====================

.. java:package:: tigase.util.ui.console
   :noindex:

.. java:type:: public class CommandlineParameter

Methods
-------
equals
^^^^^^

.. java:method:: @Override public boolean equals(Object o)
   :outertype: CommandlineParameter

getDefaultValue
^^^^^^^^^^^^^^^

.. java:method:: public Optional<String> getDefaultValue()
   :outertype: CommandlineParameter

   Retrieves default value for this parameter option

   :return: Optional for the default value

getDescription
^^^^^^^^^^^^^^

.. java:method:: public Optional<String> getDescription()
   :outertype: CommandlineParameter

   Retrieves description for this parameter option

   :return: Optional for the description

getFullName
^^^^^^^^^^^

.. java:method:: public Optional<String> getFullName(boolean includeDash)
   :outertype: CommandlineParameter

   Retrieves full name for this parameter option

   :param includeDash: controls whether dashes should be included
   :return: Optional for the full name (which may include dashes depending on parameter)

getFullName
^^^^^^^^^^^

.. java:method:: public Optional<String> getFullName()
   :outertype: CommandlineParameter

   Retrieves full name for this parameter option

   :return: Optional for the full name

getSelectionOptions
^^^^^^^^^^^^^^^^^^^

.. java:method:: public Optional<List<String>> getSelectionOptions()
   :outertype: CommandlineParameter

   Retrieves list of possible selection options for this parameter option

   :return: Optional list of the possible selection options

getSingleLetter
^^^^^^^^^^^^^^^

.. java:method:: public Optional<String> getSingleLetter()
   :outertype: CommandlineParameter

   Retrieves single letter identification for this parameter option

   :return: Optional for the single letter

getSingleLetter
^^^^^^^^^^^^^^^

.. java:method:: public Optional<String> getSingleLetter(boolean includeDash)
   :outertype: CommandlineParameter

   Retrieves single letter identification for this parameter option

   :param includeDash: controls whether dash should be included
   :return: Optional for the single letter (which may include dash depending on parameter)

getType
^^^^^^^

.. java:method:: public Class getType()
   :outertype: CommandlineParameter

   Retrives expected class of a parameter

getValue
^^^^^^^^

.. java:method:: public Optional<String> getValue()
   :outertype: CommandlineParameter

   Retrieves stored value for this parameter option

   :return: Optional with the stored value

getValueDependentParameters
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public List<CommandlineParameter> getValueDependentParameters()
   :outertype: CommandlineParameter

hasValueDependentParameters
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public boolean hasValueDependentParameters()
   :outertype: CommandlineParameter

hashCode
^^^^^^^^

.. java:method:: @Override public int hashCode()
   :outertype: CommandlineParameter

isRequireArguments
^^^^^^^^^^^^^^^^^^

.. java:method:: public boolean isRequireArguments()
   :outertype: CommandlineParameter

   Retrieves information whether this parameter option requires arguments

   :return: true if the parameter option requires arguments

isRequired
^^^^^^^^^^

.. java:method:: public boolean isRequired()
   :outertype: CommandlineParameter

   Retrieves information whether this parameter option is required

   :return: true if the parameter option is required

isSecret
^^^^^^^^

.. java:method:: public boolean isSecret()
   :outertype: CommandlineParameter

   Retrieves information whether this parameter option is secret

   :return: true if the parameter option is secret

setValue
^^^^^^^^

.. java:method:: public void setValue(String value)
   :outertype: CommandlineParameter

   Sets value for this parameter option

   :param value: to be set

setValueFromDefault
^^^^^^^^^^^^^^^^^^^

.. java:method:: public void setValueFromDefault()
   :outertype: CommandlineParameter

   Sets the value from the configured default if present

toString
^^^^^^^^

.. java:method:: @Override public String toString()
   :outertype: CommandlineParameter

toStringSimple
^^^^^^^^^^^^^^

.. java:method:: public String toStringSimple()
   :outertype: CommandlineParameter

