.. java:import:: java.util.function Function

CommandlineParameter.Builder
============================

.. java:package:: tigase.util.ui.console
   :noindex:

.. java:type:: public static class Builder
   :outertype: CommandlineParameter

   Creates a \ :java:ref:`CommandlineParameter`\  builder

Constructors
------------
Builder
^^^^^^^

.. java:constructor:: public Builder(String singleLetter, String fullName)
   :outertype: CommandlineParameter.Builder

   Constructs a \ :java:ref:`CommandlineParameter`\  builder. It takes as parameters both "single-letter" and "full-name" of which one is mandatory

   :param singleLetter: single letter identification of the option
   :param fullName: full named identification of the option

Methods
-------
build
^^^^^

.. java:method:: public CommandlineParameter build()
   :outertype: CommandlineParameter.Builder

   Creates a concrete \ :java:ref:`CommandlineParameter`\  from particular builder

   :return: constructed \ :java:ref:`CommandlineParameter`\

defaultValue
^^^^^^^^^^^^

.. java:method:: public Builder defaultValue(String defaultValue)
   :outertype: CommandlineParameter.Builder

   Sets default value for the parameter option

   :param defaultValue: default value to be set
   :return: current Builder object

description
^^^^^^^^^^^

.. java:method:: public Builder description(String description)
   :outertype: CommandlineParameter.Builder

   Sets the description for the parameter option

   :param description: description to be set
   :return: current Builder object

options
^^^^^^^

.. java:method:: public Builder options(String... options)
   :outertype: CommandlineParameter.Builder

   Sets possible options for the parameter option

   :param options: array of possible options
   :return: current Builder object

requireArguments
^^^^^^^^^^^^^^^^

.. java:method:: public Builder requireArguments(boolean required)
   :outertype: CommandlineParameter.Builder

   Sets whether particular parameter option requires arguments

   :param required: whether the option needs parameter
   :return: current Builder object

required
^^^^^^^^

.. java:method:: public Builder required(boolean required)
   :outertype: CommandlineParameter.Builder

   Sets whether particular parameter option is required - if yes then it's mandatory to set it's value or provide default.

   :param required: whether the option is required
   :return: current Builder object

secret
^^^^^^

.. java:method:: public Builder secret()
   :outertype: CommandlineParameter.Builder

   Sets the parameter option as secret which influences how it will be obtained in interactive mode (won't be printed, useful for passwords)

   :return: current Builder object

type
^^^^

.. java:method:: public Builder type(Class type)
   :outertype: CommandlineParameter.Builder

valueDependentParametersProvider
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Builder valueDependentParametersProvider(Function<String, List<CommandlineParameter>> provider)
   :outertype: CommandlineParameter.Builder

