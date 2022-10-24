TODO.Severity
=============

.. java:package:: tigase.annotations
   :noindex:

.. java:type:: public enum Severity
   :outertype: TODO

   This enumeration defines importance levels for code change which is expected to be made for annotated element.

Enum Constants
--------------
CRITICAL
^^^^^^^^

.. java:field:: public static final TODO.Severity CRITICAL
   :outertype: TODO.Severity

   If change severity is set to \ ``CRITICAL``\  it means that wihtout this change some progress is not possible. Probably it blocks some important functionality like \ *SSL*\  activation for server port.

DOCUMENTATION
^^^^^^^^^^^^^

.. java:field:: public static final TODO.Severity DOCUMENTATION
   :outertype: TODO.Severity

   \ ``DOCUMENTATION``\  severity refers to code which should be documented. It does not refer to API documentation. It refers to in-line documentation which should be added due to complicity of some code or unusual algorithm used. Usually I try to avoid "smart" code but in certain cases it is required to use code which might be difficult to understand. In all such cases code should be detaily documented. This annotation can help to remind what parts of code needs more documentation. This annotation should be also added by other developer who is not owner of some part of code but tried to read it and found it difficult to understand. In such case it is recommended that this developers should leave such annotation to bring attention to owner that some code needs better documentation.

IMPORTANT
^^^^^^^^^

.. java:field:: public static final TODO.Severity IMPORTANT
   :outertype: TODO.Severity

   \ ``IMPORTANT``\  severity means that this code does not block implementation of any functionality but might be inefficient, insecure or contain some temporary solution. \ ``IMPORTANT``\  severity can be also assigned to code which needs some medium or major refactoring.

TRIVIAL
^^^^^^^

.. java:field:: public static final TODO.Severity TRIVIAL
   :outertype: TODO.Severity

   \ ``TRIVIAL``\  severity means that this code works correctly and is implemented according to design but there is still some minor improvement that can be done or just cleaning the code. \ ``TRIVIAL``\  severity can be assigned also to code which needs some minor refactoring.

