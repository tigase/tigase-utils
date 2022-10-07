TODO
====

.. java:package:: tigase.annotations
   :noindex:

.. java:type:: @Retention @Documented @Target public @interface TODO

   \ ``TODO``\  this is information for developers that there is still something to do with annotated code. Additional parameters can provide detailed information what exatcly is suposed to correct in code, how important it is for project the time when it should be done and name of developer to which correction is assigned.

   \ ``TODO``\  annotation has a few properties which can be set to better describe code to be changed like \ ``note``\  - allows you to add some description, \ ``severity``\  - allows you to set severity level for this code change, \ ``timeLine``\  - allows you to set expected time when code change should be ready to use and \ ``assignedTo``\  - allows you to set name of developer who should make the change to code. All this properties has some default values so it is not necessary to set them all every time you use \ ``TODO``\  annotation. Below you can find a few samples how to use \ ``TODO``\  annotation:

   Sample of use all annotation with all possible properties:

   .. parsed-literal::

        @TODO(
       severity=TODO.Severity.CRITICAL,
       note="This empty method which should calculate data checksum, needs implementation.",
       timeLine="30/11/2004",
       assignedTo="Artur Hefczyc"
      )
      public long checksum(char[] buff) { return -1; }

   A few samples using selected set of \ ``TODO``\  properties:

   .. parsed-literal::

        @TODO(
       severity=TODO.Severity.DOCUMENTATION,
       note="This method needs better inline documentation, I can't udnerstan how it works",
       assignedTo="Artur Hefczyc"
      )
      public String calculateWeather(byte[][][] buff) { ... }

   .. parsed-literal::

        @TODO(note="SSL socket functionality not implemented yet.")
      protected void init() { ... }

   Created: Wed Sep 29 18:58:21 2004

   :author: \ `Artur Hefczyc <mailto:artur.hefczyc@tigase.org>`_\

